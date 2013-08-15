package br.com.mcampos.ejb.user.company.collaborator.property;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

/**
 * Session Bean implementation class LoginPropertySessionBean
 */
@Stateless( name = "LoginPropertySession", mappedName = "LoginPropertySession" )
@LocalBean
public class LoginPropertySessionBean extends SimpleSessionBean<LoginProperty> implements LoginPropertySession, LoginPropertySessionLocal
{
	@Override
	protected Class<LoginProperty> getEntityClass( )
	{
		return LoginProperty.class;
	}

	@Override
	public LoginProperty getProperty( Collaborator collaborator, String propertyName )
	{
		LoginProperty p = null;

		if ( collaborator != null && propertyName != null ) {
			p = get( new LoginPropertyPK( collaborator, propertyName ) );
		}
		return p;
	}

	public LoginProperty remove( Collaborator collaborator, String propertyName )
	{
		LoginProperty p = null;

		if ( collaborator != null && propertyName != null ) {
			p = get( new LoginPropertyPK( collaborator, propertyName ) );
		}
		remove( p );
		return p;
	}

	@Override
	public void setProperty( Collaborator collaborator, String propertyName, String Value )
	{
		if ( collaborator != null && propertyName != null ) {
			LoginProperty p = new LoginProperty( );

			p.setCollaborator( collaborator );
			p.getId( ).setName( propertyName );
			p.setValue( Value );
			p.setFieldType( 1 );
			merge( p );
		}
	}

}
