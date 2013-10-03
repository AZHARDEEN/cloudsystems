package br.com.mcampos.ejb.email;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class EmailSessionBean
 */
@Stateless( name = "EmailSession", mappedName = "EmailSession" )
@LocalBean
public class EmailSessionBean extends SimpleSessionBean<EMail> implements EmailSession, EmailSessionLocal
{

	@Override
	protected Class<EMail> getEntityClass( )
	{
		return EMail.class;
	}

}