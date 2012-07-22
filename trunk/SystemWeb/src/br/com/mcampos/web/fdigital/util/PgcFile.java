package br.com.mcampos.web.fdigital.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.fdigital.pgc.Pgc;
import br.com.mcampos.ejb.fdigital.upload.UploadSession;
import br.com.mcampos.web.locator.ServiceLocator;

import com.anoto.api.NoSuchPermissionException;
import com.anoto.api.Pen;
import com.anoto.api.PenHome;

public class PgcFile implements Serializable, Runnable
{
	private static final short KEY_LOCATION_COORDINATES = 16386;
	private static final Logger logger = LoggerFactory.getLogger( PgcFile.class );

	private final String imageFileTypeExtension = "jpg";

	private List<MediaDTO> pgcs;
	private String penId;
	private UploadSession session;
	private Pgc currentPgc;
	private Pen currentPen;
	private PadFile pad;
	private byte[ ] bytePgc;

	private MediaDTO pgc;
	private List<MediaDTO> medias;

	public PgcFile( )
	{
		super( );
	}

	public PgcFile( MediaDTO pgc, ArrayList<MediaDTO> medias ) throws IOException, NoSuchPermissionException, ApplicationException
	{
		setPgc( pgc );
		setMedias( medias );
	}

	@Override
	public void run( )
	{
		final long startTime = System.nanoTime( );
		logger.info( "Tread is running" );
		if ( getPgc( ) == null ) {
			return;
		}
		processPgcFile( );
		final long endTime = System.nanoTime( ) - startTime;
		logger.info( "Tread has finished its jobs in " + ( endTime / 1000000000.0 ) + "seconds" );
	}

	public MediaDTO getPgc( )
	{
		return this.pgc;
	}

	private void setPgc( MediaDTO pgc )
	{
		this.pgc = pgc;
	}

	public List<MediaDTO> getMedias( )
	{
		return this.medias;
	}

	private void setMedias( List<MediaDTO> medias )
	{
		this.medias = medias;
	}

	public UploadSession getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = (UploadSession) ServiceLocator.getInstance( ).getRemoteSession( UploadSession.class );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

	private boolean processPgcFile( )
	{
		Pgc pgc = getSession( ).add( getPgc( ) );
		if ( pgc == null ) {
			return false;
		}
		setCurrentPgc( pgc );
		if ( getPen( ) != null ) {
			List<String> pages = getPageAddresess( );
			for ( String page : pages ) {
				logger.info( "This pgc has page:" + page );
			}
		}
		setCurrentPgc( getSession( ).update( getCurrentPgc( ) ) );
		return true;
	}

	public Pgc getCurrentPgc( )
	{
		return this.currentPgc;
	}

	private void setCurrentPgc( Pgc currentPgc )
	{
		this.currentPgc = currentPgc;
	}

	public Pen getPen( )
	{
		ByteArrayInputStream is = new ByteArrayInputStream( getPgc( ).getObject( ) );
		Pen pen = null;
		try {
			pen = PenHome.read( is );
			if ( pen != null ) {
				setCurrentPen( pen );
				String penSerial;
				penSerial = getCurrentPen( ).getPenData( ).getPenSerial( );
				logger.info( "Pen Serial" + penSerial );
				getCurrentPgc( ).setPenId( penSerial );
			}
		}
		catch ( Exception e ) {
			logger.error( "Error getting pen", e );
			pen = null;
		}
		finally {
			if ( pen == null ) {
				getSession( ).setStatus( getCurrentPgc( ), 6 );
			}
		}
		return pen;
	}

	public Pen getCurrentPen( )
	{
		return this.currentPen;
	}

	private void setCurrentPen( Pen currentPen )
	{
		this.currentPen = currentPen;
	}

	private List<String> getPageAddresess( )
	{
		@SuppressWarnings( "rawtypes" )
		Iterator it = getCurrentPen( ).getPageAddresses( );
		List<String> addresses = null;
		while ( it != null && it.hasNext( ) ) {
			if ( addresses == null ) {
				addresses = new ArrayList<String>( );
			}
			addresses.add( (String) it.next( ) );
		}
		return addresses;
	}
}
