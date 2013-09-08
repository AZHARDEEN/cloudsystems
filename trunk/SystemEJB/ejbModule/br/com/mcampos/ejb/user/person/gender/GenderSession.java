package br.com.mcampos.ejb.user.person.gender;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.Gender;

@Remote
public interface GenderSession extends BaseCrudSessionInterface<Gender>
{
}
