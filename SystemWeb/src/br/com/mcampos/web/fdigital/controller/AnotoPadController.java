package br.com.mcampos.web.fdigital.controller;

import org.zkoss.zul.Window;

import br.com.mcampos.ejb.fdigital.form.pad.Pad;
import br.com.mcampos.ejb.fdigital.form.pad.PadSession;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public class AnotoPadController extends BaseDBListController<PadSession, Pad>
{
	private static final long serialVersionUID = 908771873595482637L;

	@Override
	protected void showFields( Pad targetEntity )
	{
	}

	@Override
	protected void updateTargetEntity( Pad entity )
	{
	}

	@Override
	protected boolean validateEntity( Pad entity, int operation )
	{
		return true;
	}

	@Override
	protected Class<PadSession> getSessionClass( )
	{
		return PadSession.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
	}

}
