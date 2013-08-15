package br.com.mcampos.ejb.user.client.entry;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface ClientEntrySession extends BaseSessionInterface<ClientEntry>
{

}
