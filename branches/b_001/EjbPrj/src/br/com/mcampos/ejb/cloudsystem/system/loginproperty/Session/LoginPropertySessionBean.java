package br.com.mcampos.ejb.cloudsystem.system.loginproperty.Session;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.mcampos.ejb.cloudsystem.system.loginproperty.entity.LoginProperty;
import br.com.mcampos.ejb.cloudsystem.system.loginproperty.entity.LoginPropertyPK;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.entity.SystemUserProperty;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

@Stateless( name = "LoginPropertySession", mappedName = "CloudSystems-EjbPrj-LoginPropertySession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class LoginPropertySessionBean extends Crud<LoginPropertyPK, LoginProperty> implements LoginPropertySessionLocal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5763992479525790987L;

	@Override
	public LoginProperty get( LoginPropertyPK key ) throws ApplicationException
	{
		return get( LoginProperty.class, key );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List<LoginProperty> getAll( Collaborator login ) throws ApplicationException
	{
		return (List<LoginProperty>) getResultList( LoginProperty.getAll, login );
	}

	@Override
	public LoginProperty get( Collaborator login, SystemUserProperty property ) throws ApplicationException
	{
		return get( new LoginPropertyPK( property.getId( ), login.getCompanyId( ), login.getSequence( ) ) );
	}

}
