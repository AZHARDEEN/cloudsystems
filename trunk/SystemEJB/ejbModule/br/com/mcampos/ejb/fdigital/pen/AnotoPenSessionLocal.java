package br.com.mcampos.ejb.fdigital.pen;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.fdigital.AnotoPen;

@Local
public interface AnotoPenSessionLocal extends BaseCrudSessionInterface<AnotoPen>
{

}
