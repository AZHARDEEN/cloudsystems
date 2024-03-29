package br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.session.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.entity.UserStatus;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "UserStatusSession", mappedName = "UserStatusSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class UserStatusSessionBean extends Crud<Integer, UserStatus> implements UserStatusSessionLocal
{
	public void delete( Integer key ) throws ApplicationException
	{
		delete( UserStatus.class, key );
	}

	public UserStatus get( Integer key ) throws ApplicationException
	{
		return get( UserStatus.class, key );
	}

	public List<UserStatus> getAll() throws ApplicationException
	{
		return getAll( UserStatus.getAll );
	}

	public Integer nextIntegerId() throws ApplicationException
	{
		return nextIntegerId( UserStatus.nextId );
	}
}
