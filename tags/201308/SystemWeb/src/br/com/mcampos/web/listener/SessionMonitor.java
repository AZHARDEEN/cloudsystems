package br.com.mcampos.web.listener;

import javax.naming.NamingException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.http.HttpSessionListener;

import br.com.mcampos.ejb.fdigital.services.SamaBSBClient;
import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.utils.dto.Credential;
import br.com.mcampos.web.core.LoggedInterface;
import br.com.mcampos.web.locator.ServiceLocator;

@WebListener( )
public class SessionMonitor extends HttpSessionListener
{
	private LoginSession session;
	private static final Logger logger = LoggerFactory.getLogger( SamaBSBClient.class );

	@Override
	public void sessionDestroyed( HttpSessionEvent httpSessionEvent )
	{
		if ( getSession( ) != null ) {
			Object obj = httpSessionEvent.getSession( ).getAttribute( LoggedInterface.userSessionParamName );
			if ( obj != null && obj instanceof Login ) {
				logger.info( "Logout from " + ( (Login) obj ).getPerson( ).getName( ) );
				getSession( ).logout( (Login) obj, getCredential( httpSessionEvent ) );
				httpSessionEvent.getSession( ).setAttribute( LoggedInterface.userSessionParamName, null );
			}
		}
		super.sessionDestroyed( httpSessionEvent );
	}

	protected LoginSession getSession( )
	{
		try {
			if ( session == null ) {
				session = (LoginSession) ServiceLocator.getInstance( ).getRemoteSession( LoginSession.class );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return session;
	}

	private Credential getCredential( HttpSessionEvent sessionEvent )
	{
		Credential c = new Credential( );

		c.setLocale( Locales.getCurrent( ) );
		if ( Executions.getCurrent( ) != null ) {
			c.setRemoteAddr( Executions.getCurrent( ).getRemoteAddr( ) );
			c.setRemoteHost( Executions.getCurrent( ).getRemoteHost( ) );
			c.setProgram( Executions.getCurrent( ).getBrowser( ) );
		}
		else {
			c.setRemoteAddr( "n/a" );
		}
		c.setSessionId( sessionEvent.getSession( ).getId( ) );
		return c;
	}
}
