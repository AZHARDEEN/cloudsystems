package br.com.mcampos.controller.admin.tables.AddressType;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.facade.AddressTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public class AddressTypeController extends SimpleTableController<AddressTypeDTO>
{
    private AddressTypeFacade session;

    protected Integer getNextId() throws ApplicationException
    {
        return getSession().nextId( getLoggedInUser() );
    }

    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll();
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( AddressTypeDTO )currentRecord );
    }

    protected Object createNewRecord()
    {
        return new AddressTypeDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( AddressTypeDTO )e );
        else
            getSession().update( getLoggedInUser(), ( AddressTypeDTO )e );
    }


    private AddressTypeFacade getSession()
    {
        if ( session == null )
            session = ( AddressTypeFacade )getRemoteSession( AddressTypeFacade.class );
        return session;
    }
}


