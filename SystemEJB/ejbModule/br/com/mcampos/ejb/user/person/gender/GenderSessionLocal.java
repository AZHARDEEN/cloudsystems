package br.com.mcampos.ejb.user.person.gender;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.Gender;

@Local
public interface GenderSessionLocal extends BaseSessionInterface<Gender>
{
}
