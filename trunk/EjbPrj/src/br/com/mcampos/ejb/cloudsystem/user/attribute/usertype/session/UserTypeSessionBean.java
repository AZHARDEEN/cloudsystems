package br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.session;


import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "UserTypeSession", mappedName = "CloudSystems-EjbPrj-UserTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class UserTypeSessionBean extends Crud<Integer, UserType> implements UserTypeSessionLocal
{
	public void delete( Integer key ) throws ApplicationException
	{
		delete( UserType.class, key );
	}

	public UserType get( Integer key ) throws ApplicationException
	{
		return get( UserType.class, key );
	}

	public List<UserType> getAll() throws ApplicationException
	{
		return getAll( UserType.getAll );
	}

	public Integer nextIntegerId() throws ApplicationException
	{
		return nextIntegerId( UserType.nextId );
	}
}
