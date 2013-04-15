package br.com.mcampos.web.inep.controller.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginProperty;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.core.ComboboxUtils;
import br.com.mcampos.web.core.report.JDBCReportServlet;
import br.com.mcampos.web.core.report.JasperReportController;
import br.com.mcampos.web.core.report.ReportItem;
import br.com.mcampos.web.core.report.ReportListRenderer;

public class InepReportController extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = -8511479189709820364L;

	private static final String defaultReportFormat = "defaultReportFormat";

	@Wire( "#exportFormat" )
	private Combobox cmbFormat;

	@Wire
	private Combobox cmbTask;

	@Wire
	private Combobox cmbRevisor;

	@Wire( "#listReport" )
	private Listbox listbox;

	@Wire( "#generalView" )
	Component generalView;

	@Wire( "#divTarefa" )
	Component generalDiv;

	@Wire
	private Combobox comboEvent;

	private InepRevisor revisor;

	@Listen( "onClick=#cmdSubmit" )
	public void onOK( Event evt )
	{
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item != null ) {
			ReportItem r = (ReportItem) item.getValue( );
			if ( getCmbFormat( ) != null && getCmbFormat( ).getSelectedItem( ) != null ) {
				String value = (String) getCmbFormat( ).getSelectedItem( ).getValue( );
				r.setReportFormat( Integer.parseInt( value ) );
				getSession( ).setProperty( getCurrentCollaborator( ), defaultReportFormat, value );
				try {
					getListbox( ).setSelectedItem( null );
					doReport( r );
				}
				catch ( JRException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace( );
				}
			}
		}
	}

	@Listen( "onClick=#cmdCancel" )
	public void onCancel( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		unloadMe( );
	}

	@Listen( "onSelect=#listReport" )
	public void onSelect( Event evt )
	{
		onOK( evt );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	public Combobox getCmbFormat( )
	{
		return cmbFormat;
	}

	public Listbox getListbox( )
	{
		return listbox;
	}

	private void doReport( ReportItem item ) throws JRException
	{
		pushSessionParameter( JasperReportController.paramName, item );
		Executions.getCurrent( ).sendRedirect( JDBCReportServlet.reportUrl, "_blank" );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		if ( getListbox( ) != null ) {
			listbox.setItemRenderer( new ReportListRenderer( ) );
		}
		if ( getCmbFormat( ) != null && getCmbFormat( ).getItemCount( ) > 0 ) {
			LoginProperty p = getSession( ).getProperty( getCurrentCollaborator( ), defaultReportFormat );
			if ( p != null && SysUtils.isEmpty( p.getValue( ) ) == false ) {
				ComboboxUtils.find( getCmbFormat( ), p.getValue( ) );
			}
			else {
				getCmbFormat( ).setSelectedIndex( 0 );
			}
		}
		loadCombobox( );
		if ( getRevisor( ) == null ) {
			generalView.setVisible( true );
		}
		else if ( getRevisor( ).isCoordenador( ) ) {
			generalView.setVisible( true );
			generalDiv.setVisible( false );
		}
	}

	public void setReports( )
	{
		List<ReportItem> list = new ArrayList<ReportItem>( );
		ReportItem item;
		/*Relatorio 1*/
		item = new ReportItem( "Extrato de Correção - Corretor" );
		item.setReportUrl( "/reports/inep/revisor_1" );
		item.setParams( configReportParams( ) );
		list.add( item );

		/*Relatorio 3*/
		item = new ReportItem( "Extrato de Correção Geral" );
		item.setReportUrl( "/reports/inep/revisor_3" );
		item.setParams( configReportParams( ) );
		list.add( item );

		/*Relatorio 3*/
		item = new ReportItem( "Inscrições com Pendências" );
		item.setReportUrl( "/reports/inep/revisor_3_1" );
		item.setParams( configReportParams( ) );
		list.add( item );

		if ( getRevisor( ) == null ) {
			/*Relatorio 4*/
			item = new ReportItem( "Situação da Correção - Corretores" );
			item.setReportUrl( "/reports/inep/inep_1" );
			item.setParams( configReportParams( ) );
			list.add( item );

			/*Relatorio 5*/
			item = new ReportItem( "Situação da Correção - Coordenadores" );
			item.setReportUrl( "/reports/inep/inep_1_2" );
			item.setParams( configReportParams( ) );
			list.add( item );

			item = new ReportItem( "Tarefas Não Corrigidas" );
			item.setReportUrl( "/reports/inep/inep_4_1" );
			item.setParams( configReportParams( ) );
			list.add( item );

			item = new ReportItem( "Tarefas Corrigidas" );
			item.setReportUrl( "/reports/inep/inep_4" );
			item.setParams( configReportParams( ) );
			list.add( item );

		}
		else {

			if ( getRevisor( ).isCoordenador( ) ) {
				/*Relatorio 4*/
				item = new ReportItem( "Situação da Correção - Corretores" );
				item.setReportUrl( "/reports/inep/inep_2" );
				item.setParams( configReportParams( ) );
				list.add( item );

				/*Relatorio 5*/
				item = new ReportItem( "Extrato de Correção" );
				item.setReportUrl( "/reports/inep/inep_1_1" );
				item.setParams( configReportParams( ) );
				list.add( item );

				item = new ReportItem( "Tarefas Não Corrigidas" );
				item.setReportUrl( "/reports/inep/inep_4_1" );
				item.setParams( configReportParams( ) );
				list.add( item );

				item = new ReportItem( "Tarefas Corrigidas" );
				item.setReportUrl( "/reports/inep/inep_4" );
				item.setParams( configReportParams( ) );
				list.add( item );
			}
			else {
				/*Relatorio 4*/
				item = new ReportItem( "Meu Extrato de Correção" );
				item.setReportUrl( "/reports/inep/inep_3" );
				item.setParams( configReportParams( ) );
				list.add( item );

				item = new ReportItem( "Minhas Tarefas Não Corrigidas" );
				item.setReportUrl( "/reports/inep/inep_4_1" );
				item.setParams( configReportParams( ) );
				list.add( item );

				item = new ReportItem( "Minhas Tarefas Corrigidas" );
				item.setReportUrl( "/reports/inep/inep_4" );
				item.setParams( configReportParams( ) );
				list.add( item );
			}
		}

		if ( getListbox( ) != null ) {
			getListbox( ).setModel( new ListModelList<ReportItem>( list ) );
		}
	}

	@Override
	protected Map<String, Object> configReportParams( )
	{
		Map<String, Object> params = super.configReportParams( );

		InepPackage event = (InepPackage) ( getComboEvent( ).getSelectedItem( ).getValue( ) );
		if ( event != null ) {
			params.put( "EVENT_ID", event.getId( ).getId( ) );
		}
		if ( getRevisor( ) != null ) {
			params.put( "EVENT_ID", getRevisor( ).getId( ).getEventId( ) );
			params.put( "TASK_ID", getRevisor( ).getTask( ).getId( ).getId( ) );
			params.put( "COLLABORATOR_ID", getRevisor( ).getId( ).getSequence( ) );
			if ( getRevisor( ).isCoordenador( ) ) {
				if ( getCmbRevisor( ).getSelectedItem( ) != null ) {
					InepRevisor rev = getCmbRevisor( ).getSelectedItem( ).getValue( );
					params.put( "COLLABORATOR_ID", rev.getId( ).getSequence( ) );
				}
			}
		}
		else {
			if ( getCmbRevisor( ).getSelectedItem( ) != null ) {
				InepRevisor rev = getCmbRevisor( ).getSelectedItem( ).getValue( );
				params.put( "COLLABORATOR_ID", rev.getId( ).getSequence( ) );
			}
			if ( getCmbTask( ).getSelectedItem( ) != null ) {
				InepTask task = getCmbTask( ).getSelectedItem( ).getValue( );
				params.put( "TASK_ID", task.getId( ).getId( ) );
			}
		}
		return params;
	}

	public InepRevisor getRevisor( )
	{
		if ( revisor == null ) {
			revisor = getSession( ).getRevisor( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ),
					getCurrentCollaborator( ) );
		}
		return revisor;
	}

	@Override
	protected Class<TeamSession> getSessionClass( )
	{
		return TeamSession.class;
	}

	public Combobox getComboEvent( )
	{
		return comboEvent;
	}

	private void loadCombobox( )
	{
		List<InepPackage> events = getSession( ).getEvents( getCurrentCollaborator( ) );

		if ( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) == false ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for ( InepPackage e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
			onSelectPackage( null );
		}
	}

	private void loadTasks( InepPackage event )
	{

		ComboboxUtils.load( getCmbTask( ), getSession( ).getTasks( event ),
				getRevisor( ) == null ? null : getRevisor( ).getTask( ), true );
	}

	private void loadRevisor( InepTask task )
	{
		ComboboxUtils.load( getCmbRevisor( ), getSession( ).getTeam( task ), getRevisor( ) == null ? null : getRevisor( ), true );
	}

	@Listen( "onSelect = #cmbTask" )
	public void onSelectTask( Event evt )
	{
		loadRevisor( (InepTask) getCmbTask( ).getSelectedItem( ).getValue( ) );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onSelect = #cmbRevisor" )
	public void onSelectRevisor( Event evt )
	{
		setReports( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		Comboitem item = comboEvent.getSelectedItem( );
		if ( item != null ) {
			loadTasks( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ) );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private Combobox getCmbTask( )
	{
		return cmbTask;
	}

	private Combobox getCmbRevisor( )
	{
		return cmbRevisor;
	}
}
