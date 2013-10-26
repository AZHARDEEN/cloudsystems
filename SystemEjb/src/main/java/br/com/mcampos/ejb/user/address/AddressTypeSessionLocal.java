package br.com.mcampos.ejb.user.address;
import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.AddressType;

@Local
public interface AddressTypeSessionLocal extends BaseCrudSessionInterface<AddressType>
{

}
