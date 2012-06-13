package br.com.mcampos.web.core.report;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.com.mcampos.web.core.event.IDialogEvent;

public class BaseReportWindow extends Window
{
	private static final long serialVersionUID = -6857383444036220646L;
	public static final String reportTemplateName = "/templates/report.zul";

	private IDialogEvent callEvent;
	private Listbox listbox;
	private Button btnOk;
	private Combobox cmbFormat;

	public IDialogEvent getCallEvent( )
	{
		return this.callEvent;
	}

	public void setCallEvent( IDialogEvent callEvent )
	{
		this.callEvent = callEvent;
	}

	public void onOK( Event evt )
	{
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item != null ) {
			ReportItem r = (ReportItem) item.getValue( );
			if ( getCmbFormat( ) != null && getCmbFormat( ).getSelectedItem( ) != null ) {
				String value = (String) getCmbFormat( ).getSelectedItem( ).getValue( );
				r.setReportFormat( Integer.parseInt( value ) );
			}
			pushSessionParameter( JasperReportController.paramName, r );
			Executions.getCurrent( ).sendRedirect( "/private/report.zul", "_blank" );
			onCancel( evt );
		}
	}

	public void onCancel( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		onClose( );
	}

	public void onCreate( Event evt )
	{
		if ( getListbox( ) != null ) {
			this.listbox.setItemRenderer( new ReportListRenderer( ) );
		}
		if ( getCmbFormat( ) != null ) {
			if ( getCmbFormat( ).getItemCount( ) > 0 ) {
				getCmbFormat( ).setSelectedIndex( 0 );
			}
		}
		configListener( );
	}

	public void setReports( List<ReportItem> list )
	{
		if ( getListbox( ) != null ) {
			getListbox( ).setModel( new ListModelList<ReportItem>( list ) );
		}
	}

	public Listbox getListbox( )
	{
		if ( this.listbox == null ) {
			this.listbox = (Listbox) getFellow( "listReport" );
		}
		return this.listbox;
	}

	public Button getBtnOk( )
	{
		if ( this.btnOk == null ) {
			this.btnOk = (Button) getFellow( "cmdSubmit" );
		}
		return this.btnOk;
	}

	protected void onSelect( Event evt )
	{
		getBtnOk( ).setDisabled( false );
	}

	private void configListener( )
	{
		getListbox( ).addEventListener( Events.ON_SELECT, new EventListener<Event>( )
		{
			@Override
			public void onEvent( Event evt )
			{
				onSelect( evt );
			}
		} );
		getListbox( ).addEventListener( Events.ON_DOUBLE_CLICK, new EventListener<Event>( )
		{
			@Override
			public void onEvent( Event evt )
			{
				onOK( evt );
			}
		} );
	}

	public void pushSessionParameter( String name, Object value )
	{
		Sessions.getCurrent( ).setAttribute( name, value );
	}

	public Object popSessionParameter( String name )
	{
		Object obj = ( Sessions.getCurrent( ) != null ) ? Sessions.getCurrent( ).getAttribute( name ) : null;
		Sessions.getCurrent( ).setAttribute( name, null );
		return obj;
	}

	public Combobox getCmbFormat( )
	{
		if ( this.cmbFormat == null ) {
			this.cmbFormat = (Combobox) getFellow( "exportFormat" );
		}
		return this.cmbFormat;
	}

}
