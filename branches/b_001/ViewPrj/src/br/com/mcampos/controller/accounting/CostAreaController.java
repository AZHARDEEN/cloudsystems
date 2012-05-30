package br.com.mcampos.controller.accounting;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.accounting.CostAreaDTO;
import br.com.mcampos.ejb.cloudsystem.account.costcenter.facade.CostAreaFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public class CostAreaController extends SimpleTableController<CostAreaDTO>
{

    private CostAreaFacade session;

    public CostAreaController()
    {
        super();
    }

    protected Integer getNextId() throws ApplicationException
    {
        return getSession().nextId( getLoggedInUser() );
    }

    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll( getLoggedInUser() );
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        CostAreaDTO dto = ( CostAreaDTO )currentRecord;
        getSession().delete( getLoggedInUser(), dto.getId() );
    }

    protected Object createNewRecord()
    {
        return new CostAreaDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() ) {
            getSession().add( getLoggedInUser(), ( CostAreaDTO )e );
        }
        else
            getSession().update( getLoggedInUser(), ( CostAreaDTO )e );
    }

    public CostAreaFacade getSession()
    {
        if ( session == null )
            session = ( CostAreaFacade )getRemoteSession( CostAreaFacade.class );
        return session;
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "costAreaPageTitle" );
    }
}
