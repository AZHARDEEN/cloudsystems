package br.com.mcampos.ejb.system.operators;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.Operator;

@Local
public interface OperatorSessionLocal extends BaseCrudSessionInterface<Operator>
{

}
