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

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.fdigital.upload.UploadSession;
import br.com.mcampos.jpa.fdigital.AnotoPen;
import br.com.mcampos.jpa.fdigital.AnotoPenPage;
import br.com.mcampos.jpa.fdigital.Pgc;
import br.com.mcampos.jpa.fdigital.PgcProperty;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;

import com.anoto.api.NoSuchPermissionException;
import com.anoto.api.Pen;
import com.anoto.api.PenHome;

public class PgcFile implements Serializable, Runnable
{
	private static final long serialVersionUID = -4000145716459202136L;

	// private static final short KEY_LOCATION_COORDINATES = 16386;
	private static final Logger logger = LoggerFactory.getLogger( PgcFile.class );

	// private final String imageFileTypeExtension = "jpg";

	// private List<MediaDTO> pgcs;
	// private String penId;
	private UploadSession session;
	private Pgc currentPgc;
	private Pen currentPen;
	// private PadFile pad;
	// private byte[ ] bytePgc;

	private MediaDTO pgc;
	private List<MediaDTO> medias;

	public PgcFile( )
	{
		super( );
	}

	public PgcFile( MediaDTO pgc, ArrayList<MediaDTO> medias ) throws IOException, NoSuchPermissionException, ApplicationException
	{
		this.setPgc( pgc );
		this.setMedias( medias );
	}

	@Override
	public void run( )
	{
		final long startTime = System.nanoTime( );
		logger.info( "Tread is running" );
		if ( this.getPgc( ) == null ) {
			return;
		}
		this.processPgcFile( );
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
				this.session = (UploadSession) ServiceLocator.getInstance( ).getRemoteSession( UploadSession.class, ServiceLocator.EJB_NAME[ 0 ] );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

	private boolean processPgcFile( )
	{
		// Pgc pgc = getSession( ).add( getPgc( ) );
		this.createPgcObject( );
		if ( this.getPen( ) != null && this.getCurrentPgc( ).getStatus( ).getId( ).equals( 1 ) ) {
			if ( this.getCurrentPgc( ).getStatus( ).getId( ).equals( 1 ) ) {
				this.getPageAddresess( );
			}
			if ( this.getCurrentPgc( ).getStatus( ).getId( ).equals( 1 ) ) {
				this.getProperties( );
			}
		}
		this.setCurrentPgc( this.getSession( ).persist( this.getCurrentPgc( ), this.getPgc( ) ) );
		return true;
	}

	private void createPgcObject( )
	{
		Pgc pgc = new Pgc( );
		pgc.setStatus( 1 );
		this.setCurrentPgc( pgc );
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
		ByteArrayInputStream is = new ByteArrayInputStream( this.getPgc( ).getObject( ) );
		Pen pen = null;
		try {
			pen = PenHome.read( is );
			if ( pen != null ) {
				this.setCurrentPen( pen );
				String penSerial;
				penSerial = this.getCurrentPen( ).getPenData( ).getPenSerial( );
				logger.info( "Pen Serial" + penSerial );
				this.getCurrentPgc( ).setPenId( penSerial );
				if ( this.existsPen( penSerial ) == false ) {
					this.getCurrentPgc( ).setStatus( 2 );
				}
			}
		}
		catch ( Exception e ) {
			logger.error( "Error getting pen", e );
			pen = null;
		}
		finally {
			if ( pen == null ) {
				this.getCurrentPgc( ).setStatus( 6 );
			}
		}
		return pen;
	}

	private boolean existsPen( String penId )
	{
		AnotoPen pen = this.getSession( ).getPen( penId );
		return pen != null ? true : false;
	}

	public Pen getCurrentPen( )
	{
		return this.currentPen;
	}

	private void setCurrentPen( Pen currentPen )
	{
		this.currentPen = currentPen;
	}

	private void getPageAddresess( )
	{
		logger.info( "getPageAddesses" );
		@SuppressWarnings( "rawtypes" )
		Iterator it = this.getCurrentPen( ).getPageAddresses( );
		List<String> addresses = null;
		while ( it != null && it.hasNext( ) ) {
			if ( addresses == null ) {
				addresses = new ArrayList<String>( );
			}
			addresses.add( (String) it.next( ) );
		}
		if ( SysUtils.isEmpty( addresses ) ) {
			this.getCurrentPgc( ).setStatus( 4 );
			logger.warn( "Pgc does not have any pages" );
			return;
		}
		List<AnotoPenPage> penPages = this.getSession( ).getPenPages( this.getCurrentPgc( ).getPenId( ), addresses );
		if ( SysUtils.isEmpty( penPages ) ) {
			this.getCurrentPgc( ).setStatus( 3 );
			logger.warn( "PenPage not found" );
			return;
		}
		this.getCurrentPgc( ).setPenPages( penPages );
	}

	private List<PgcProperty> getProperties( )
	{
		List<PgcProperty> properties = new ArrayList<PgcProperty>( );
		try {
			@SuppressWarnings( "rawtypes" )
			Iterator it = this.getCurrentPen( ).getPropertyIds( );
			while ( it != null && it.hasNext( ) ) {
				Short obj = (Short) it.next( );
				if ( obj != null ) {
					String[ ] values = this.getCurrentPen( ).getProperty( obj );
					if ( values != null && values.length > 0 ) {
						for ( String v : values ) {
							v = v.replaceAll( "'", "" );
							if ( SysUtils.isEmpty( v ) ) {
								continue;
							}
							PgcProperty p = new PgcProperty( );
							p.getId( ).setId( obj.intValue( ) );
							logger.info( "Adding property: " + obj + " - " + v );
							p.setValue( v );
							properties.add( p );
						}
					}
				}
			}
		}
		catch ( Exception e ) {
			logger.error( "Error getting property", e );
		}
		return properties;
	}

}
