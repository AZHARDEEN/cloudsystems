package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.anoto.renderer.PenListRenderer;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.system.UploadMedia;

import com.anoto.api.FormatException;
import com.anoto.api.IllegalValueException;
import com.anoto.api.NotAllowedException;
import com.anoto.api.PenHome;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
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


public class AnotoFormController extends SimpleTableController<FormDTO>
{
    private AnodeFacade session;
    protected Textbox editIP;
    protected Label recordIP;
    protected Button btnAddAttach;
    protected Button btnRemoveAttach;
    protected Button btnProperties;
    protected Listbox listAttachs;
    protected Listbox listAvailable;
    protected Listbox listAdded;

    public AnotoFormController()
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

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        FormDTO dto = getValue( (Listitem) currentRecord );

        getSession().delete( getLoggedInUser(), dto );
    }

    protected boolean validate (  FormDTO dto, boolean bNew )
    {
        if ( SysUtils.isEmpty( dto.getDescription() ) )
        {
            showErrorMessage( "A descrição do formulário deve estar preenchida", "Formulário" );
            return false;
        }
        if ( SysUtils.isEmpty( dto.getApplication() ) )
        {
            showErrorMessage( "A aplicação do formulário deve estar preenchida", "Formulário" );
            return false;
        }
        if ( bNew ) {
            FormDTO existDTO;
            try {
                existDTO = getSession().get( getLoggedInUser(), dto );
                if ( existDTO != null )
                {
                    showErrorMessage( "Já existe um registro com este código de formulário.", "Formulário" );
                }
            }
            catch ( ApplicationException e ) {
                e = null;
            }
        }
        return true;
    }

    protected void insertItem( Object e ) throws ApplicationException
    {
        FormDTO dto = getValue ( (Listitem)e );
        if ( validate ( dto, true ) )
            getSession().add( getLoggedInUser(), dto );
    }

    protected void updateItem( Object e ) throws ApplicationException
    {
        FormDTO dto = getValue ( (Listitem)e );
        if ( validate ( dto, false ) )
            getSession().update( getLoggedInUser(), dto );
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
        btnProperties.setDisabled( true );
        return getSession().getForms( getLoggedInUser() );
    }

    @Override
    protected void prepareToInsert()
    {
        super.prepareToInsert();
        editIP.setValue( "" );
        editIP.setFocus( true );
        btnRemoveAttach.setDisabled( true );
        btnProperties.setDisabled( true );
    }

    @Override
    protected FormDTO prepareToUpdate( Object currentRecord )
    {
        FormDTO dto = ( FormDTO )super.prepareToUpdate( currentRecord );
        if ( dto != null )
            editIP.setValue( dto.getApplication() );
        editIP.setFocus( true );
        return dto;
    }

    @Override
    protected SimpleTableDTO copyTo( SimpleTableDTO dto )
    {
        FormDTO d = ( FormDTO )super.copyTo( dto );
        if ( d != null ) {
            d.setIp( editIP.getValue() );
            d.setDescription( editDescription.getValue() );
        }
        return d;
    }

    @Override
    public void render ( Listitem item, Object value)
    {
        FormDTO dto = (FormDTO)value;

        if ( dto != null ) {
            item.setValue( value );
            item.getChildren().clear();
            item.getChildren().add( new Listcell( dto.getApplication() ) );
            item.getChildren().add( new Listcell( dto.getDescription() ) );
        }
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        if ( record != null ) {
            super.showRecord( record );
            recordIP.setValue( record != null ? ( ( FormDTO )record ).getApplication() : "" );
            btnAddAttach.setDisabled( record == null );
            listAttachs.setModel( getMediaModel( ( FormDTO )record ) );
            if ( record != null ) {
                //refreshAttachs( ( FormDTO )record );
                //listAdded.setModel( getPenModel( ( FormDTO )record ) );
            }
            else {
                listAvailable.getItems().clear();
                listAdded.getItems().clear();
            }
        }
        else
            clearRecordInfo();
    }

    public void onUpload$btnAddAttach( UploadEvent evt )
    {
        if ( getListboxRecord().getSelectedCount() != 1 ) {
            showErrorMessage( "Não existe nenhum formulário selecionado", "Upload Error" );
            evt.stopPropagation();
            return;
        }
        MediaDTO dto = null;
        try {
            dto = UploadMedia.getMedia( evt.getMedia() );
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "UploadMedia" );
        }
        if ( dto == null )
            return;
        FormDTO form = getValue( getListboxRecord().getSelectedItem() );

        if ( isPadFile( dto ) ) {
            PadDTO addedDTO;
            try {
                dto.setFormat( "pad" );
                addedDTO = getSession().addToForm( getLoggedInUser(), form, dto );
                ListModelList model = ( ListModelList )listAttachs.getModel();
                model.add( addedDTO );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Adicinar PAD" );
            }
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

    protected AbstractListModel getMediaModel( FormDTO currentForm )
    {
        if ( currentForm == null )
            return null;
        List<PadDTO> list;
        ListModelList model = null;
        try {
            list = getSession().getPads( getLoggedInUser(), currentForm );
            model = new ListModelList( list );
            return model;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }


    /*
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
*/

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
            //moveListitem( listAvailable, listAdded );
        }
        else {
            // we are adding forms into this pen
            //moveListitem( listAdded, listAvailable );
        }
    }

    /*
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
            model.loadPage( 1, list.size() );
            return model;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }

    */

    public void onClick$btnRemoveAttach()
    {
        FormDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
        Set selected = listAttachs.getSelectedItems();
        if ( selected.isEmpty() )
            return;
        List al = new ArrayList( selected );
        try {
            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                PadDTO dto = ( PadDTO )li.getValue();
                getSession().removeFromForm( getLoggedInUser(), currentForm, dto.getMedia() );
                ( ( ListModelList )listAttachs.getModel() ).remove( li.getValue() );
            }
            btnRemoveAttach.setDisabled( true );
            btnProperties.setDisabled( true );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Remover Media" );
        }

    }

    public void onSelect$listAttachs()
    {
        btnRemoveAttach.setDisabled( false );
        btnProperties.setDisabled( false );
    }


    public void onClick$btnProperties()
    {
        Listitem item = listAttachs.getSelectedItem();

        if ( item != null && item.getValue() != null ) {
            PadDTO pad = ( PadDTO )item.getValue();
            Properties params = new Properties();
            params.put( AnotoPADController.padIdParameterName, pad );
            gotoPage( "/private/admin/anoto/anoto_pad.zul", getRootParent().getParent(), params );
        }
    }


    protected boolean isPadFile( MediaDTO media )
    {
        String fileExtension;

        if ( media == null || SysUtils.isEmpty( media.getName() ) )
            return false;
        fileExtension = media.getName().toLowerCase();

        if ( fileExtension.endsWith( ".pad" ) == false )
            return false;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream( media.getObject() );
            PenHome.loadPad( "CloudSystems", is, true );
            return true;
        }
        catch ( IllegalValueException e ) {
            showErrorMessage( e.getMessage(), "IllegalValueException" );
            return false;
        }
        catch ( FormatException e ) {
            showErrorMessage( e.getMessage(), "FormatException" );
            return false;
        }
        catch ( NotAllowedException e ) {
            showErrorMessage( e.getMessage(), "NotAllowedException" );
            return false;
        }
    }

    public void onDoubleClick$listAttachs()
    {
        onClick$btnProperties();
    }
}

