package br.com.mcampos.ejb.system.operators;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface OperatorSession extends BaseSessionInterface<Operator>
{

}
