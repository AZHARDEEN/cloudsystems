package br.com.mcampos.util.system;

import br.com.mcampos.dto.user.login.LoginDTO;

import br.com.mcampos.util.business.LoginLocator;
import br.com.mcampos.util.business.UsersLocator;

import javax.servlet.http.HttpSessionEvent;

import org.zkoss.zk.ui.http.HttpSessionListener;

public class CloudSystemSessionListener extends HttpSessionListener
{
    public static String userSessionId="userSessionID";
    
    protected LoginLocator locator;
    
    
    public CloudSystemSessionListener()
    {
        super();
    }

    @Override
    public void sessionDestroyed( HttpSessionEvent httpSessionEvent )
    {
        LoginDTO user;
        
        user = ( (LoginDTO) httpSessionEvent.getSession().getAttribute( userSessionId ) );
        if ( user != null ) 
            getLocator().logoutUser( user );
        super.sessionDestroyed( httpSessionEvent );
    }

    protected LoginLocator getLocator()
    {
        if ( locator == null )
            locator = new LoginLocator();
        return locator;
    }
}
