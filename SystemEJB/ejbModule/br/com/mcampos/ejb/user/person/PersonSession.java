package br.com.mcampos.ejb.user.person;


import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface PersonSession extends BaseSessionInterface<Person>
{
}
