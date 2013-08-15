package br.com.mcampos.ejb.inep.media;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepMedia;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.media.Media;

/**
 * Session Bean implementation class InepMediaSessionBean
 */
@Stateless( mappedName = "InepMediaSession" )
@LocalBean
public class InepMediaSessionBean extends SimpleSessionBean<InepMedia> implements InepMediaSession, InepMediaSessionLocal
{
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
		return merge( inepMedia );

	}

}
