package br.com.mcampos.controller.anoto.base;

import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

public abstract class AnotoBaseController<DTO> extends BasicListController<DTO>
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
