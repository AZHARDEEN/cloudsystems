package br.com.mcampos.ejb.user.person.gender;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Local
public interface GenderSessionLocal extends BaseSessionInterface<Gender>
{
}
