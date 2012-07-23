package br.com.mcampos.ejb.fdigital.penpage;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.fdigital.pen.AnotoPen;

@Local
public interface AnotoPenPageSessionLocal extends BaseSessionInterface<AnotoPenPage>
{
	AnotoPenPage get( AnotoPen pen, String pageId );
}
