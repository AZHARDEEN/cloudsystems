package br.com.mcampos.web.inep.controller;

import br.com.mcampos.ejb.inep.revisortype.RevisorTypeSession;
import br.com.mcampos.jpa.inep.RevisorType;
import br.com.mcampos.web.core.SimpleTableController;

public class RevisorTypeController extends SimpleTableController<RevisorTypeSession, RevisorType>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4102315882527890478L;

	@Override
	protected Class<RevisorTypeSession> getSessionClass( )
	{
		return RevisorTypeSession.class;
	}

}
