package br.com.mcampos.ejb.core;

import javax.ejb.EJB;

import br.com.mcampos.dto.Authentication;
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

	public String getProperty( Authentication auth, String name )
	{
		Collaborator c = getCollaboratorSession( ).find( auth );
		LoginProperty p = getPropertySession( ).getProperty( c, name );
		return p != null ? p.getValue( ) : "";
	}

	public void setProperty( Authentication auth, String name, String value )
	{
		Collaborator c = getCollaboratorSession( ).find( auth );
		getPropertySession( ).setProperty( c, name, value );
	}

	public void remove( Authentication auth, String name )
	{
		Collaborator c = getCollaboratorSession( ).find( auth );
		getPropertySession( ).remove( c, name );
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
