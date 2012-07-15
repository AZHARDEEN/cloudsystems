package br.com.mcampos.web.fdigital.controller;

import br.com.mcampos.ejb.fdigital.pen.AnotoPen;
import br.com.mcampos.ejb.fdigital.pen.AnotoPenSession;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public class AnotoPenController extends BaseDBListController<AnotoPenSession, AnotoPen>
{
	private static final long serialVersionUID = 3669046391987942113L;

	@Override
	protected void showFields( AnotoPen targetEntity )
	{

	}

	@Override
	protected void updateTargetEntity( AnotoPen entity )
	{
	}

	@Override
	protected boolean validateEntity( AnotoPen entity, int operation )
	{
		return true;
	}

	@Override
	protected Class<AnotoPenSession> getSessionClass( )
	{
		return AnotoPenSession.class;
	}

}
