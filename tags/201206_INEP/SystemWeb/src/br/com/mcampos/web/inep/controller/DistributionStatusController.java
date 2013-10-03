package br.com.mcampos.web.inep.controller;

import br.com.mcampos.ejb.inep.distribution.DistributionStatusSession;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;
import br.com.mcampos.web.core.SimpleTableController;

public class DistributionStatusController extends SimpleTableController<DistributionStatusSession, DistributionStatus>
{
	private static final long serialVersionUID = -7117544935262956901L;

	@Override
	protected Class<DistributionStatusSession> getSessionClass( )
	{
		return DistributionStatusSession.class;
	}

}