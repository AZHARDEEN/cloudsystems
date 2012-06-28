package br.com.mcampos.ejb.user.contact;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.user.contact.type.ContactType;

@Local
public interface ContactTypeSessionLocal extends BaseSessionInterface<ContactType>
{

}
