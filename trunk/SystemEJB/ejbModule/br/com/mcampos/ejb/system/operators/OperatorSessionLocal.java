package br.com.mcampos.ejb.system.operators;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.Operator;

@Local
public interface OperatorSessionLocal extends BaseSessionInterface<Operator>
{

}
