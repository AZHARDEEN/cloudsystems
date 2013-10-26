package br.com.mcampos.web.core.combobox.controller;

import java.util.List;

import br.com.mcampos.ejb.locale.state.StateSession;

public class StateComboboxController extends BaseDBCombobox<StateSession>
{
	private static final long serialVersionUID = -5864685537506539399L;

	@Override
	protected Class<StateSession> getSessionClass( )
	{
		return StateSession.class;
	}

	@Override
	protected List<?> getList( )
	{
		return getSession( ).getAll( "BR" );
	}

}
