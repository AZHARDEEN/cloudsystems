package br.com.mcampos.ejb.inep.test;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepTest;

@Remote
public interface InepTestSession extends BaseCrudSessionInterface<InepTest>
{
	List<InepTest> getTestsWithVariance( InepEvent event );

	List<InepEvent> getAvailableEvents( PrincipalDTO auth );
}
