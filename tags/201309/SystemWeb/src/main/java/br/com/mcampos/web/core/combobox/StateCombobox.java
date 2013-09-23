package br.com.mcampos.web.core.combobox;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.locale.state.StateSession;
import br.com.mcampos.jpa.locale.State;

public class StateCombobox extends ComboboxExt<StateSession, State>
{
	@SuppressWarnings( "unused" )
	private static final Logger logger = LoggerFactory.getLogger( StateCombobox.class );
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<StateSession> getSessionClass( )
	{
		return StateSession.class;
	}

	@Override
	public void load( )
	{
		load( (List<State>) getSession( ).getAll( getPrincipal( ) ), null, true );
	}
}
