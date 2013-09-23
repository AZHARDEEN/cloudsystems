package br.com.mcampos.ejb.user.client.entry;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.ClientEntry;

@Local
public interface ClientEntrySessionLocal extends BaseCrudSessionInterface<ClientEntry>
{

}
