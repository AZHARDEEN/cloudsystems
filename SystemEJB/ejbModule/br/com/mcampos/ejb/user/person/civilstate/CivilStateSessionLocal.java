package br.com.mcampos.ejb.user.person.civilstate;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.CivilState;

@Local
public interface CivilStateSessionLocal extends BaseSessionInterface<CivilState>
{
}
