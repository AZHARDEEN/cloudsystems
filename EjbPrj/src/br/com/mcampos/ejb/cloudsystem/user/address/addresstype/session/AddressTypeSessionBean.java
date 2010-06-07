package br.com.mcampos.ejb.cloudsystem.user.address.addresstype.session;


import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.entity.AddressType;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AddressTypeSession", mappedName = "CloudSystems-EjbPrj-AddressTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AddressTypeSessionBean extends Crud<Integer, AddressType> implements AddressTypeSessionLocal
{
	public void delete( Integer key ) throws ApplicationException
	{
		delete( AddressType.class, key );
	}

	public AddressType get( Integer key ) throws ApplicationException
	{
		return get( AddressType.class, key );
	}

	public List<AddressType> getAll() throws ApplicationException
	{
		return ( List<AddressType> )getResultList( AddressType.getAll );
	}

	public Integer nextIntegerId() throws ApplicationException
	{
		return nextIntegerId( AddressType.nextId );
	}
}
