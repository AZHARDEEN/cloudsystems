package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.resale.DealerTypeDTO;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.type.DealerTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;


public class DealerTypeController extends SimpleTableController<DealerTypeDTO>
{
    private DealerTypeFacade session;


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
    }


    @Override
    protected String getPageTitle()
    {
        return getLabel( "dealerTypeTitle" );
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
        getSession().delete( getLoggedInUser(), ( ( DealerTypeDTO )currentRecord ).getId() );
    }

    protected Object createNewRecord()
    {
        return new DealerTypeDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( DealerTypeDTO )e );
        else
            getSession().update( getLoggedInUser(), ( DealerTypeDTO )e );
    }

    public DealerTypeFacade getSession()
    {
        if ( session == null )
            session = ( DealerTypeFacade )getRemoteSession( DealerTypeFacade.class );
        return session;
    }
}
