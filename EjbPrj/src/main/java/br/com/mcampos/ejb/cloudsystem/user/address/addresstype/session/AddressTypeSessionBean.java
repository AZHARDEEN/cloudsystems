package br.com.mcampos.ejb.cloudsystem.user.address.addresstype.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.entity.AddressType;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AddressTypeSession", mappedName = "AddressTypeSession" )
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
