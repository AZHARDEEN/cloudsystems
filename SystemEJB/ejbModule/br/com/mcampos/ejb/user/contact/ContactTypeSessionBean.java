package br.com.mcampos.ejb.user.contact;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.user.ContactType;

/**
 * Session Bean implementation class ContactTypeSessionBean
 */
@Stateless( mappedName = "ContactTypeSession", name = "ContactTypeSession" )
@LocalBean
public class ContactTypeSessionBean extends SimpleSessionBean<ContactType> implements ContactTypeSession, ContactTypeSessionLocal
{

	@Override
	protected Class<ContactType> getEntityClass( )
	{
		return ContactType.class;
	}

	@Override
	protected String allQueryOrderByClause( String entityAlias )
	{
		return entityAlias + ".id";
	}

}
