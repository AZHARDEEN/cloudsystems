package br.com.mcampos.web.inep.controller.report;

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

public abstract class BaseReportController extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = 4373288770707627215L;
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

	protected abstract void setReports( );

	@Listen( "onClick=#cmdSubmit" )
	public void onOK( Event evt )
	{
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item != null ) {
			ReportItem r = (ReportItem) item.getValue( );
			if ( getCmbFormat( ) != null && getCmbFormat( ).getSelectedItem( ) != null ) {
				String value = (String) getCmbFormat( ).getSelectedItem( ).getValue( );
				r.setReportFormat( Integer.parseInt( value ) );
				getSession( ).setProperty( getPrincipal( ), defaultReportFormat, value );
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
			LoginProperty p = getSession( ).getProperty( getPrincipal( ), defaultReportFormat );
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
			generalDiv.setVisible( true );
		}
		else if ( getRevisor( ).isCoordenador( ) ) {
			generalView.setVisible( true );
			generalDiv.setVisible( false );
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
			if ( getRevisor( ).getTask( ) != null )
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
					getPrincipal( ) );
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
		List<InepPackage> events = getSession( ).getEvents( getPrincipal( ) );

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

		if ( getRevisor( ) == null ) {
			ComboboxUtils.load( getCmbTask( ), getSession( ).getTasks( event ), null, true );
		}
		else if ( getRevisor( ).getTask( ) != null ) {
			ComboboxUtils.load( getCmbTask( ), getSession( ).getTasks( event ), getRevisor( ).getTask( ), true );
		}
		else {
			loadRevisor( null );
		}
	}

	private void loadRevisor( InepTask task )
	{
		if ( getRevisor( ) == null ) {
			ComboboxUtils.load( getCmbRevisor( ), getSession( ).getTeam( task ), null, true );
		}
		else {
			if ( getRevisor( ).getTask( ) != null )
				ComboboxUtils.load( getCmbRevisor( ), getSession( ).getTeam( task ), getRevisor( ), true );
			else
				ComboboxUtils.load( getCmbRevisor( ), getSession( ).getOralTeam( getRevisor( ).getEvent( ) ), getRevisor( ), true );
		}
		if ( getPrincipal( ).getId( ).equals( 1 ) ) {
			setReports( );
		}
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
