package br.com.mcampos.batch.inep;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLoader
{

	public static final String path = "T:/temp/inep/";
	private static final Logger logger = LoggerFactory.getLogger( TestLoader.class.getSimpleName( ) );

	public static void main( String[ ] args )
	{
		Connection connection = null;
		try {
			connection = getConnection( );
			connection.setAutoCommit( false );
			// insertFiles( getFilesToInsert( ), connection );
			Integer targets[] = { 47, 114138, 28, 114153 };
			for ( int target : targets ) {
				redistributeFrom( target, connection );
				connection.commit( );
			}
			connection.close( );
		}
		catch ( Exception e ) {
			e.printStackTrace( );
			try {
				if ( connection != null ) {
					connection.rollback( );
					connection.close( );
				}
			}
			catch ( Exception ex ) {
				ex = null;
			}
		}
	}

	static public Connection getConnection( ) throws SQLException, ClassNotFoundException
	{
		Class.forName( "org.postgresql.Driver" );
		String url = "jdbc:postgresql://69.59.21.123:5500/inep";
		Properties props = new Properties( );
		props.setProperty( "user", "cloud" );
		props.setProperty( "password", "cloud" );
		return DriverManager.getConnection( url, props );
	}

	static public String[ ] getFilesToInsert( )
	{
		File dir = new File( path );
		String[ ] children = null;

		FilenameFilter filter = new FilenameFilter( )
		{
			@Override
			public boolean accept( File dir, String name )
			{
				name = name.toLowerCase( );
				return !name.startsWith( "." ) && name.endsWith( ".pdf" );
			}
		};
		children = dir.list( filter );
		return children;
	}

	static public void redistributeFrom( Integer from, Connection conn ) throws SQLException
	{
		Statement stmt = conn.createStatement( );
		int task;
		ArrayList<Integer> others = new ArrayList<Integer>( );
		String sql;
		int nIndex = 0;

		logger.info( "Getting task for revisor " + from );
		sql = "SELECT distinct tsk_id_in FROM INEP.INEP_DISTRIBUTION WHERE COL_SEQ_IN = " + from
				+ " AND IDS_ID_IN = 1 AND USR_ID_IN = 13623 AND PCT_ID_IN = 1";
		ResultSet rset = stmt.executeQuery( sql );
		if ( rset.next( ) ) {
			task = rset.getInt( 1 );
			logger.info( "Got task " + task );
			rset.close( );
			logger.info( "Getting others revisors" );
			sql = "select col_seq_in from inep.inep_revisor where  COL_SEQ_IN <> " + from + " AND USR_ID_IN = 13623 AND PCT_ID_IN = 1 and tsk_id_in = "
					+ task + " and rvs_coordinator_bt is false";
			rset = stmt.executeQuery( sql );
			while ( rset.next( ) ) {
				others.add( rset.getInt( 1 ) );
			}
			rset.close( );
			logger.info( "Found " + others.size( ) + " Revisors" );
			if ( others.size( ) > 0 ) {
				sql = "SELECT isc_id_ch, dis_priority_in FROM INEP.INEP_DISTRIBUTION WHERE COL_SEQ_IN = " + from
						+ " AND IDS_ID_IN = 1 AND USR_ID_IN = 13623 AND PCT_ID_IN = 1";
				rset = stmt.executeQuery( sql );
				int other = 0;
				while ( rset.next( ) ) {
					String test = rset.getString( 1 );
					Integer priority = rset.getInt( 2 );
					other = others.get( nIndex );
					nIndex++;
					if ( nIndex >= others.size( ) ) {
						nIndex = 0;
					}
					while ( canDistributeTo( conn, test, other ) == false ) {
						other = others.get( nIndex );
						nIndex++;
						if ( nIndex >= others.size( ) ) {
							nIndex = 0;
						}
					}
					logger.info( "Redistributing " + test + " to " + other + " from task " + task );
					distribute( conn, test, task, from, other, priority );
				}
			}
			rset.close( );
		}
		else {
			logger.info( "Error getting task" );
		}
		stmt.close( );
	}

	static boolean canDistributeTo( Connection conn, String test, Integer other ) throws SQLException
	{
		Statement stmt = conn.createStatement( );
		String sql;
		boolean bRet = true;

		sql = "select 1 from inep.inep_distribution where USR_ID_IN = 13623 AND PCT_ID_IN = 1 AND COL_SEQ_IN = "
				+ other + " AND ISC_ID_CH = '" + test + "'";
		ResultSet rset = stmt.executeQuery( sql );
		if ( rset.next( ) ) {
			bRet = false;
		}
		rset.close( );
		stmt.close( );
		return bRet;
	}

	static boolean distribute( Connection conn, String test, Integer task, Integer from, Integer other, Integer priority ) throws SQLException
	{
		Statement stmt = conn.createStatement( );
		String sql;
		boolean bRet = true;

		sql = String.format( "insert into inep.inep_distribution values ( 13623, 1, '%s', %d, %d, 1, null, null, now(), null, %d ) ",
				test, task, other, priority );
		stmt.executeUpdate( sql );
		sql = String.format( "delete from inep.inep_distribution where usr_id_in = 13623 and pct_id_in = 1 and isc_id_ch = '%s' and tsk_id_in = %d " +
				" and col_seq_in = %d", test, task, from );
		stmt.executeUpdate( sql );
		stmt.close( );
		return bRet;
	}

	static public void insertFiles( String[ ] files, Connection conn ) throws SQLException, NumberFormatException,
			FileNotFoundException
	{
		Statement stmt = conn.createStatement( );
		PreparedStatement pStmt;
		ArrayList<Integer> revisorList = new ArrayList<Integer>( );
		ArrayList<String> testList = new ArrayList<String>( );

		for ( int task = 1; task <= 4; task++ ) {
			logger.info( "TASK " + task );
			ResultSet rset = stmt
					.executeQuery( "select col_seq_in from inep.inep_revisor where usr_id_In = 13623 and rvs_coordinator_bt = false and tsk_id_in = "
							+ task );
			while ( rset.next( ) ) {
				revisorList.add( rset.getInt( "col_seq_in" ) );
			}
			rset.close( );
			rset = stmt.executeQuery( "select isc_id_ch from inep.inep_test where usr_id_in = 13623 and tsk_id_in = " + task );
			while ( rset.next( ) ) {
				testList.add( rset.getString( "isc_id_ch" ) );
			}
			rset.close( );
			int revIndex = 0;
			logger.info( "Running " + task );
			for ( int nIndex = 0; nIndex < testList.size( ); nIndex++ ) {
				if ( nIndex % 100 == 0 && nIndex > 0 ) {
					logger.info( "Partial!!!!! " + task + "- " + nIndex );
				}
				pStmt = conn
						.prepareStatement( "INSERT INTO inep.inep_distribution( usr_id_in, pct_id_in, isc_id_ch, tsk_id_in, col_seq_in, ids_id_in )"
								+
								"VALUES (13623,1,?,?,?,1) " );
				pStmt.setString( 1, testList.get( nIndex ) );
				pStmt.setInt( 2, task );
				pStmt.setInt( 3, revisorList.get( revIndex ) );
				pStmt.execute( );
				pStmt.close( );
				revIndex++;
				if ( revIndex >= revisorList.size( ) ) {
					revIndex = 0;
				}
				pStmt = conn
						.prepareStatement( "INSERT INTO inep.inep_distribution( usr_id_in, pct_id_in, isc_id_ch, tsk_id_in, col_seq_in, ids_id_in )"
								+
								"VALUES (13623,1,?,?,?,1) " );
				pStmt.setString( 1, testList.get( nIndex ) );
				pStmt.setInt( 2, task );
				pStmt.setInt( 3, revisorList.get( revIndex ) );
				pStmt.execute( );
				pStmt.close( );
				revIndex++;
				if ( revIndex >= revisorList.size( ) ) {
					revIndex = 0;
				}
			}
			logger.info( "Donne!!!!! " + task );
		}
		return;
	}

	static public byte[ ] read( String aInputFileName )
	{
		File file = new File( path + aInputFileName );
		byte[ ] result = new byte[ (int) file.length( ) ];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream( new FileInputStream( file ) );
				while ( totalBytesRead < result.length ) {
					int bytesRemaining = result.length - totalBytesRead;
					// input.read() returns -1, 0, or more :
					int bytesRead = input.read( result, totalBytesRead, bytesRemaining );
					if ( bytesRead > 0 ) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
				/*
				 * the above style is a bit tricky: it places bytes into the
				 * 'result' array; 'result' is an output parameter; the while
				 * loop usually has a single iteration only.
				 */
			}
			finally {
				input.close( );
			}
		}
		catch ( FileNotFoundException ex ) {
			ex = null;
		}
		catch ( IOException ex ) {
			ex = null;
		}
		return result;
	}

	static public void insertIntoDatabase( String filename, byte[ ] blob, String subscription, Integer task, Connection conn )
			throws SQLException, FileNotFoundException
	{
		subscription = getSubscription( conn, subscription );
		Long idMedia = insertMedia( conn, blob, filename );
		PreparedStatement pStmt;
		pStmt = conn
				.prepareStatement( "INSERT INTO inep.inep_test( usr_id_in, pct_id_in, isc_id_ch, tsk_id_in, med_id_in) VALUES (?, ?, ?, ?, ?)" );
		pStmt.setInt( 1, 13623 );
		pStmt.setInt( 2, 1 );
		pStmt.setString( 3, subscription );
		pStmt.setInt( 4, task );
		pStmt.setLong( 5, idMedia );
		pStmt.execute( );
		if ( pStmt != null ) {
			pStmt.close( );
		}
	}

	static public String getSubscription( Connection conn, String subscription ) throws SQLException
	{
		String s = null;
		Statement stmt = conn.createStatement( );
		ResultSet rSet = stmt
				.executeQuery( "select * from inep.inep_subscription where usr_id_in = 13623 and pct_id_in = 1 and isc_id_ch = '"
						+ subscription + "'" );

		if ( rSet.next( ) ) {
			s = rSet.getString( "isc_id_ch" );
		}
		rSet.close( );
		return s;
	}

	static public Long insertMedia( Connection conn, byte[ ] buf, String filename ) throws SQLException, FileNotFoundException
	{
		Long id = 0L;
		Statement stmt = conn.createStatement( );
		PreparedStatement pStmt;
		ResultSet rSet = stmt.executeQuery( "select nextval ( 'seq_media' )" );

		if ( rSet.next( ) ) {
			id = rSet.getLong( 1 );
		}
		if ( id.equals( 0L ) == false ) {
			String sql = "INSERT INTO media "
					+ "( med_id_in, med_name_ch, med_mime_ch, med_object_bin, med_size_in, med_format_ch ) "
					+ "VALUES ( ?, ?, 'text/pdf', ?, ?, 'pdf' ) ";

			pStmt = conn.prepareStatement( sql );
			pStmt.setLong( 1, id );
			pStmt.setString( 2, filename );
			File file = new File( path + filename );
			pStmt.setBytes( 3, buf );
			pStmt.setInt( 4, (int) file.length( ) );
			pStmt.execute( );
		}
		rSet.close( );
		return id;
	}

}