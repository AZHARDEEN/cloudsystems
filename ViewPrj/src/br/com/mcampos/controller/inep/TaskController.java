package br.com.mcampos.controller.inep;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.ejb.inep.task.InepTaskFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.inep.dto.InepTaskDTO;

import java.util.List;

public class TaskController extends SimpleTableController<InepTaskDTO>
{
    public transient InepTaskFacade session;

    public TaskController()
    {
        super();
    }

    public InepTaskFacade getSession()
    {
        if ( session == null )
            session = ( InepTaskFacade )getRemoteSession( InepTaskFacade.class );
        return session;
    }

    @Override
    protected Integer getNextId() throws ApplicationException
    {
        return getSession().getNextId( getLoggedInUser() );
    }

    @Override
    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll( getLoggedInUser() );
    }

    @Override
    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( InepTaskDTO )currentRecord );
    }

    @Override
    protected Object createNewRecord()
    {
        return new InepTaskDTO();
    }

    @Override
    protected void persist( Object e ) throws ApplicationException
    {
        InepTaskDTO dto = ( InepTaskDTO )e;
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), dto );
        else
            getSession().update( getLoggedInUser(), dto );
    }
}
