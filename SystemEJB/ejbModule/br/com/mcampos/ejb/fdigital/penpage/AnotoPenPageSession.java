package br.com.mcampos.ejb.fdigital.penpage;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.fdigital.pen.AnotoPen;

@Remote
public interface AnotoPenPageSession extends BaseSessionInterface<AnotoPenPage>
{
	AnotoPenPage get( AnotoPen pen, String pageId );
}
