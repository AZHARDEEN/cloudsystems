package br.com.mcampos.ejb.user.person;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.Person;

@Local
public interface PersonSessionLocal extends BaseSessionInterface<Person>
{
	Person getByDocument( String document );
}
