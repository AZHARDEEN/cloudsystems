package br.com.mcampos.ejb.inep.media;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepMedia;

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

}
