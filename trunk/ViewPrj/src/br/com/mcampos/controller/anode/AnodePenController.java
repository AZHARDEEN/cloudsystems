package br.com.mcampos.controller.anode;

import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.controller.anode.model.FormListModel;
import br.com.mcampos.controller.anode.renderer.FormListRenderer;
import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class AnodePenController extends BasicListController<PenDTO>
{
    protected Textbox editId;
    private AnodeFacade session;
    protected Listbox listAvailable;
    protected Listbox listAdded;
    protected Button btnAddForm;
    protected Button btnRemoveForm;

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
            listAvailable.setModel( getAvailableFormsListModel( record ) );
            listAdded.setModel( getFormModel( record ) );
        }
        else {
            editId.setValue( "" );
            listAvailable.getItems().clear();
            listAdded.getItems().clear();
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
        //getSession().update( getLoggedInUser(), getValue( e ) );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        /*We do not need update by now*/
        cmdUpdate.setVisible( false );
        if ( listAvailable != null ) {
            listAvailable.setItemRenderer( ( new FormListRenderer() ).setDraggable( true ) );
            listAvailable.addEventListener( Events.ON_DROP, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        onDrop( ( ( DropEvent )event ) );
                    }
                } );
        }

        if ( listAdded != null ) {
            listAdded.setItemRenderer( ( new FormListRenderer() ).setDraggable( true ) );
            listAdded.addEventListener( Events.ON_DROP, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        onDrop( ( ( DropEvent )event ) );
                    }
                } );
        }
    }


    protected AbstractListModel getAvailableFormsListModel( PenDTO currentPen )
    {
        List<FormDTO> list;
        FormListModel model = null;
        try {
            list = getSession().getAvailableForms( getLoggedInUser(), currentPen );
            model = new FormListModel( list );
            model.loadPage( 1, list.size() ); /*Neste momento o model não pagina*/
            return model;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }

    protected AbstractListModel getFormModel( PenDTO currentPen )
    {
        List<FormDTO> list;
        FormListModel model = null;
        try {
            list = getSession().getForms( getLoggedInUser(), currentPen );
            model = new FormListModel( list );
            model.loadPage( 1, list.size() ); /*Neste momento o model não pagina*/
            return model;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }


    public void onClick$btnAddForm()
    {
        moveListitem( listAdded, listAvailable );
    }


    public void onClick$btnRemoveForm()
    {
        moveListitem( listAvailable, listAdded );
    }


    protected void moveListitem( Listbox toListbox, Listbox fromListbox )
    {
        if ( getListboxRecord().getSelectedCount() != 1 )
            return;
        PenDTO currentPen = getValue( getListboxRecord().getSelectedItem() );
        Set selected = fromListbox.getSelectedItems();
        if ( selected.isEmpty() )
            return;
        List al = new ArrayList( selected );
        ArrayList<FormDTO> forms = new ArrayList<FormDTO>( al.size() );
        try {
            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                forms.add( ( FormDTO )li.getValue() );
            }
            if ( toListbox.equals( listAvailable ) )
                getSession().removeFromPen( getLoggedInUser(), currentPen, forms );
            else
                getSession().insertIntoPen( getLoggedInUser(), currentPen, forms );


            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                li.setSelected( false );
                toListbox.appendChild( li );
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Formularios Associados" );
        }
    }

    public void onDrop( DropEvent evt )
    {
        if ( getListboxRecord().getSelectedCount() != 1 )
            return;
        if ( evt == null )
            return;
        Component dragged = evt.getDragged();
        Component target = evt.getTarget();
        if ( dragged == null || !( dragged instanceof Listitem ) || target == null || !( target instanceof Listbox ) )
            return;
        ( ( Listitem )dragged ).setSelected( true );
        if ( target.equals( listAvailable ) ) {
            //we are removing forms from this pen
            moveListitem( listAvailable, listAdded );
        }
        else {
            // we are adding forms into this pen
            moveListitem( listAdded, listAvailable );
        }
    }
}
