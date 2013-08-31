package br.com.mcampos.ejb.system.operators;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.system.Operator;

/**
 * Session Bean implementation class OperatorSessionBean
 */
@Stateless( name = "OperatorSession", mappedName = "OperatorSession" )
@LocalBean
public class OperatorSessionBean extends SimpleSessionBean<Operator> implements OperatorSession, OperatorSessionLocal
{
	@Override
	protected Class<Operator> getEntityClass( )
	{
		// TODO Auto-generated method stub
		return Operator.class;
	}

}
