package br.com.mcampos.ejb.core;

import javax.ejb.EJB;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.property.LoginPropertySessionLocal;
import br.com.mcampos.jpa.BaseCompanyEntity;
import br.com.mcampos.jpa.security.LoginProperty;

public abstract class CollaboratorBaseSessionBean<Y extends BaseCompanyEntity> extends BaseCompanySessionBean<Y> implements CollaboratorBaseSessionInterface<Y>
{
	@EJB
	private LoginPropertySessionLocal propertySession;

	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@Override
	public String getProperty( PrincipalDTO auth, String name )
	{
		if ( auth == null || name == null ) {
			return null;
		}
		LoginProperty p = getPropertySession( ).getProperty( auth, name );
		return p != null ? p.getValue( ) : "";
	}

	@Override
	public void setProperty( PrincipalDTO auth, String name, String value )
	{
		getPropertySession( ).setProperty( auth, name, value );
	}

	@Override
	public void remove( PrincipalDTO auth, String name )
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
