package br.com.mcampos.ejb.user.address;
import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.AddressType;

@Local
public interface AddressTypeSessionLocal extends BaseSessionInterface<AddressType>
{

}
