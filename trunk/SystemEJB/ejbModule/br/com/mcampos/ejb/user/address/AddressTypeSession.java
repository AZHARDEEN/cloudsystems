package br.com.mcampos.ejb.user.address;
import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface AddressTypeSession extends BaseSessionInterface<AddressType>
{

}
