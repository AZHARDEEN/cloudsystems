package br.com.mcampos.controller.anoto;

import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

public abstract class AnotoBaseController<DTO> extends SimpleTableController<DTO>
{
    private AnodeFacade session;

    public AnotoBaseController()
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
