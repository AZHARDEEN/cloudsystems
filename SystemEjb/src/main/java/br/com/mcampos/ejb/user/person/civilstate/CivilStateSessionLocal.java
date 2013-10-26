package br.com.mcampos.ejb.user.person.civilstate;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.CivilState;

@Local
public interface CivilStateSessionLocal extends BaseCrudSessionInterface<CivilState>
{
}
