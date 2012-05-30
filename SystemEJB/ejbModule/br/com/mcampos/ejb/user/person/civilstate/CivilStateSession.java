package br.com.mcampos.ejb.user.person.civilstate;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface CivilStateSession extends BaseSessionInterface<CivilState>
{
}
