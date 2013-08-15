package br.com.mcampos.ejb.user.contact;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.user.contact.type.ContactType;

@Remote
public interface ContactTypeSession extends BaseSessionInterface<ContactType>
{

}
