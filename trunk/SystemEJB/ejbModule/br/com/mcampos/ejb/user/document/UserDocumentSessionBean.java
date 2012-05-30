package br.com.mcampos.ejb.user.document;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.user.Users;

/**
 * Session Bean implementation class UserDocumentSessionBean
 */
@Stateless( name = "UserDocumentSession", mappedName = "UserDocumentSession" )
@LocalBean
public class UserDocumentSessionBean extends SimpleSessionBean<UserDocument> implements UserDocumentSessionLocal
{

	@Override
	protected Class<UserDocument> getEntityClass( )
	{
		return UserDocument.class;
	}

	@Override
	public Users getUserByDocument( String document )
	{
		UserDocument doc = null;

		try {
			doc = getByNamedQuery( UserDocument.findDocument, document );
		}
		catch ( Exception e ) {
			e = null;
		}
		return doc != null ? doc.getUser( ) : null;
	}

}
