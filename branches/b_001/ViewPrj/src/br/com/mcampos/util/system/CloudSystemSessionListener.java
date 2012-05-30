package br.com.mcampos.util.system;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.util.business.LoginLocator;

import javax.servlet.http.HttpSessionEvent;

import org.zkoss.zk.ui.http.HttpSessionListener;


public class CloudSystemSessionListener extends HttpSessionListener
{
    public static final String userSessionId = "userSessionID";

    protected LoginLocator locator;


    public CloudSystemSessionListener()
    {
        super();
    }

    @Override
    public void sessionDestroyed( HttpSessionEvent httpSessionEvent )
    {
        AuthenticationDTO user;

        try {
            user = ( ( AuthenticationDTO )httpSessionEvent.getSession().getAttribute( userSessionId ) );
            if ( user != null )
                getLocator().logoutUser( user );
        }
        catch ( Exception e ) {
            e = null;
        }
        super.sessionDestroyed( httpSessionEvent );
    }

    protected LoginLocator getLocator()
    {
        if ( locator == null )
            locator = new LoginLocator();
        return locator;
    }
}
