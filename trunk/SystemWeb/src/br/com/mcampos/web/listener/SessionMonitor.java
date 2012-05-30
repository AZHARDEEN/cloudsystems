package br.com.mcampos.web.listener;

import javax.naming.NamingException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;

import org.zkoss.zk.ui.http.HttpSessionListener;

import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.locator.ServiceLocator;

@WebListener( )
public class SessionMonitor extends HttpSessionListener
{
	private LoginSession session;

	@Override
	public void sessionDestroyed( HttpSessionEvent httpSessionEvent )
	{
		if ( getSession( ) != null ) {
			httpSessionEvent.getSession( ).getAttribute( BaseController.userSessionParamName );
		}
		super.sessionDestroyed( httpSessionEvent );
	}

	protected LoginSession getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = (LoginSession) ServiceLocator.getInstance( ).getRemoteSession( LoginSession.class );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

}
