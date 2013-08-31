package br.com.mcampos.ejb.user.client.entry;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.ClientEntry;

@Local
public interface ClientEntrySessionLocal extends BaseSessionInterface<ClientEntry>
{

}
