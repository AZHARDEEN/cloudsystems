package br.com.mcampos.ejb.fdigital.pen;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.fdigital.AnotoPen;

@Remote
public interface AnotoPenSession extends BaseSessionInterface<AnotoPen>
{

}
