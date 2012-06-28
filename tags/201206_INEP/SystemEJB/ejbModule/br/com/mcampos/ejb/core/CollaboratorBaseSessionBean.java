package br.com.mcampos.ejb.core;

import javax.ejb.EJB;

import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginProperty;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginPropertySessionLocal;

public abstract class CollaboratorBaseSessionBean<Y> extends SimpleSessionBean<Y>
{
	@EJB
	private LoginPropertySessionLocal propertySession;

	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	public String getProperty( Collaborator auth, String name )
	{
		if ( auth == null || name == null ) {
			return null;
		}
		LoginProperty p = getPropertySession( ).getProperty( auth, name );
		return p != null ? p.getValue( ) : "";
	}

	public void setProperty( Collaborator auth, String name, String value )
	{
		getPropertySession( ).setProperty( auth, name, value );
	}

	public void remove( Collaborator auth, String name )
	{
		getPropertySession( ).remove( auth, name );
	}

	protected LoginPropertySessionLocal getPropertySession( )
	{
		return this.propertySession;
	}

	protected CollaboratorSessionLocal getCollaboratorSession( )
	{
		return this.collaboratorSession;
	}

}
