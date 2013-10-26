package br.com.mcampos.ejb.inep.oral;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepOralTest;

@Remote
public interface InepOralTestSession extends BaseCrudSessionInterface<InepOralTest>
{
	InepOralTest add( InepOralTest entity, boolean createSubscription );

}
