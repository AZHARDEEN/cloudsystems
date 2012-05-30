package br.com.mcampos.controller.admin.tables.gender;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.facade.GenderFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zul.ListitemRenderer;


public class GenderController extends SimpleTableController<GenderDTO>
{
    private transient GenderFacade session;

    public GenderFacade getSession()
    {
        if ( session == null )
            session = ( GenderFacade )getRemoteSession( GenderFacade.class );
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
        getSession().delete( getLoggedInUser(), ( GenderDTO )currentRecord );
    }

    @Override
    protected Object createNewRecord()
    {
        return new GenderDTO();
    }

    @Override
    protected void persist( Object e ) throws ApplicationException
    {
        GenderDTO gender = ( GenderDTO )e;
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), gender );
        else
            getSession().update( getLoggedInUser(), gender );
    }

    @Override
    protected ListitemRenderer getRenderer()
    {
        return new GenderListRenderer();
    }
}
