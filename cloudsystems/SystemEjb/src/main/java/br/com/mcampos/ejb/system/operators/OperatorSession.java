package br.com.mcampos.ejb.system.operators;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.Operator;

@Remote
public interface OperatorSession extends BaseCrudSessionInterface<Operator>
{

}
