package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.facade.UserTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public class UserTypeController extends SimpleTableController<UserTypeDTO>
{
    private UserTypeFacade session;

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
            UserTypeDTO dto = ( UserTypeDTO )currentRecord;
            getSession().delete( getLoggedInUser(), dto.getId() );
        }
    }

    protected Object createNewRecord()
    {
        return new UserTypeDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() ) {
            getSession().add( getLoggedInUser(), ( UserTypeDTO )e );
        }
        else
            getSession().update( getLoggedInUser(), ( UserTypeDTO )e );
    }

    public UserTypeFacade getSession()
    {
        if ( session == null )
            session = ( UserTypeFacade )getRemoteSession( UserTypeFacade.class );
        return session;
    }
}
