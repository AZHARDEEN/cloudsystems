package br.com.mcampos.ejb.inep.media;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.test.InepTestSessionLocal;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepMediaPK;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.jpa.system.FileUpload;
import br.com.mcampos.jpa.system.Media;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class InepMediaSessionBean
 */
@Stateless( mappedName = "InepMediaSession" )
@LocalBean
public class InepMediaSessionBean extends SimpleSessionBean<InepMedia> implements InepMediaSession, InepMediaSessionLocal
{
	private static final long serialVersionUID = 5074675431050412230L;
	private static final Logger LOGGER = LoggerFactory.getLogger( InepMediaSessionBean.class.getSimpleName( ) );

	@EJB
	private MediaSessionBeanLocal mediaSession;

	@EJB
	private InepTestSessionLocal testSession;

	@Override
	protected Class<InepMedia> getEntityClass( )
	{
		return InepMedia.class;
	}

	@Override
	public InepMedia addAudio( InepSubscription isc, Media media )
	{
		InepMedia inepMedia = new InepMedia( isc );
		inepMedia.setMedia( media );
		inepMedia.setTask( null );
		inepMedia = this.add( inepMedia );
		inepMedia.setType( InepMedia.TYPE_AUDIO );
		isc.add( inepMedia );
		return inepMedia;

	}

	@Override
	public void removeAudio( InepSubscription isc )
	{
		Query query = getEntityManager( ).createQuery( "delete from InepMedia o where o.subscription = ?1 and o.type = 2" ).setParameter( 1, isc );
		query.executeUpdate( );
	}

	@Override
	public InepMedia addPDF( InepTest test, String name, byte[ ] object )
	{
		Media media = new Media( );
		media.setName( name + ".pdf" );
		media.setFormat( "pdf" );
		media.setMimeType( "text/pdf" );
		media.setObject( object );
		media.setInsertDate( new Date( ) );
		media = mediaSession.add( media );

		InepMedia inepMedia = new InepMedia( test.getSubscription( ) );
		inepMedia.setMedia( media );
		inepMedia.setTask( test.getId( ).getTaskId( ) );
		inepMedia.setType( InepMedia.TYPE_TEST );
		test.getSubscription( ).add( inepMedia );
		return this.add( inepMedia );
	}

	@Override
	public InepMedia addPDF( InepTest test, String name, FileUpload f )
	{
		InepMediaPK key = new InepMediaPK( test.getSubscription( ).getId( ) );
		key.setMediaId( f.getMediaId( ) );
		InepMedia inepMedia = get( key );
		if ( inepMedia != null ) {
			return inepMedia;
		}
		InepTest merged = testSession.get( test.getId( ) );
		try {
			if( !SysUtils.isEmpty( merged.getSubscription( ).getMedias( ) ) ) {
				for( InepMedia m : merged.getSubscription( ).getMedias( ) ) {
					if( test.getTask( ).equals( m.getTask( ) ) ) {
						LOGGER.info( "Changing Media from " + m.getMedia( ).getId( ) + " to " + f.getMediaId( ) );
						m.setMedia( f.getMedia( ) );
						return m;
					}
				}
			}
		}
		catch ( Exception e ) {
			LOGGER.error( "Error trying to find media for subscription " + merged.getSubscription( ).getId( ).getId( ) + " and Task "
					+ merged.getId( ).getTaskId( ), e );
		}
		inepMedia = new InepMedia( merged.getSubscription( ) );
		inepMedia.setMedia( f.getMedia( ) );
		inepMedia.setTask( merged.getId( ).getTaskId( ) );
		inepMedia.setType( InepMedia.TYPE_TEST );
		merged.getSubscription( ).add( inepMedia );
		inepMedia = this.add( inepMedia );
		return inepMedia;
	}
}
