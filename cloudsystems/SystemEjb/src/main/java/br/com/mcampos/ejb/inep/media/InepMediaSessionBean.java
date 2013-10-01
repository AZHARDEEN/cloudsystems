package br.com.mcampos.ejb.inep.media;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.jpa.system.Media;

/**
 * Session Bean implementation class InepMediaSessionBean
 */
@Stateless( mappedName = "InepMediaSession" )
@LocalBean
public class InepMediaSessionBean extends SimpleSessionBean<InepMedia> implements InepMediaSession, InepMediaSessionLocal
{
	private static final long serialVersionUID = 5074675431050412230L;

	@EJB
	private MediaSessionBeanLocal mediaSession;

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
	public InepMedia addPDF( InepTest test, String name, byte[ ] object )
	{
		Media media = new Media( );
		media.setName( name + ".pdf" );
		media.setFormat( "pdf" );
		media.setMimeType( "text/pdf" );
		media.setObject( object );
		media.setInsertDate( new Date( ) );
		media = this.mediaSession.add( media );

		InepMedia inepMedia = new InepMedia( test.getSubscription( ) );
		inepMedia.setMedia( media );
		inepMedia.setTask( test.getId( ).getTaskId( ) );
		inepMedia.setType( InepMedia.TYPE_TEST );
		test.getSubscription( ).add( inepMedia );
		return this.add( inepMedia );
	}

}
