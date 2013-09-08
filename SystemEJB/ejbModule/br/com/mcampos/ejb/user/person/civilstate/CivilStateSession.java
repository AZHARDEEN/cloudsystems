package br.com.mcampos.ejb.user.person.civilstate;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.CivilState;

@Remote
public interface CivilStateSession extends BaseCrudSessionInterface<CivilState>
{
}
