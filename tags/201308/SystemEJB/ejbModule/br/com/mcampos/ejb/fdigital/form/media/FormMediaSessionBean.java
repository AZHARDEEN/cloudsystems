package br.com.mcampos.ejb.fdigital.form.media;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;

/**
 * Session Bean implementation class FormMediaSessionBean
 */
@Stateless( name = "FormMediaSession", mappedName = "FormMediaSession" )
@LocalBean
public class FormMediaSessionBean extends SimpleSessionBean<FormMedia> implements FormMediaSessionLocal
{
	@EJB
	MediaSessionBeanLocal mediaSession;

	@Override
	protected Class<FormMedia> getEntityClass( )
	{
		return FormMedia.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.mcampos.ejb.core.BaseSessionBean#remove(java.lang.Object)
	 */
	@Override
	public FormMedia remove( FormMedia entity )
	{
		if ( entity == null )
			return null;
		Media media = entity.getMedia( );
		if ( media != null )
			mediaSession.remove( media );
		return super.remove( entity );
	}

}
