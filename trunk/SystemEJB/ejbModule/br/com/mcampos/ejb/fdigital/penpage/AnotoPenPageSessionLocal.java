package br.com.mcampos.ejb.fdigital.penpage;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.fdigital.AnotoPen;
import br.com.mcampos.entity.fdigital.AnotoPenPage;

@Local
public interface AnotoPenPageSessionLocal extends BaseCrudSessionInterface<AnotoPenPage>
{
	AnotoPenPage get( AnotoPen pen, String pageId );
}
