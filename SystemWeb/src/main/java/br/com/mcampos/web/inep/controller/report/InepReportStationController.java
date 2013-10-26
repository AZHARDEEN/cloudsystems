package br.com.mcampos.web.inep.controller.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.StationSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.core.report.ReportItem;
import br.com.mcampos.web.core.report.ReportListRenderer;
import br.com.mcampos.zkutils.ReportEvent;

public class InepReportStationController extends BaseDBLoggedController<StationSession>
{
	private static final long serialVersionUID = -8596262655603307170L;

	@Wire( "#listReport" )
	private Listbox listbox;

	@Wire
	private Combobox comboEvent;

	protected void setReports( )
	{
		List<ReportItem> list = new ArrayList<ReportItem>( );
		ReportItem item;

		item = new ReportItem( "Listagem Geral de Inscrições do Posto" );
		item.setReportUrl( "/reports/inep/oral_station_001" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Listagem Geral de Ausentes do Posto" );
		item.setReportUrl( "/reports/inep/oral_station_002" );
		item.setParams( this.configReportParams( ) );
		list.add( item );

		item = new ReportItem( "Listagem Geral de Inscrições Sem Nota" );
		item.setReportUrl( "/reports/inep/oral_station_003" );
		item.setParams( this.configReportParams( ) );
		list.add( item );
		
		item = new ReportItem( "Listagem Geral de Audios Carregados" );
		item.setReportUrl( "/reports/inep/oral_station_004" );
		item.setParams( this.configReportParams( ) );
		list.add( item );
		
		item = new ReportItem( "Listagem Geral de Inscrições Com Nota e Sem Audio" );
		item.setReportUrl( "/reports/inep/oral_station_005" );
		item.setParams( this.configReportParams( ) );
		list.add( item );
		

		if ( this.getListbox( ) != null ) {
			this.getListbox( ).setModel( new ListModelList<ReportItem>( list ) );
		}
	}

	@Override
	protected Class<StationSession> getSessionClass( )
	{
		return StationSession.class;
	}

	@Listen( "onClick=#cmdSubmit" )
	public void onOK( Event evt )
	{
		Listitem item = this.getListbox( ).getSelectedItem( );
		if ( item != null ) {
			ReportItem r = (ReportItem) item.getValue( );
			r.setReportFormat( 1 );
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
		this.loadCombobox( );
		this.setReports( );
	}

	@Override
	protected Map<String, Object> configReportParams( )
	{
		Map<String, Object> params = super.configReportParams( );

		InepEvent event = (InepEvent) ( this.getComboEvent( ).getSelectedItem( ).getValue( ) );
		if ( event != null ) {
			params.put( "EVENT_ID", event.getId( ).getId( ) );
		}
		params.put( "COLLABORATOR_ID", this.getPrincipal( ).getSequence( ) );
		params.put( "COMPANY_ID", this.getPrincipal( ).getCompanyID( ) );
		return params;
	}

	public Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	private void loadCombobox( )
	{
		InepEvent event = this.getSession( ).getCurrentEvent( this.getPrincipal( ) );

		if ( SysUtils.isEmpty( this.getComboEvent( ).getItems( ) ) == false ) {
			this.getComboEvent( ).getItems( ).clear( );
		}
		Comboitem item = this.getComboEvent( ).appendItem( event.getDescription( ) );
		item.setValue( event );
		if ( this.getComboEvent( ).getItemCount( ) > 0 ) {
			this.getComboEvent( ).setSelectedIndex( 0 );
		}
	}

}
