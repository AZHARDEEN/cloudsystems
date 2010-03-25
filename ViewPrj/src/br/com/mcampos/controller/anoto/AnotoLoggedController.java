package br.com.mcampos.controller.anoto;

import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

public class AnotoLoggedController extends LoggedBaseController
{
    private AnodeFacade session;

    public AnotoLoggedController( char c )
    {
        super( c );
    }

    public AnotoLoggedController()
    {
        super();
    }


    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }


    protected Object getRemoteSession( Class remoteClass )
    {
        try {
            return ServiceLocator.getInstance().getRemoteSession( remoteClass );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }

}
