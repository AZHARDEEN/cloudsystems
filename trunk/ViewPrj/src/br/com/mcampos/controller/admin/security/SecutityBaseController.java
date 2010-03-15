package br.com.mcampos.controller.admin.security;

import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.security.facade.SecurityFacade;

public abstract class SecutityBaseController extends LoggedBaseController
{
    private SecurityFacade securitySession;

    public SecutityBaseController( char c )
    {
        super( c );
    }

    public SecutityBaseController()
    {
        super();
    }

    SecurityFacade getSession ()
    {
        if ( securitySession == null )
            securitySession = ( SecurityFacade ) getRemoteSession( SecurityFacade.class );
        return securitySession;
    }
}
