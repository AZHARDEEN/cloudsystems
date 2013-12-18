package br.com.mcampos.web.inep.controller.report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.reporting.BaseSubscriptionDTO;
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.core.report.JasperReportController;

public class InterfacesController extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = 984742122241722367L;
	private DataSource dataSource;
	private static final Logger LOGGER = LoggerFactory.getLogger( JasperReportController.class );

	@Wire
	private Listbox reportList;

	@Wire
	private Combobox comboEvent;

	@Override
	protected Class<TeamSession> getSessionClass( )
	{
		return TeamSession.class;
	}

	@Listen( "onClick = #btnExport " )
	public void onExport( Event evt )
	{
		Listitem item = reportList.getSelectedItem( );
		if( item == null ) {
			showErrorMessage( "Selecione um item da lista para exportar", "Exportar Arquivos de Interface" );
			return;
		}
		Comboitem itemEvent = getComboEvent( ).getSelectedItem( );
		Integer report = Integer.parseInt( (String) item.getValue( ) );
		if( report.equals( 4 ) || report.equals( 3 ) || report.equals( 5 ) ) {
			report( (InepEvent) itemEvent.getValue( ), report );
		}
		else {
			List<BaseSubscriptionDTO> list = getSession( ).report( (InepEvent) itemEvent.getValue( ), report );
			exportReport( list );
		}
		if( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private void exportReport( List<BaseSubscriptionDTO> list )
	{
		if( SysUtils.isEmpty( list ) ) {
			return;
		}
		StringBuffer buffer = new StringBuffer( list.get( 0 ).getHeader( ) );
		buffer.append( "\n" );
		for( BaseSubscriptionDTO item : list ) {
			String[ ] fields = item.getFields( );
			boolean first = true;
			for( String field : fields )
			{
				if( !first ) {
					buffer.append( ";" );
				}
				buffer.append( field );
				first = false;
			}
			buffer.append( "\n" );
		}
		Integer report = Integer.parseInt( (String) reportList.getSelectedItem( ).getValue( ) );
		Filedownload.save( buffer.toString( ), "text/plain", "report_" + report.toString( ) + ".txt" );
	}

	private Combobox getComboEvent( )
	{
		return comboEvent;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		loadCombobox( );
	}

	private void loadCombobox( )
	{
		List<InepEvent> events = getSession( ).getEvents( getPrincipal( ) );

		if( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for( InepEvent e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
		}
	}

	private Connection getConnection( ) throws Exception
	{
		LOGGER.info( "Aquiring Connection on Interfaces Controller " + (dataSource != null ? " Done" : "Error") );
		return getDatasource( ).getConnection( );
	}

	private DataSource getDatasource( ) throws NamingException
	{
		if( dataSource == null ) {
			InitialContext cxt = new InitialContext( );
			dataSource = (DataSource) cxt.lookup( "jdbc/ReportDS" );
			LOGGER.info( "Aquiring Datasource on Interfaces Controller " + (dataSource != null ? " Done" : "Error") );
		}
		return dataSource;
	}

	private void report( InepEvent event, Integer report )
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rSet = null;
		int nColumns = 0;
		StringBuffer buffer = new StringBuffer( getHeader( report ) );
		try {
			conn = getConnection( );
			stmt = conn.createStatement( );
			stmt.execute( getSqlToProcess( event, report ) );
			rSet = stmt.getResultSet( );
			while( rSet.next( ) ) {
				if( nColumns == 0 ) {
					nColumns = rSet.getMetaData( ).getColumnCount( );
				}
				for( int nIndex = 1; nIndex <= nColumns; nIndex++ ) {
					if( nIndex != 1 ) {
						buffer.append( ";" );
					}
					buffer.append( rSet.getString( nIndex ) );
				}
				buffer.append( "\n" );
			}
		}
		catch( Exception e ) {
			LOGGER.error( "Error processing  report Interfaces Controller", e );
		}
		finally {
			try {
				if( rSet != null && !rSet.isClosed( ) ) {
					rSet.close( );
				}
				if( stmt != null && !stmt.isClosed( ) ) {
					stmt.close( );
				}
				if( conn != null && !conn.isClosed( ) ) {
					LOGGER.info( "Closing Connection on Interfaces Controller " + (dataSource != null ? " Done" : "Error") );
					conn.close( );
				}
			}
			catch( SQLException e ) {
				LOGGER.error( "Error closing connection on Interfaces Controller", e );
			}
		}
		Filedownload.save( buffer.toString( ), "text/plain", "report_" + report.toString( ) + ".txt" );
	}

	private String getSqlToProcess( InepEvent event, Integer report )
	{
		String sql = null;

		switch( report ) {
		case 3:
			sql = "select " +
					"d.isc_id_ch subscription, " +
					"d.tsk_id_in task, " +
					"case when d.dis_grade_in > 5 then 0 else d.dis_grade_in end grade " +
					"from " +
					"inep.inep_distribution d inner join inep.inep_revisor r on ( d.usr_id_in = r.usr_id_in and d.pct_id_in = r.pct_id_in " +
					"and d.tsk_id_in = r.tsk_id_in and d.col_seq_in = r.col_seq_in  ) " +
					"where " +
					"d.usr_id_in = " + event.getId( ).getCompanyId( ).toString( ) +
					"and 	d.pct_id_in = " + event.getId( ).getId( ).toString( ) +
					"and 	d.dis_golden_bt is false " +
					"and 	r.rvs_coordinator_bt is true " +
					"order by 1, 2";
			break;
		case 4:
			sql = "select " +
					"d.isc_id_ch subscription, " +
					"d.tsk_id_in task, " +
					"ud.udc_code_ch cpf, " +
					"case when d.dis_grade_in > 5 then 0 else d.dis_grade_in end grade " +
					"from " +
					"inep.inep_distribution d inner join inep.inep_revisor r on ( d.usr_id_in = r.usr_id_in and d.pct_id_in = r.pct_id_in " +
					"and d.tsk_id_in = r.tsk_id_in and d.col_seq_in = r.col_seq_in  ) " +
					"inner join collaborator c on ( r.usr_id_in = c.usr_id_in and r.col_seq_in = c.col_seq_in ) " +
					"inner join user_document ud on ( c.col_id_in = ud.usr_id_in ) " +
					"where " +
					"d.usr_id_in = " + event.getId( ).getCompanyId( ).toString( ) +
					"and 	d.pct_id_in = " + event.getId( ).getId( ).toString( ) +
					"and 	d.dis_golden_bt is false " +
					"and 	ud.doc_id_in = 1 " +
					"and 	r.rvs_coordinator_bt is false " +
					"order by 1, 2, 3";
			break;
		case 5:
			sql = "select "
					+
					"t.isc_id_ch subscription, "
					+
					"trunc ( coalesce ( iot_agreement2_grade_nm, iot_agreement_grade_in )) grade "
					+
					"from  "
					+
					"inep.inep_oral_test t inner join inep.inep_subscription s on ( t.usr_id_in = s.usr_id_in and t.pct_id_in = s.pct_id_in and t.isc_id_ch = s.isc_id_ch ) "
					+
					"where t.usr_id_in = " + event.getId( ).getCompanyId( ).toString( ) +
					"and t.pct_id_in = " + event.getId( ).getId( ).toString( ) +
					"and ( iot_agreement2_grade_nm is not null or iot_agreement_grade_in is not null )  ";
			break;
		}
		return sql;
	}

	private String getHeader( Integer report )
	{
		String header = null;
		switch( report ) {
		case 3:
			header = "Inscrição;Tarefa;NotaConsenso\n";
			break;
		case 4:
			header = "Inscrição;Tarefa;CPF;Nota\n";
			break;
		case 5:
			header = "Inscrição;NotaReavaliacao\n";
			break;
		default:
			header = "Undefined";
			break;
		}
		return header;
	}

}
