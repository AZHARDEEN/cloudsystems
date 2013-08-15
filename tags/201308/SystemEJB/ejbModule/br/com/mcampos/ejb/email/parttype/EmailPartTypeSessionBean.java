package br.com.mcampos.ejb.email.parttype;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class EmailPartTypeSessionBean
 */
@Stateless( name = "EmailPartTypeSession", mappedName = "EmailPartTypeSession" )
@LocalBean
public class EmailPartTypeSessionBean extends SimpleSessionBean<EMailPartType> implements EmailPartTypeSession,
EmailPartTypeSessionLocal
{

	@Override
	protected Class<EMailPartType> getEntityClass( )
	{
		return EMailPartType.class;
	}

}
