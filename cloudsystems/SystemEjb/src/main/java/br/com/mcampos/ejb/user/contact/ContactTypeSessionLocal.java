package br.com.mcampos.ejb.user.contact;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.ContactType;

@Local
public interface ContactTypeSessionLocal extends BaseCrudSessionInterface<ContactType>
{

}
