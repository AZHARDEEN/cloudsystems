package br.com.mcampos.ejb.email;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.system.EMail;

/**
 * Session Bean implementation class EmailSessionBean
 */
@Stateless( name = "EmailSession", mappedName = "EmailSession" )
@LocalBean
public class EmailSessionBean extends SimpleSessionBean<EMail> implements EmailSession, EmailSessionLocal
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6228595648302506733L;

	@Override
	protected Class<EMail> getEntityClass( )
	{
		return EMail.class;
	}

}
