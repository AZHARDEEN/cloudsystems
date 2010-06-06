package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.facade.ContactTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public class ContactTypeController extends SimpleTableController<ContactTypeDTO>
{
    private ContactTypeFacade session;

    protected Integer getNextId() throws ApplicationException
    {
        return getSession().nextId( getLoggedInUser() );
    }

    protected List getRecordList() throws ApplicationException
    {
        List list = getSession().getAll();

        return list;
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        if ( currentRecord != null ) {
            ContactTypeDTO dto = ( ContactTypeDTO )currentRecord;
            getSession().delete( getLoggedInUser(), dto.getId() );
        }
    }

    protected Object createNewRecord()
    {
        return new ContactTypeDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() ) {
            getSession().add( getLoggedInUser(), ( ContactTypeDTO )e );
        }
        else
            getSession().update( getLoggedInUser(), ( ContactTypeDTO )e );
    }

    public ContactTypeFacade getSession()
    {
        if ( session == null )
            session = ( ContactTypeFacade )getRemoteSession( ContactTypeFacade.class );
        return session;
    }
}
