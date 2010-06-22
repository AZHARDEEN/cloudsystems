package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.UserMediaTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.media.type.facade.UserMediaTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;


public class UserMediaTypeController extends SimpleTableController<UserMediaTypeDTO>
{
    private UserMediaTypeFacade session;
    private Label labelCollaboratorTypeTitle;

    @Override
    protected Integer getNextId() throws ApplicationException
    {
        return session.getNextId( getLoggedInUser() );
    }

    protected void showRecord( UserMediaTypeDTO record ) throws ApplicationException
    {
        super.showRecord( record );
    }

    @Override
    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll( getLoggedInUser() );
    }

    @Override
    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( ( UserMediaTypeDTO )currentRecord ).getId() );
    }

    @Override
    protected Object createNewRecord()
    {
        return new UserMediaTypeDTO();
    }

    @Override
    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( UserMediaTypeDTO )e );
        else
            getSession().update( getLoggedInUser(), ( UserMediaTypeDTO )e );
    }

    public UserMediaTypeFacade getSession()
    {
        if ( session == null )
            session = ( UserMediaTypeFacade )getRemoteSession( UserMediaTypeFacade.class );
        return session;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelCollaboratorTypeTitle );
    }
}
