package br.com.mcampos.web.listener;

import javax.naming.NamingException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.http.HttpSessionListener;

import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.utils.dto.CredentialDTO;
import br.com.mcampos.utils.dto.PrincipalDTO;
import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.locator.ServiceLocator;

@WebListener( )
public class SessionMonitor extends HttpSessionListener
{
	private LoginSession session;
	@SuppressWarnings( "unused" )
	private static final Logger logger = LoggerFactory.getLogger( SessionMonitor.class );

	@Override
	public void sessionDestroyed( HttpSessionEvent httpSessionEvent )
	{
		if ( getSession( ) != null ) {
			Object obj = httpSessionEvent.getSession( ).getAttribute( BaseController.currentPrincipal );
			if ( obj != null ) {
				PrincipalDTO dto = (PrincipalDTO) obj;
				getSession( ).logout( dto.getUserId( ), getCredential( httpSessionEvent ) );
				httpSessionEvent.getSession( ).setAttribute( BaseController.currentPrincipal, null );
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

	private CredentialDTO getCredential( HttpSessionEvent sessionEvent )
	{
		CredentialDTO c = new CredentialDTO( );

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
