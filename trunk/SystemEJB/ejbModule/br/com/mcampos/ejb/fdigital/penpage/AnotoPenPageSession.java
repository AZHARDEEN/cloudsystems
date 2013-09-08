package br.com.mcampos.ejb.fdigital.penpage;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.fdigital.AnotoPen;
import br.com.mcampos.entity.fdigital.AnotoPenPage;

@Remote
public interface AnotoPenPageSession extends BaseCrudSessionInterface<AnotoPenPage>
{
	AnotoPenPage get( AnotoPen pen, String pageId );
}
