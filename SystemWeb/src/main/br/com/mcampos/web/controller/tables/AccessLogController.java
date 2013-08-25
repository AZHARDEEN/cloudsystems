package br.com.mcampos.web.controller.tables;


import br.com.mcampos.ejb.security.log.AccessLog;
import br.com.mcampos.ejb.security.log.AccessLogSession;
import br.com.mcampos.web.core.listbox.ReadOnlyListboxController;



public class AccessLogController extends ReadOnlyListboxController<AccessLogSession, AccessLog>
{
	private static final long serialVersionUID = -6706603843933185036L;

	@Override
	protected Class<AccessLogSession> getSessionClass( )
	{
		return AccessLogSession.class;
	}

}
