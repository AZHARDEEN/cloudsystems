package br.com.mcampos.ejb.inep.oral;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepOralTest;

@Remote
public interface InepOralTestSession extends BaseSessionInterface<InepOralTest>
{
	void add( InepOralTest entity, boolean createSubscription );

}
