package br.com.mcampos.ejb.user.company.collaborator.property;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.jpa.security.LoginProperty;
import br.com.mcampos.jpa.security.LoginPropertyPK;

/**
 * Session Bean implementation class LoginPropertySessionBean
 */
@Stateless( name = "LoginPropertySession", mappedName = "LoginPropertySession" )
@LocalBean
public class LoginPropertySessionBean extends SimpleSessionBean<LoginProperty> implements LoginPropertySession, LoginPropertySessionLocal
{
	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@Override
	protected Class<LoginProperty> getEntityClass( )
	{
		return LoginProperty.class;
	}

	@Override
	public LoginProperty getProperty( PrincipalDTO collaborator, String propertyName )
	{
		LoginProperty p = null;

		if ( collaborator != null && propertyName != null ) {
			p = get( new LoginPropertyPK( collaboratorSession.find( collaborator ), propertyName ) );
		}
		return p;
	}

	@Override
	public LoginProperty remove( PrincipalDTO collaborator, String propertyName )
	{
		LoginProperty p = null;

		if ( collaborator != null && propertyName != null ) {
			p = get( new LoginPropertyPK( collaboratorSession.find( collaborator ), propertyName ) );
		}
		remove( collaborator, p );
		return p;
	}

	@Override
	public void setProperty( PrincipalDTO auth, String propertyName, String Value )
	{
		if ( auth != null && propertyName != null ) {
			LoginProperty p = new LoginProperty( );

			p.setCollaborator( collaboratorSession.find( auth ) );
			p.getId( ).setName( propertyName );
			p.setValue( Value );
			p.setFieldType( 1 );
			LoginProperty entity = get( p.getId( ) );
			if ( entity == null )
				add( auth, p );
			else
				update( auth, p );
		}
	}

}
