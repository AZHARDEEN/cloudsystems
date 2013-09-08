package br.com.mcampos.ejb.user.person.gender;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.Gender;

@Local
public interface GenderSessionLocal extends BaseCrudSessionInterface<Gender>
{
}
