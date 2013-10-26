package br.com.mcampos.web.inep.controller.report;

import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.security.LoginProperty;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.core.ComboboxUtils;
import br.com.mcampos.web.core.report.ReportItem;
import br.com.mcampos.web.core.report.ReportListRenderer;
import br.com.mcampos.zkutils.ReportEvent;

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
		Listitem item = this.getListbox( ).getSelectedItem( );
		if ( item != null ) {
			ReportItem r = (ReportItem) item.getValue( );
			if ( this.getCmbFormat( ) != null && this.getCmbFormat( ).getSelectedItem( ) != null ) {
				String value = (String) this.getCmbFormat( ).getSelectedItem( ).getValue( );
				r.setReportFormat( Integer.parseInt( value ) );
				this.getSession( ).setProperty( this.getPrincipal( ), defaultReportFormat, value );
				try {
					this.getListbox( ).setSelectedItem( null );
					this.doReport( r );
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
		this.unloadMe( );
	}

	@Listen( "onSelect=#listReport" )
	public void onSelect( Event evt )
	{
		this.onOK( evt );
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
		/*
		pushSessionParameter( JasperReportController.paramName, item );
		Executions.getCurrent( ).sendRedirect( JDBCReportServlet.reportUrl, "_blank" );
		*/
		EventQueues.lookup( ReportEvent.queueName, true ).publish( new ReportEvent( item ) );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		if ( this.getListbox( ) != null ) {
			this.listbox.setItemRenderer( new ReportListRenderer( ) );
		}
		if ( this.getCmbFormat( ) != null && this.getCmbFormat( ).getItemCount( ) > 0 ) {
			LoginProperty p = this.getSession( ).getProperty( this.getPrincipal( ), defaultReportFormat );
			if ( p != null && SysUtils.isEmpty( p.getValue( ) ) == false ) {
				ComboboxUtils.find( this.getCmbFormat( ), p.getValue( ) );
			}
			else {
				this.getCmbFormat( ).setSelectedIndex( 0 );
			}
		}
		this.loadCombobox( );
		if ( this.getRevisor( ) == null ) {
			if ( this.generalView != null ) {
				this.generalView.setVisible( true );
			}
			if ( this.generalDiv != null ) {
				this.generalDiv.setVisible( true );
			}
		}
		else if ( this.getRevisor( ).isCoordenador( ) ) {
			if ( this.generalView != null ) {
				this.generalView.setVisible( true );
			}
			if ( this.generalDiv != null ) {
				this.generalDiv.setVisible( false );
			}
		}
	}

	@Override
	protected Map<String, Object> configReportParams( )
	{
		Map<String, Object> params = super.configReportParams( );

		InepEvent event = (InepEvent) ( this.getComboEvent( ).getSelectedItem( ).getValue( ) );
		if ( event != null ) {
			params.put( "EVENT_ID", event.getId( ).getId( ) );
		}
		if ( this.getRevisor( ) != null ) {
			params.put( "EVENT_ID", this.getRevisor( ).getId( ).getEventId( ) );
			if ( this.getRevisor( ).getTask( ) != null ) {
				params.put( "TASK_ID", this.getRevisor( ).getTask( ).getId( ).getId( ) );
			}
			params.put( "COLLABORATOR_ID", this.getRevisor( ).getId( ).getSequence( ) );
			if ( this.getRevisor( ).isCoordenador( ) ) {
				if ( this.getCmbRevisor( ).getSelectedItem( ) != null ) {
					InepRevisor rev = this.getCmbRevisor( ).getSelectedItem( ).getValue( );
					params.put( "COLLABORATOR_ID", rev.getId( ).getSequence( ) );
				}
			}
		}
		else {
			if ( this.getCmbRevisor( ).getSelectedItem( ) != null ) {
				InepRevisor rev = this.getCmbRevisor( ).getSelectedItem( ).getValue( );
				params.put( "COLLABORATOR_ID", rev.getId( ).getSequence( ) );
			}
			if ( this.getCmbTask( ).getSelectedItem( ) != null ) {
				InepTask task = this.getCmbTask( ).getSelectedItem( ).getValue( );
				params.put( "TASK_ID", task.getId( ).getId( ) );
			}
		}
		return params;
	}

	public InepRevisor getRevisor( )
	{
		if ( this.revisor == null ) {
			if ( this.getComboEvent( ).getSelectedItem( ) != null ) {
				this.revisor = this.getSession( ).getRevisor( (InepEvent) this.getComboEvent( ).getSelectedItem( ).getValue( ),
						this.getPrincipal( ) );
			}
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
		List<InepEvent> events = this.getSession( ).getEvents( this.getPrincipal( ) );

		if ( SysUtils.isEmpty( this.getComboEvent( ).getItems( ) ) == false ) {
			this.getComboEvent( ).getItems( ).clear( );
		}
		for ( InepEvent e : events ) {
			Comboitem item = this.getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( this.getComboEvent( ).getItemCount( ) > 0 ) {
			this.getComboEvent( ).setSelectedIndex( 0 );
			this.onSelectPackage( null );
		}
	}

	private void loadTasks( InepEvent event )
	{

		if ( this.getRevisor( ) == null ) {
			if ( this.getCmbTask( ) != null ) {
				ComboboxUtils.load( this.getCmbTask( ), this.getSession( ).getTasks( event ), null, true );
			}
		}
		else if ( this.getRevisor( ).getTask( ) != null ) {
			if ( this.getCmbTask( ) != null ) {
				ComboboxUtils.load( this.getCmbTask( ), this.getSession( ).getTasks( event ), this.getRevisor( ).getTask( ), true );
			}
		}
		else {
			this.loadRevisor( null );
		}
	}

	private void loadRevisor( InepTask task )
	{
		if ( this.getRevisor( ) == null ) {
			ComboboxUtils.load( this.getCmbRevisor( ), this.getSession( ).getTeam( task ), null, true );
		}
		else {
			if ( this.getRevisor( ).getTask( ) != null ) {
				ComboboxUtils.load( this.getCmbRevisor( ), this.getSession( ).getTeam( task ), this.getRevisor( ), true );
			}
			else {
				ComboboxUtils.load( this.getCmbRevisor( ), this.getSession( ).getOralTeam( this.getRevisor( ).getEvent( ) ), this.getRevisor( ), true );
			}
		}
		if ( this.getPrincipal( ).getUserId( ).equals( 1 ) ) {
			this.setReports( );
		}
	}

	@Listen( "onSelect = #cmbTask" )
	public void onSelectTask( Event evt )
	{
		this.loadRevisor( (InepTask) this.getCmbTask( ).getSelectedItem( ).getValue( ) );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onSelect = #cmbRevisor" )
	public void onSelectRevisor( Event evt )
	{
		this.setReports( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		Comboitem item = this.comboEvent.getSelectedItem( );
		if ( item != null ) {
			this.loadTasks( (InepEvent) this.getComboEvent( ).getSelectedItem( ).getValue( ) );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private Combobox getCmbTask( )
	{
		return this.cmbTask;
	}

	private Combobox getCmbRevisor( )
	{
		return this.cmbRevisor;
	}

}
