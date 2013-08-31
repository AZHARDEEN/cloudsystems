package br.com.mcampos.ejb.user.address;

import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.user.AddressType;

/**
 * Session Bean implementation class AddressTypeSessionBean
 */
@Stateless( name = "AddressTypeSession", mappedName = "AddressTypeSession" )
public class AddressTypeSessionBean extends SimpleSessionBean<AddressType> implements AddressTypeSession, AddressTypeSessionLocal
{

	@Override
	protected Class<AddressType> getEntityClass( )
	{
		return AddressType.class;
	}
}
