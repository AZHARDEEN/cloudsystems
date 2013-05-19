package br.com.mcampos.ejb.inep.oral;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepOralTest;

@Local
public interface InepOralTestSessionLocal extends BaseSessionInterface<InepOralTest>
{
	void add( InepOralTest entity, boolean createSubscription );
}
