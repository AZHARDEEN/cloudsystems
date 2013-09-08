package br.com.mcampos.ejb.user.contact;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.ContactType;

@Remote
public interface ContactTypeSession extends BaseCrudSessionInterface<ContactType>
{

}
