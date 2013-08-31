package br.com.mcampos.web.fdigital.controller;

import br.com.mcampos.ejb.fdigital.pgcstatus.PgcStatusSession;
import br.com.mcampos.entity.fdigital.PgcStatus;
import br.com.mcampos.web.core.SimpleTableController;

public class PgcStatusController extends SimpleTableController<PgcStatusSession, PgcStatus>
{
	private static final long serialVersionUID = -8099699601085290551L;

	@Override
	protected Class<PgcStatusSession> getSessionClass( )
	{
		return PgcStatusSession.class;
	}

}
