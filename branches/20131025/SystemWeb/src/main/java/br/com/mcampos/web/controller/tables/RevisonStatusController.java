package br.com.mcampos.web.controller.tables;

import br.com.mcampos.ejb.system.revisionstatus.RevisionStatusSession;
import br.com.mcampos.jpa.system.RevisionStatus;
import br.com.mcampos.web.core.SimpleTableController;

public class RevisonStatusController extends SimpleTableController<RevisionStatusSession, RevisionStatus>
{
	private static final long serialVersionUID = -2667344478692389303L;

	@Override
	protected Class<RevisionStatusSession> getSessionClass( )
	{
		return RevisionStatusSession.class;
	}

}
