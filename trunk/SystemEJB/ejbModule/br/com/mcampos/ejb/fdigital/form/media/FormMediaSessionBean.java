package br.com.mcampos.ejb.fdigital.form.media;

import java.io.Serializable;
import java.security.InvalidParameterException;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.jpa.fdigital.FormMedia;
import br.com.mcampos.jpa.system.Media;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

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
	public FormMedia remove( PrincipalDTO auth, Serializable key )
	{
		if( key == null || auth == null )
			throw new InvalidParameterException( "Form Media remove" );

		FormMedia entity = get( key );
		if( entity != null ) {
			Media media = entity.getMedia( );
			if( media != null )
				mediaSession.remove( auth, media.getId( ) );
			return super.remove( auth, entity.getId( ) );
		}
		else
			return null;
	}

}
