package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.collaborator.CollaboratorTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.facade.CollaboratorTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;


public class CollaboratorTypeController extends SimpleTableController<CollaboratorTypeDTO>
{
    private CollaboratorTypeFacade session;
    private Label labelCollaboratorTypeTitle;

    public CollaboratorTypeController()
    {
        super();
    }

    @Override
    protected Integer getNextId() throws ApplicationException
    {
        return session.getNextId( getLoggedInUser() );
    }

    protected void showRecord( CollaboratorTypeDTO record ) throws ApplicationException
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
        getSession().delete( getLoggedInUser(), ( CollaboratorTypeDTO )currentRecord );
    }

    @Override
    protected Object createNewRecord()
    {
        return new CollaboratorTypeDTO();
    }

    @Override
    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( CollaboratorTypeDTO )e );
        else
            getSession().update( getLoggedInUser(), ( CollaboratorTypeDTO )e );
    }

    public CollaboratorTypeFacade getSession()
    {
        if ( session == null )
            session = ( CollaboratorTypeFacade )getRemoteSession( CollaboratorTypeFacade.class );
        return session;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelCollaboratorTypeTitle );
    }
}
