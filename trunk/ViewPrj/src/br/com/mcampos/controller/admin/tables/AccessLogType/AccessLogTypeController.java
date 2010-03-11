package br.com.mcampos.controller.admin.tables.AccessLogType;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zul.Listitem;


public class AccessLogTypeController extends SimpleTableController<AccessLogTypeDTO>
{
    private AccessLogLocator locator;

    public AccessLogTypeController()
    {
        super();
    }

    protected Object createNewRecord()
    {
        return new AccessLogTypeDTO();
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getLocator().delete( getLoggedInUser(), getValue( ( Listitem )currentRecord ) );
    }

    protected void persist( Object e ) throws ApplicationException
    {
        getLocator().add( getLoggedInUser(), ( AccessLogTypeDTO )e );
    }

    protected void updateItem( Object e ) throws ApplicationException
    {
        getLocator().update( getLoggedInUser(), ( AccessLogTypeDTO )e );
    }

    public AccessLogLocator getLocator()
    {
        if ( locator == null )
            locator = new AccessLogLocator();
        return locator;
    }

    @Override
    protected AccessLogTypeDTO getValue( Listitem selecteItem )
    {
        return ( AccessLogTypeDTO )super.getValue( selecteItem );
    }

    protected Integer getNextId()
    {
        try {
            return getLocator().getNextAccessLogTypeId( getLoggedInUser() );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Próximo ID" );
            return 0;
        }
    }

    protected List getRecordList()
    {
        return getLocator().getList( getLoggedInUser() );
    }
}
