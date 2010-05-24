package br.com.mcampos.controller.admin.tables.gender;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.ejb.cloudsystem.user.gender.GenderFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;


public class GenderController extends SimpleTableController<GenderDTO>
{
    private transient GenderFacade session;

    public GenderFacade getSession()
    {
        if ( session == null )
            session = ( GenderFacade )getRemoteSession( GenderFacade.class );
        return session;
    }

    @Override
    protected Integer getNextId()
    {
        return null;
    }

    @Override
    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll( getLoggedInUser() );
    }

    @Override
    protected void delete( Object currentRecord )
    {
    }

    @Override
    protected Object createNewRecord()
    {
        return new GenderDTO();
    }

    @Override
    protected void persist( Object e )
    {
    }
}
