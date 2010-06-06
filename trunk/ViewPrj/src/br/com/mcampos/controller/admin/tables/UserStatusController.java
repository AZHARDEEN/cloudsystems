package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.facade.UserStatusFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public class UserStatusController extends SimpleTableController<UserStatusDTO>
{
    private UserStatusFacade session;

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
            UserStatusDTO dto = ( UserStatusDTO )currentRecord;
            getSession().delete( getLoggedInUser(), dto.getId() );
        }
    }

    protected Object createNewRecord()
    {
        return new UserStatusDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() ) {
            getSession().add( getLoggedInUser(), ( UserStatusDTO )e );
        }
        else
            getSession().update( getLoggedInUser(), ( UserStatusDTO )e );
    }

    public UserStatusFacade getSession()
    {
        if ( session == null )
            session = ( UserStatusFacade )getRemoteSession( UserStatusFacade.class );
        return session;
    }
}
