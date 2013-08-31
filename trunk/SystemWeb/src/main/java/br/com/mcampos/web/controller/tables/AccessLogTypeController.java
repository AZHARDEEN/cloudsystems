package br.com.mcampos.web.controller.tables;

import br.com.mcampos.ejb.security.log.AccessLogTypeSession;
import br.com.mcampos.entity.security.AccessLogType;
import br.com.mcampos.web.core.SimpleTableController;

public class AccessLogTypeController extends SimpleTableController<AccessLogTypeSession, AccessLogType>
{
	private static final long serialVersionUID = -7795576056970437170L;

	public AccessLogTypeController( )
	{
		super( );
	}

	@Override
	protected Class<AccessLogTypeSession> getSessionClass( )
	{
		return AccessLogTypeSession.class;
	}
}
