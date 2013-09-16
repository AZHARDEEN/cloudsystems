package br.com.mcampos.ejb.inep.test;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.jpa.inep.InepTestPK;

@Remote
public interface InepTestSession extends BaseCrudSessionInterface<InepTest>
{
	boolean insert( InepTestPK key, byte[ ] object );

	List<InepTest> getTestsWithVariance( InepEvent event );

	List<InepEvent> getAvailableEvents( );

	boolean insert( InepOralTest entity, boolean createSubscription );
}
