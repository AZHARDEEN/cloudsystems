package br.com.mcampos.web.core.report;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.event.IDialogEvent;

public class BaseReportWindow extends Window
{
	private static final long serialVersionUID = -6857383444036220646L;
	public static final String reportTemplateName = "/templates/report.zul";
	private static final Logger logger = LoggerFactory.getLogger( BaseReportWindow.class );

	private IDialogEvent callEvent;
	private Listbox listbox;
	private Button btnOk;
	private Combobox cmbFormat;

	public IDialogEvent getCallEvent( )
	{
		return callEvent;
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
			listbox.setItemRenderer( new ReportListRenderer( ) );
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
		if ( listbox == null ) {
			listbox = (Listbox) getFellow( "listReport" );
		}
		return listbox;
	}

	public Button getBtnOk( )
	{
		if ( btnOk == null ) {
			btnOk = (Button) getFellow( "cmdSubmit" );
		}
		return btnOk;
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
		if ( cmbFormat == null ) {
			cmbFormat = (Combobox) getFellow( "exportFormat" );
		}
		return cmbFormat;
	}

	private void doReport( ReportItem item ) throws JRException
	{
		/*
		pushSessionParameter( JasperReportController.paramName, item );
		Executions.getCurrent( ).sendRedirect( JDBCReportServlet.reportUrl, "_blank" );
		*/

		JasperPrint print = compileReport( item.getReportUrl( ), item.getParams( ) );
		if ( print != null ) {
			JasperViewer.viewReport( print );
		}

	}

	protected JasperPrint compileReport( String report, Map<String, Object> params )
	{
		if ( report == null ) {
			return null;
		}
		if ( params == null ) {
			params = new HashMap<String, Object>( );
		}
		String realPath;

		realPath = getRealPath( "/img" );
		params.put( "IMAGE_PATH", realPath );

		Connection conn = null;
		try {
			realPath = getRealPath( report );
			File file = new File( realPath );
			String name = SysUtils.getExtension( file );
			if ( SysUtils.isEmpty( name ) ) {
				name = realPath + ".jrxml";
			}
			else {
				name = realPath;
			}
			conn = getConnection( );
			logger.info( "Compiling report: " + report );
			JasperReport jasperReport = JasperCompileManager.compileReport( name );
			JasperPrint nRet = JasperFillManager.fillReport( jasperReport, params, conn );
			return nRet;
		}
		catch ( JRException e ) {
			logger.error( "JRException", e );
		}
		catch ( SQLException e ) {
			logger.error( "JRException", e );
		}
		catch ( ClassNotFoundException e ) {
			logger.error( "JRException", e );
		}
		catch ( Exception e ) {
			logger.error( "JRException", e );
		}
		finally {
			try {
				if ( conn != null && conn.isClosed( ) == false ) {
					conn.close( );
					conn = null;
				}
			}
			catch ( SQLException e ) {
				e.printStackTrace( );
			}
		}
		return null;
	}

	protected String getRealPath( String uri )
	{
		return Executions.getCurrent( ).getDesktop( ).getWebApp( ).getRealPath( uri );
	}

	protected Connection getConnection( ) throws Exception
	{
		InitialContext cxt = new InitialContext( );
		DataSource ds = (DataSource) cxt.lookup( "java:/ReportDS" );
		if ( ds != null ) {
			return ds.getConnection( );
		}
		Class.forName( "org.postgresql.Driver" );
		String url = "jdbc:postgresql://69.59.21.123:5500/inep";
		Properties props = new Properties( );
		props.setProperty( "user", "jreport" );
		props.setProperty( "password", "jreport" );
		return DriverManager.getConnection( url, props );
	}

}
