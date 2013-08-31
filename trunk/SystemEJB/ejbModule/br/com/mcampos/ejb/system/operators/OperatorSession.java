package br.com.mcampos.ejb.system.operators;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.Operator;

@Remote
public interface OperatorSession extends BaseSessionInterface<Operator>
{

}
