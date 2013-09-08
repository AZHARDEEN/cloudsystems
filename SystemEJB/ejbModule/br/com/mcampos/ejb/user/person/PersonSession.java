package br.com.mcampos.ejb.user.person;


import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.Person;

@Remote
public interface PersonSession extends BaseCrudSessionInterface<Person>
{
}
