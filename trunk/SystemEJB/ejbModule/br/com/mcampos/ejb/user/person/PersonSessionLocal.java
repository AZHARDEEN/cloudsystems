package br.com.mcampos.ejb.user.person;


import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Local
public interface PersonSessionLocal extends BaseSessionInterface<Person>
{
}
