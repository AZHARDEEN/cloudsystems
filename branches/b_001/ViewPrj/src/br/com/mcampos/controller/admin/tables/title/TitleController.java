package br.com.mcampos.controller.admin.tables.title;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.facade.TitleFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zul.Label;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;


public class TitleController extends SimpleTableController<TitleDTO>
{
    private transient TitleFacade session;
    private Textbox editMask;
    private Label recordMask;

    public TitleFacade getSession()
    {
        if ( session == null )
            session = ( TitleFacade )getRemoteSession( TitleFacade.class );
        return session;
    }

    protected Integer getNextId() throws ApplicationException
    {
        return getSession().getNextId( getLoggedInUser() );
    }

    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll( getLoggedInUser() );
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        if ( currentRecord != null )
            getSession().delete( getLoggedInUser(), ( TitleDTO )currentRecord );
    }

    protected Object createNewRecord()
    {
        return new TitleDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        TitleDTO title = ( TitleDTO )e;


        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), title );
        else
            getSession().update( getLoggedInUser(), title );
    }

    @Override
    protected ListitemRenderer getRenderer()
    {
        return new TitleListRenderer();
    }

    @Override
    protected void clearRecordInfo()
    {
        super.clearRecordInfo();
        editMask.setRawValue( "" );
    }

    @Override
    protected Object saveRecord( Object object )
    {
        TitleDTO dto = ( TitleDTO )super.saveRecord( object );
        if ( dto != null ) {
            dto.setAbbreviation( editMask.getValue() );
        }
        return dto;
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        super.showRecord( record );
        if ( record != null ) {
            TitleDTO dto = ( TitleDTO )record;
            recordMask.setValue( dto.getAbbreviation() );
        }
    }
}
