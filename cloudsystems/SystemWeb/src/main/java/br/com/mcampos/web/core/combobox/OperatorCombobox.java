package br.com.mcampos.web.core.combobox;

import java.util.List;

import br.com.mcampos.ejb.system.operators.OperatorSession;
import br.com.mcampos.jpa.system.Operator;

public class OperatorCombobox extends ComboboxExt<OperatorSession, Operator>
{
	private static final long serialVersionUID = 7962366432990234277L;

	@Override
	protected Class<OperatorSession> getSessionClass( )
	{
		return OperatorSession.class;
	}

	@Override
	protected void load( )
	{
		load( (List<Operator>) getSession( ).getAll( getPrincipal( ) ), null, true );
	}

}
