package br.com.mcampos.ejb.user.person.gender;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface GenderSession extends BaseSessionInterface<Gender>
{
}
