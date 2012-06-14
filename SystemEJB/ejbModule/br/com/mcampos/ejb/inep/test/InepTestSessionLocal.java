package br.com.mcampos.ejb.inep.test;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.entity.InepTest;

@Local
public interface InepTestSessionLocal extends BaseSessionInterface<InepTest>
{
	List<InepTest> getTests( InepPackage event );

	List<InepTest> getTests( InepTask task );

	List<InepTest> getTests( InepRevisor revisor );

}
