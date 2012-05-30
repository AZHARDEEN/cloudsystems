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
import java.util.Properties;

import br.com.mcampos.sysutils.SysUtils;

public class TestLoader
{

	public static final String path = "T:/temp/inep/";
	public static void main( String[ ] args )
	{
		Connection connection = null;
		try {
			connection = getConnection( );
			connection.setAutoCommit( false );
			insertFiles( getFilesToInsert( ), connection );
			connection.commit( );
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
		String url = "jdbc:postgresql://localhost/db_cloud";
		Properties props = new Properties( );
		props.setProperty( "user", "cloud" );
		props.setProperty( "password", "cloud" );
		return DriverManager.getConnection( url, props );
	}

	static public String[] getFilesToInsert ()
	{
		File dir = new File( path );
		String[] children = null;

		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				name = name.toLowerCase( );
				return !name.startsWith( "." ) && name.endsWith( ".pdf" );
			}
		};
		children = dir.list( filter );
		return children;
	}

	static public void insertFiles( String[ ] files, Connection conn ) throws SQLException, NumberFormatException,
	FileNotFoundException
	{
		byte[ ] blob;
		String[ ] fileParts;

		if ( files == null || conn == null || conn.isClosed( ) ) {
			return;
		}
		for ( String item : files ) {
			System.out.println( "Inserindo " + item );
			blob = read( item );
			fileParts = item.split( "-" );
			insertIntoDatabase( item, blob, fileParts[ 0 ], Integer.parseInt( fileParts[ 1 ] ), conn );
			System.out.println( ":) " + item + "Inserido com sucesso" );
		}
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
		if ( SysUtils.isEmpty( s ) ) {
			stmt.execute( "INSERT INTO inep.inep_subscription VALUES ( 13623, 1, '" + subscription + "')" );
			s = subscription;
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