package br.com.mcampos.ejb.user.person;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.Person;

@Local
public interface PersonSessionLocal extends BaseCrudSessionInterface<Person>
{
	Person getByDocument( String document );
}
