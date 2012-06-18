package br.com.mcampos.web.inep.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

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
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.core.report.JDBCReportServlet;
import br.com.mcampos.web.core.report.JasperReportController;
import br.com.mcampos.web.core.report.ReportItem;
import br.com.mcampos.web.core.report.ReportListRenderer;

public class InepReportController extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = -8511479189709820364L;

	@Wire( "#exportFormat" )
	private Combobox cmbFormat;

	@Wire( "#listReport" )
	private Listbox listbox;

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
				try {
					doReport( r );
				}
				catch ( JRException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace( );
				}
			}
			onCancel( evt );
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
		return this.cmbFormat;
	}

	public Listbox getListbox( )
	{
		return this.listbox;
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
			this.listbox.setItemRenderer( new ReportListRenderer( ) );
		}
		if ( getCmbFormat( ) != null ) {
			if ( getCmbFormat( ).getItemCount( ) > 0 ) {
				getCmbFormat( ).setSelectedIndex( 0 );
			}
		}
		loadCombobox( );
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

		/*Relatorio 2*/
		item = new ReportItem( "Extrato de Correção por Tarefa" );
		item.setReportUrl( "/reports/inep/revisor_2" );
		item.setParams( configReportParams( ) );
		list.add( item );

		/*Relatorio 3*/
		item = new ReportItem( "Grade de Correção" );
		item.setReportUrl( "/reports/inep/revisor_3" );
		item.setParams( configReportParams( ) );
		list.add( item );

		if ( getListbox( ) != null ) {
			getListbox( ).setModel( new ListModelList<ReportItem>( list ) );
		}
	}

	private Map<String, Object> configReportParams( )
	{
		Map<String, Object> params = new HashMap<String, Object>( );

		InepPackage event = (InepPackage) ( getComboEvent( ).getSelectedItem( ).getValue( ) );
		params.put( "COLLABORATOR_ID", getCurrentCollaborator( ).getId( ).getSequence( ) );
		params.put( "COMPANY_ID", getCurrentCollaborator( ).getId( ).getCompanyId( ) );
		if ( event != null ) {
			params.put( "EVENT_ID", event.getId( ).getId( ) );
		}
		if ( getRevisor( ) != null ) {
			params.put( "EVENT_ID", getRevisor( ).getId( ).getEventId( ) );
			params.put( "TASK_ID", getRevisor( ).getTask( ).getId( ).getId( ) );
		}
		return params;
	}

	public InepRevisor getRevisor( )
	{
		if ( this.revisor == null ) {
			this.revisor = getSession( ).getRevisor( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ),
					getCurrentCollaborator( ) );
		}
		return this.revisor;
	}

	@Override
	protected Class<TeamSession> getSessionClass( )
	{
		return TeamSession.class;
	}

	public Combobox getComboEvent( )
	{
		return this.comboEvent;
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

	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		Comboitem item = this.comboEvent.getSelectedItem( );
		if ( item != null ) {
			setReports( );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}
}
