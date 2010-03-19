package br.com.mcampos.controller.anoto;

import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

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
}
