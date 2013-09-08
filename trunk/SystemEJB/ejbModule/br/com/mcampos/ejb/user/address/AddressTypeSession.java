package br.com.mcampos.ejb.user.address;
import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.AddressType;

@Remote
public interface AddressTypeSession extends BaseCrudSessionInterface<AddressType>
{

}
