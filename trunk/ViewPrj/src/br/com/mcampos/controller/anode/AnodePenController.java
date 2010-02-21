package br.com.mcampos.controller.anode;

import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class AnodePenController extends BasicListController<PenDTO>
{
    protected Textbox editId;
    private AnodeFacade session;

    public AnodePenController()
    {
        super();
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }

    protected void showRecord( PenDTO record )
    {
        editId.setValue( record == null ? "" : record.getId() );
    }

    protected PenDTO createDTO()
    {
        return new PenDTO();
    }

    protected PenDTO copyTo( PenDTO dto )
    {
        dto.setId( editId.getValue() );
        return dto;
    }

    protected void configure( Listitem item )
    {
        if ( item == null )
            return;
        PenDTO dto = getValue( item );

        if ( dto != null ) {
            item.getChildren().add( new Listcell( dto.getId() ) );
        }
    }

    protected List getRecordList() throws ApplicationException
    {
        return getSession().getPens( getLoggedInUser() );
    }

    protected void delete( Listitem currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), getValue( currentRecord ) );
    }

    protected Listitem saveRecord( Listitem getCurrentRecord )
    {
        return null;
    }

    protected void prepareToInsert()
    {
        editId.setValue( "" );
    }

    protected Object prepareToUpdate( Listitem currentRecord )
    {
        PenDTO dto = null;

        dto = getValue( currentRecord );

        editId.setValue( dto.getId() );
        return dto;
    }

    protected void insertItem( Listitem e ) throws ApplicationException
    {
        getSession().add( getLoggedInUser(), getValue( e ) );
    }

    protected void updateItem( Listitem e ) throws ApplicationException
    {
        getSession().update( getLoggedInUser(), getValue( e ) );
    }
}
