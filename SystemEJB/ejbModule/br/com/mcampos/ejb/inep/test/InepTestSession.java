package br.com.mcampos.ejb.inep.test;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepOralTest;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepTest;
import br.com.mcampos.entity.inep.InepTestPK;

@Remote
public interface InepTestSession extends BaseSessionInterface<InepTest>
{
	boolean insert( InepTestPK key, byte[ ] object );

	List<InepTest> getTestsWithVariance( InepPackage event );

	List<InepPackage> getAvailableEvents( );

	boolean insert( InepOralTest entity, boolean createSubscription );
}
