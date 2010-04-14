package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.controller.anoto.model.FormApplicationComparator;
import br.com.mcampos.controller.anoto.model.FormDescriptionApplication;
import br.com.mcampos.controller.anoto.model.FormListModel;
import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.anoto.renderer.PenListRenderer;
import br.com.mcampos.controller.anoto.util.PadFile;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.system.UploadMedia;

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
import org.zkoss.zul.Listheader;
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
    protected Button btnAddAttachOther;
    protected Button btnRemoveAttachOther;
    protected Button btnAddPen;
    protected Button btnRemovePen;

    protected Listbox listAttachs;
    protected Listbox listAvailable;
    protected Listbox listAdded;
    protected Listbox listAttachsOther;

    protected Listheader headerApplication;
    protected Listheader headerDescription;


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

    @Override
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

    protected void clearAllFormLists()
    {
        listAdded.getChildren().clear();
        listAttachs.getChildren().clear();
        listAttachsOther.getChildren().clear();
        listAvailable.getChildren().clear();
    }

    @Override
    protected Object createNewRecord()
    {
        return new FormDTO();
    }

    @Override
    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( FormDTO )currentRecord );
    }

    protected boolean validate( FormDTO dto, boolean bNew )
    {
        if ( SysUtils.isEmpty( dto.getDescription() ) ) {
            showErrorMessage( "A descrição do formulário deve estar preenchida", "Formulário" );
            return false;
        }
        if ( SysUtils.isEmpty( dto.getApplication() ) ) {
            showErrorMessage( "A aplicação do formulário deve estar preenchida", "Formulário" );
            return false;
        }
        if ( bNew ) {
            FormDTO existDTO;
            try {
                existDTO = getSession().get( getLoggedInUser(), dto );
                if ( existDTO != null ) {
                    showErrorMessage( "Já existe um registro com este código de formulário.", "Formulário" );
                }
            }
            catch ( ApplicationException e ) {
                e = null;
            }
        }
        return true;
    }

    @Override
    protected void persist( Object e ) throws ApplicationException
    {
        FormDTO dto = ( FormDTO )e;
        if ( validate( dto, isAddNewOperation() ) == false )
            return;
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), dto );
        else
            getSession().update( getLoggedInUser(), dto );
    }


    @Override
    protected FormDTO getValue( Listitem selecteItem )
    {
        return ( FormDTO )super.getValue( selecteItem );
    }

    @Override
    protected List getRecordList() throws ApplicationException
    {
        btnAddAttach.setDisabled( true );
        btnAddAttachOther.setDisabled( true );
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
    protected Object saveRecord( Object dto )
    {
        FormDTO d = ( FormDTO )super.saveRecord( dto );
        d.setIp( editIP.getValue() );
        return d;
    }

    @Override
    public void render( Listitem item, Object value )
    {
        FormDTO dto = ( FormDTO )value;

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
            listAttachs.setModel( getMediaModel( ( FormDTO )record ) );
            refreshPens( ( FormDTO )record );
            //listAdded.setModel( getPenModel( ( FormDTO )record ) );
        }
        else {
            clearRecordInfo();
            listAvailable.getItems().clear();
            listAdded.getItems().clear();
            listAttachsOther.getItems().clear();
        }
        btnAddAttach.setDisabled( record == null );
        recordIP.setValue( record != null ? ( ( FormDTO )record ).getApplication() : "" );
        btnAddAttachOther.setDisabled( record == null );
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
        try {
            PadFile file = new PadFile( form, dto.getObject() );

            if ( file.isPadFile( form, dto ) ) {
                PadDTO addedDTO;

                List<String> pages = new ArrayList<String>( file.getPages().size() );
                for ( int nIndex = 0; nIndex < file.getPages().size(); nIndex++ )
                    pages.add( file.getPageAddress( file.getPages().get( nIndex ) ) );
                addedDTO = getSession().addToForm( getLoggedInUser(), form, dto, pages );
                ListModelList model = ( ListModelList )listAttachs.getModel();
                model.add( addedDTO );
            }
            else {
                showErrorMessage( "Erro ao registrar o arquivo PAD", "Adicinar PAD" );
            }
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Adicinar PAD" );
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
        headerApplication.setSortAscending( new FormApplicationComparator( true ) );
        headerApplication.setSortDescending( new FormApplicationComparator( false ) );

        headerDescription.setSortAscending( new FormDescriptionApplication( true ) );
        headerDescription.setSortDescending( new FormDescriptionApplication( false ) );
    }

    protected AbstractListModel getMediaModel( FormDTO currentForm )
    {
        if ( currentForm == null )
            return null;
        List<PadDTO> list;
        ListModelList model = null;
        try {
            list = getSession().getPads( currentForm );
            model = new ListModelList( list );
            return model;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }

    protected void moveListitem( ListModelList toListbox, ListModelList fromListbox )
    {
        if ( getListboxRecord().getSelectedCount() != 1 )
            return;
        FormDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
        Set selected = fromListbox.getSelection();
        if ( selected.isEmpty() )
            return;
        ArrayList<PenDTO> itens = new ArrayList<PenDTO>();
        try {
            for ( Iterator it = selected.iterator(); it.hasNext(); ) {
                itens.add( ( PenDTO )it.next() );
            }
            if ( toListbox.equals( listAvailable.getModel() ) ) {
                getSession().removePens( getLoggedInUser(), currentForm, itens );
            }
            else {
                getSession().addPens( getLoggedInUser(), currentForm, itens );
            }
            toListbox.addAll( selected );
            fromListbox.removeAll( selected );
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
            moveListitem( getModel( listAvailable ), getModel( listAdded ) );
        }
        else {
            // we are adding forms into this pen
            moveListitem( getModel( listAdded ), getModel( listAvailable ) );
        }
    }

    protected ListModelList getModel( Listbox target )
    {
        ListModelList modelList = ( ListModelList )target.getModel();
        if ( modelList == null ) {
            modelList = new ListModelList( new ArrayList<Object>() );
            target.setModel( modelList );
        }
        return modelList;
    }

    public void onClick$btnAddPen()
    {
        moveListitem( ( ListModelList )listAdded.getModel(), ( ListModelList )listAvailable.getModel() );
    }

    public void onClick$btnRemovePen()
    {
        moveListitem( ( ListModelList )listAvailable.getModel(), ( ListModelList )listAdded.getModel() );
    }


    protected void refreshPens( FormDTO current )
    {
        listAvailable.setModel( getAvailablePensListModel( current ) );
        listAvailable.invalidate();
        listAdded.setModel( getPensListModel( current ) );
        listAdded.invalidate();
        listAttachsOther.setModel( getFilesListModel( current ) );
    }


    protected ListModelList getAvailablePensListModel( FormDTO current )
    {
        List<PenDTO> list;
        try {
            list = getSession().getAvailablePens( getLoggedInUser(), current );
            if ( SysUtils.isEmpty( list ) )
                list = new ArrayList<PenDTO>();
            return new ListModelList( list, true );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }

    protected ListModelList getPensListModel( FormDTO current )
    {
        List<PenDTO> list;
        try {
            list = getSession().getPens( getLoggedInUser(), current );
            if ( SysUtils.isEmpty( list ) )
                list = new ArrayList<PenDTO>();
            return new ListModelList( list, true );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }

    protected ListModelList getFilesListModel( FormDTO current )
    {
        List<MediaDTO> list;
        try {
            list = getSession().getFiles( getLoggedInUser(), current );
            if ( SysUtils.isEmpty( list ) )
                list = new ArrayList<MediaDTO>();
            return new ListModelList( list, true );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }


    public void onClick$btnRemoveAttach()
    {
        FormDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
        Set selected = listAttachs.getSelectedItems();
        if ( selected.isEmpty() )
            return;
        List al = new ArrayList( selected );
        PadFile padFile = new PadFile( currentForm );
        try {
            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                PadDTO dto = ( PadDTO )li.getValue();
                getSession().removeFromForm( getLoggedInUser(), currentForm, dto.getMedia() );
                padFile.unregister( currentForm, dto.getMedia() );
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

    public void onSelect$listAttachsOther()
    {
        btnRemoveAttachOther.setDisabled( false );
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

    public void onUpload$btnAddAttachOther( UploadEvent evt )
    {
        if ( getListboxRecord().getSelectedCount() != 1 ) {
            showErrorMessage( "Não existe nenhum formulário selecionado", "Upload Error" );
            evt.stopPropagation();
            return;
        }
        MediaDTO dto = null;

        try {
            dto = UploadMedia.getMedia( evt.getMedia() );
            if ( dto == null )
                return;
            FormDTO form = getValue( getListboxRecord().getSelectedItem() );
            ListModelList model = ( ListModelList )listAttachsOther.getModel();
            model.add( addPadFile( form, dto ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Adicinar Arquivo" );
        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "UploadMedia" );
        }
    }

    protected MediaDTO addPadFile( FormDTO form, MediaDTO newPad ) throws ApplicationException
    {
        MediaDTO dto = getSession().addFile( getLoggedInUser(), form, newPad );
        return dto;
    }


    public void onClick$btnRemoveAttachOther()
    {
        FormDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
        ListModelList modelList = ( ( ListModelList )listAttachsOther.getModel() );
        Set selected = modelList.getSelection();
        if ( selected.isEmpty() )
            return;
        try {
            for ( Iterator it = selected.iterator(); it.hasNext(); ) {
                getSession().removeFile( getLoggedInUser(), currentForm, ( MediaDTO )it.next() );
            }
            modelList.removeAll( selected );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Remover Media" );
        }

    }


    public void onDoubleClick$listAttachs()
    {
        onClick$btnProperties();
    }

    @Override
    protected ListModelList getModel()
    {
        FormListModel listModel;

        listModel = ( FormListModel )getListboxRecord().getModel();
        if ( listModel == null ) {
            listModel = new FormListModel();
            getListboxRecord().setModel( listModel );
        }
        return listModel;
    }
}
