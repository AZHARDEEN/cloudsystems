package br.com.mcampos.controller.anode;

import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.controller.anode.model.MediaListModel;
import br.com.mcampos.controller.anode.model.PenListModel;
import br.com.mcampos.controller.anode.renderer.MediaListRenderer;
import br.com.mcampos.controller.anode.renderer.PenListRenderer;
import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import br.com.mcampos.exception.ApplicationException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class AnodeFormController extends SimpleTableController<FormDTO>
{
    private AnodeFacade session;
    protected Textbox editIP;
    protected Label recordIP;
    Button btnAddAttach;
    Button btnRemoveAttach;
    Listbox listAttachs;
    protected Listbox listAvailable;
    protected Listbox listAdded;

    public AnodeFormController()
    {
        super();
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }

    protected Integer getNextId()
    {
        try {
            return session.nextFormId( getLoggedInUser() );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Próximo id" );
            return 0;
        }
    }

    protected SimpleTableDTO createDTO()
    {
        return new FormDTO();
    }

    protected void delete( Listitem currentRecord ) throws ApplicationException
    {
        FormDTO dto = getValue( currentRecord );

        getSession().delete( getLoggedInUser(), dto );
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
    protected FormDTO getValue( Listitem selecteItem )
    {
        return ( FormDTO )super.getValue( selecteItem );
    }

    protected List getRecordList() throws ApplicationException
    {
        btnAddAttach.setDisabled( true );
        btnRemoveAttach.setDisabled( true );
        return getSession().getForms( getLoggedInUser() );
    }

    @Override
    protected void prepareToInsert()
    {
        super.prepareToInsert();
        editIP.setValue( "" );
        editIP.setFocus( true );
        btnRemoveAttach.setDisabled( true );
    }

    @Override
    protected FormDTO prepareToUpdate( Listitem currentRecord )
    {
        FormDTO dto = ( FormDTO )super.prepareToUpdate( currentRecord );
        if ( dto != null )
            editIP.setValue( dto.getIp() );
        editIP.setFocus( true );
        return dto;
    }

    @Override
    protected SimpleTableDTO copyTo( SimpleTableDTO dto )
    {
        FormDTO d = ( FormDTO )super.copyTo( dto );
        if ( d != null )
            d.setIp( editIP.getValue() );
        return d;
    }

    @Override
    protected void configure( Listitem item )
    {
        if ( item == null )
            return;
        FormDTO dto = getValue( item );

        if ( dto != null ) {
            item.getChildren().add( new Listcell( dto.getIp() ) );
            item.getChildren().add( new Listcell( dto.getDescription() ) );
        }
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        super.showRecord( record );
        editIP.setValue( record != null ? ( ( FormDTO )record ).getIp() : "" );
        btnAddAttach.setDisabled( record == null );
        listAttachs.setModel( getMediaListModel( ( FormDTO )record ) );
        if ( record != null ) {
            refreshAttachs( ( FormDTO )record );
            listAdded.setModel( getPenModel( ( FormDTO )record ) );
        }
        else {
            listAvailable.getItems().clear();
            listAdded.getItems().clear();
        }
    }

    public void onUpload$btnAddAttach( UploadEvent evt )
    {
        if ( getListboxRecord().getSelectedCount() != 1 ) {
            showErrorMessage( "Não existe nenhum formulário selecionado", "Upload Error" );
            evt.stopPropagation();
            return;
        }
        MediaDTO dto = getMedia( evt.getMedia() );
        if ( dto == null )
            return;
        FormDTO form = getValue( getListboxRecord().getSelectedItem() );

        try {
            getSession().addToForm( getLoggedInUser(), form, dto );
            refreshAttachs( form );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Upload Error" );
        }
    }

    protected MediaDTO getMedia( org.zkoss.util.media.Media media )
    {
        MediaDTO dto = new MediaDTO();
        int mediaSize;

        try {
            dto.setFormat( media.getFormat() );
            dto.setName( media.getName() );
            dto.setMimeType( media.getContentType() );
            if ( media.inMemory() ) {
                if ( media.isBinary() )
                    dto.setObject( media.getByteData() );
                else
                    dto.setObject( media.getStringData().getBytes() );
            }
            else {
                mediaSize = media.getStreamData().available();
                dto.setObject( new byte[ mediaSize ] );
                media.getStreamData().read( dto.getObject(), 0, mediaSize );
            }
            return dto;

        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Upload Error" );
            return null;
        }
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        if ( listAttachs != null ) {
            listAttachs.setItemRenderer( new MediaListRenderer() );
        }
        if ( listAvailable != null ) {
            listAvailable.setItemRenderer( ( new PenListRenderer() ).setDraggable( true ) );
            listAvailable.addEventListener( Events.ON_DROP, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        onDrop( ( ( DropEvent )event ) );
                    }
                } );
        }

        if ( listAdded != null ) {
            listAdded.setItemRenderer( ( new PenListRenderer() ).setDraggable( true ) );
            listAdded.addEventListener( Events.ON_DROP, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        onDrop( ( ( DropEvent )event ) );
                    }
                } );
        }
    }

    protected AbstractListModel getMediaListModel( FormDTO currentForm )
    {
        if ( currentForm == null )
            return null;
        List<MediaDTO> list;
        MediaListModel model = null;
        try {
            list = getSession().getMedias( getLoggedInUser(), currentForm );
            model = new MediaListModel( list );
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
        FormDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
        Set selected = fromListbox.getSelectedItems();
        if ( selected.isEmpty() )
            return;
        List al = new ArrayList( selected );
        ArrayList<PenDTO> itens = new ArrayList<PenDTO>( al.size() );
        try {
            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                itens.add( ( PenDTO )li.getValue() );
            }
            if ( toListbox.equals( listAvailable ) )
                getSession().removeFromForm( getLoggedInUser(), currentForm, itens );
            else
                getSession().insertIntoForm( getLoggedInUser(), currentForm, itens );


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

    protected AbstractListModel getAvailablePensListModel( FormDTO current )
    {
        List<PenDTO> list;
        ListModelList model = null;
        try {
            list = getSession().getAvailablePens( getLoggedInUser(), current );
            model = new ListModelList( list, true );
            return model;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }

    protected void refreshAttachs( FormDTO current )
    {
        listAvailable.setModel( getAvailablePensListModel( current ) );
        listAvailable.invalidate();
    }

    protected AbstractListModel getPenModel( FormDTO current )
    {
        List<PenDTO> list;
        PenListModel model = null;
        try {
            list = getSession().getPens( getLoggedInUser(), current );
            model = new PenListModel( list );
            model.loadPage( 1, list.size() ); /*Neste momento o model não pagina*/
            return model;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }


}

