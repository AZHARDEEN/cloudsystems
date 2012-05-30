package br.com.mcampos.ejb.user.person.title;


import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface TitleSession extends BaseSessionInterface<Title>
{
}
