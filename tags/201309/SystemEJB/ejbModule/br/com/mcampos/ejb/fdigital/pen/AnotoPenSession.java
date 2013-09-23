package br.com.mcampos.ejb.fdigital.pen;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.fdigital.AnotoPen;

@Remote
public interface AnotoPenSession extends BaseCrudSessionInterface<AnotoPen>
{

}
