package br.com.mcampos.ejb.user.person.gender;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.Gender;

@Remote
public interface GenderSession extends BaseSessionInterface<Gender>
{
}
