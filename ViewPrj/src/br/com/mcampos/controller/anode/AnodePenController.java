package br.com.mcampos.controller.anode;

import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class AnodePenController extends BasicListController<PenDTO>
{
    protected Textbox editId;
    private AnodeFacade session;
    protected Listbox listAvailableForms;
    protected Listbox listForms;

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
        if ( record != null ) {
            editId.setValue( record.getId() );
            listAvailableForms.setModel( getAvailableFormsListModel( record ) );
        }
        else {
            editId.setValue( "" );
            listAvailableForms.getItems().clear();
        }
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
        showRecord( null );
        return getSession().getPens( getLoggedInUser() );
    }

    protected void delete( Listitem currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), getValue( currentRecord ) );
    }

    protected Listitem saveRecord( Listitem getCurrentRecord )
    {
        return getCurrentRecord;
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

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        if ( listAvailableForms != null )
            listAvailableForms.setItemRenderer( ( new FormListRenderer() ).setDraggable( true ) );
    }


    protected AbstractListModel getAvailableFormsListModel( PenDTO currentPen )
    {
        AvailablePenFormListModel model = new AvailablePenFormListModel( getSession(), getLoggedInUser(), currentPen );

        model.loadPage( 1, 65535 ); /*Neste momento o model não pagina*/
        return model;
    }
}
