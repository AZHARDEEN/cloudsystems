package br.com.mcampos.ejb.user.client.entry;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.ClientEntry;

@Remote
public interface ClientEntrySession extends BaseSessionInterface<ClientEntry>
{

}
