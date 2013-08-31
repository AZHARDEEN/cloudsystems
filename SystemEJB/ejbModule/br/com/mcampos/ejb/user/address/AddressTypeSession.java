package br.com.mcampos.ejb.user.address;
import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.AddressType;

@Remote
public interface AddressTypeSession extends BaseSessionInterface<AddressType>
{

}
