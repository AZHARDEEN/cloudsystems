package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.controller.anoto.model.FormApplicationComparator;
import br.com.mcampos.controller.anoto.model.FormDescriptionApplication;
import br.com.mcampos.controller.anoto.model.FormListModel;
import br.com.mcampos.controller.anoto.renderer.AnotoFormListRenderer;
import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.anoto.renderer.PenListRenderer;
import br.com.mcampos.controller.anoto.util.PadFile;
import br.com.mcampos.controller.user.UserListRenderer;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormFacade;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.system.UploadMedia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;


public class AnotoFormController extends SimpleTableController<FormDTO>
{
    @SuppressWarnings( "compatibility:-4187399303111746730" )
    private static final long serialVersionUID = 6328445810601958206L;
    private AnotoFormFacade session;


    private Button btnAddAttach;
    private Button btnAddAttachOther;
    private Button btnRemoveAttach;
    private Button btnRemoveAttachOther;
    private Button btnProperties;
    private Button btnExport;
    private Button btnExportOther;
    private Button btnAddUser;
    private Button btnRemoveUser;

    private Listbox listAttachs;
    private Listbox listAttachsOther;
    private Listbox listAvailable;
    private Listbox listAdded;
    private Listbox listUsers;

    private Listheader headerApplication;
    private Listheader listHeaderName;
    private Listheader listHeadeCodeAvailable;
    private Listheader listHeaderCode;
    private Listheader listHeaderNameOther;
    private Listheader listUserCode;
    private Listheader listUserName;


    private Label labelAnotoFormTitle;
    private Label labelApplication;
    private Label labelEditApplication;
    private Label labelLinked;
    private Label labelAvailable;
    private Label labelIcrImage;
    private Label labelEditIcrImage;
    private Label labelEditImagePath;
    private Label labelImagePath;
    private Label labelConcatPgc;
    private Label labelEditConcatPgc;
    private Label labelFormUser;
    private Label labelEditFormUser;
    private Label labelUserCode;


    private Panel panelLinks;
    private Tab tabPadFile;
    private Tab tabFormUsers;
    private Tab tabPen;
    private Tab tabOtherFiles;

    /*
     * Form Fields!
     */
    private Checkbox editIcrImage;
    private Checkbox editConcatPgc;
    private Textbox editImagePath;
    private Label recordImagePath;
    private Label recordIcrImage;
    private Label recordConcatPgc;
    private Textbox editIP;
    private Label recordIP;


    public AnotoFormController()
    {
        super();
    }

    private AnotoFormFacade getSession()
    {
        if ( session == null )
            session = ( AnotoFormFacade )getRemoteSession( AnotoFormFacade.class );
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

    private void clearAllFormLists()
    {
        try {
            listAdded.getItems().clear();
            listAttachs.getItems().clear();
            listAvailable.getItems().clear();
            listAttachsOther.getItems().clear();
            listUsers.getItems().clear();
        }
        catch ( Exception e ) {
            e = null;
        }
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

    private boolean validate( FormDTO dto, boolean bNew )
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
        btnRemoveAttachOther.setDisabled( true );
        btnProperties.setDisabled( true );
        btnAddUser.setDisabled( true );
        return getSession().getForms( getLoggedInUser() );
    }

    @Override
    protected void prepareToInsert()
    {
        super.prepareToInsert();
        editIP.setValue( "" );
        editIP.setFocus( true );
        btnRemoveAttach.setDisabled( true );
        btnRemoveAttachOther.setDisabled( true );
        btnProperties.setDisabled( true );
        btnAddUser.setDisabled( true );
        editIcrImage.setChecked( false );
        editConcatPgc.setChecked( false );
        editImagePath.setText( "" );
    }

    @Override
    protected FormDTO prepareToUpdate( Object currentRecord )
    {
        FormDTO dto = ( FormDTO )super.prepareToUpdate( currentRecord );
        if ( dto != null )
            editIP.setValue( dto.getApplication() );
        editIP.setFocus( true );
        editIcrImage.setChecked( dto.getIcrImage() );
        editImagePath.setText( dto.getImagePath() );
        editConcatPgc.setChecked( dto.getConcatenatePgc() );
        return dto;
    }

    @Override
    protected Object saveRecord( Object dto )
    {
        FormDTO d = ( FormDTO )super.saveRecord( dto );
        d.setApplication( editIP.getValue() );
        d.setIcrImage( editIcrImage.isChecked() );
        d.setImagePath( editImagePath.getValue() );
        d.setConcatenatePgc( editConcatPgc.isChecked() );
        return d;
    }

    @Override
    protected ListitemRenderer getRenderer()
    {
        return new AnotoFormListRenderer();
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        FormDTO formDTO = ( ( FormDTO )record );
        if ( record != null ) {
            super.showRecord( record );
            listAttachs.setModel( getMediaModel( formDTO ) );
            refreshPens( formDTO );
            refreshOtherAttachs( formDTO );
            refreshUsers( formDTO );
        }
        else {
            clearRecordInfo();
            listAvailable.getItems().clear();
            listAdded.getItems().clear();
        }
        btnAddAttach.setDisabled( record == null );
        btnAddAttachOther.setDisabled( record == null );
        btnAddUser.setDisabled( record == null );
        recordIP.setValue( formDTO != null ? formDTO.getApplication() : "" );
        recordIcrImage.setValue( formDTO != null ? formDTO.getIcrImage().toString() : "" );
        recordConcatPgc.setValue( formDTO != null ? formDTO.getConcatenatePgc().toString() : "" );
        recordImagePath.setValue( formDTO != null ? formDTO.getImagePath() : "" );
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
                for ( int nIndex = 0; nIndex < file.getPages().size(); nIndex++ ) {
                    String strPage = file.getPageAddress( file.getPages().get( nIndex ) );
                    pages.add( strPage );
                }
                dto.setMimeType( "application/xml" );
                addedDTO = getSession().addToForm( getLoggedInUser(), form, dto, pages, file.getUnique() );
                try {
                    AnotoPageDTO anotoPage = new AnotoPageDTO();
                    anotoPage.setPad( addedDTO );
                    for ( String page : pages ) {
                        anotoPage.setPageAddress( page );
                        List<AnotoPageFieldDTO> fields = file.getFields( anotoPage );
                        if ( SysUtils.isEmpty( fields ) == false )
                            getSession().addToPage( getLoggedInUser(), addedDTO, page, fields );
                    }
                }
                catch ( Exception e ) {
                    e = null;
                }
                ListModelList model = ( ListModelList )listAttachs.getModel();
                model.add( addedDTO );
            }
            else {
                showErrorMessage( "Erro ao registrar o arquivo PAD", "Adicinar PAD" );
            }
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Adicionar PAD" );
        }
    }


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        if ( listAttachs != null ) {
            listAttachs.setItemRenderer( new MediaListRenderer() );
        }
        if ( listAttachsOther != null ) {
            listAttachsOther.setItemRenderer( new MediaListRenderer() );
        }
        if ( listUsers != null ) {
            listUsers.setItemRenderer( new UserListRenderer() );
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

        getHeaderDescription().setSortAscending( new FormDescriptionApplication( true ) );
        getHeaderDescription().setSortDescending( new FormDescriptionApplication( false ) );

        configureLabels();
    }


    private AbstractListModel getMediaModel( FormDTO currentForm )
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

    private void moveListitem( ListModelList toListbox, ListModelList fromListbox )
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

    private ListModelList getModel( Listbox target )
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


    private void refreshPens( FormDTO current )
    {
        listAvailable.setModel( getAvailablePensListModel( current ) );
        listAvailable.invalidate();
        listAdded.setModel( getPensListModel( current ) );
        listAdded.invalidate();
    }


    private ListModelList getAvailablePensListModel( FormDTO current )
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

    private ListModelList getPensListModel( FormDTO current )
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

    private ListModelList getFilesListModel( FormDTO current )
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
        if ( selected.isEmpty() ) {
            showErrorMessage( "noCurrentRecordMessage" );
            return;
        }
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
            btnRemoveUser.setDisabled( true );
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

    public void onSelect$listUsers()
    {
        btnRemoveUser.setDisabled( false );
    }

    public void onSelect$listAttachsOther()
    {
        btnRemoveAttachOther.setDisabled( false );
    }

    public void onClick$btnAddUser()
    {
        FormDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
        if ( currentForm == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        try {
            final Window win = ( Window )Executions.createComponents( "/private/admin/anoto/dlg_select_company.zul", null, null );
            win.setAttribute( "form", currentForm );
            win.doModal();
            if ( win != null )
                win.detach();

        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    public void onClick$btnRemoveUser()
    {
        FormDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
        if ( listUsers.getSelectedItem() == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        ListUserDTO dto = ( ListUserDTO )listUsers.getSelectedItem().getValue();
        if ( currentForm == null || dto == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        try {
            if ( dto.getId().equals( getLoggedInUser().getCurrentCompany() ) == false )
                getSession().deleteCompany( getLoggedInUser(), currentForm, dto );
            else
                showErrorMessage( "Não é permitido excluir a empresa corrente" );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }


    public void onClick$btnProperties()
    {
        Listitem item = listAttachs.getSelectedItem();
        if ( item == null ) {
            showErrorMessage( "noCurrentRecordMessage" );
            return;
        }
        PadDTO pad = ( PadDTO )item.getValue();
        Properties params = new Properties();
        params.put( AnotoPADController.padIdParameterName, pad );
        gotoPage( "/private/admin/anoto/anoto_pad.zul", getRootParent().getParent(), params );
    }


    private MediaDTO addPadFile( FormDTO form, MediaDTO newPad ) throws ApplicationException
    {
        MediaDTO dto = getSession().addFile( getLoggedInUser(), form, newPad );
        return dto;
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

    public void onClick$btnExport()
    {
        try {
            Listitem item = listAttachs.getSelectedItem();
            if ( item == null ) {
                showErrorMessage( "noCurrentRecordMessage" );
                return;
            }

            PadDTO pad = ( PadDTO )item.getValue();
            byte[] obj;
            obj = getSession().getObject( pad.getMedia() );
            if ( obj != null ) {
                Filedownload.save( obj, pad.getMedia().getMimeType(), pad.getMedia().getName() );
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Download" );
        }
    }

    public void onClick$btnAddAttachOther()
    {
        FormDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
        if ( currentForm == null ) {
            showErrorMessage( "noCurrentRecordMessage" );
            return;
        }
        try {
            Media[] medias = Fileupload.get( "Escolha os arquivos ", "Arquivos", 3, 6 * 1024 * 1024, true );
            if ( medias != null && medias.length > 0 ) {
                MediaDTO[] dtos = new MediaDTO[ medias.length ];
                for ( int nIndex = 0; nIndex < medias.length; nIndex++ ) {
                    dtos[ nIndex ] = UploadMedia.getMedia( medias[ nIndex ] );
                }
                getSession().addMedias( getLoggedInUser(), currentForm, dtos );
                ListModelList model = ( ListModelList )listAttachsOther.getModel();
                if ( model != null ) {
                    for ( int nIndex = 0; nIndex < dtos.length; nIndex++ ) {
                        model.add( dtos[ nIndex ] );
                    }
                }
            }
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Erro ao realizar upload" );
        }
    }

    public void onClick$btnRemoveAttachOther()
    {
        FormDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
        Set selected = listAttachsOther.getSelectedItems();
        if ( selected.isEmpty() ) {
            showErrorMessage( "noCurrentRecordMessage" );
            return;
        }
        ArrayList al = new ArrayList( selected );
        MediaDTO[] medias = new MediaDTO[ selected.size() ];
        try {
            int nIndex = 0;
            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                MediaDTO dto = ( MediaDTO )li.getValue();
                medias[ nIndex ] = dto;
                nIndex++;
            }
            getSession().removeMedias( getLoggedInUser(), currentForm, medias );
            for ( nIndex = 0; nIndex < medias.length; nIndex++ )
                ( ( ListModelList )listAttachsOther.getModel() ).remove( medias[ nIndex ] );
            btnRemoveAttachOther.setDisabled( true );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Remover Media" );
        }

    }

    public void onClick$btnExportOther()
    {
        try {
            Listitem item = listAttachsOther.getSelectedItem();
            if ( item == null ) {
                showErrorMessage( "noCurrentRecordMessage" );
                return;
            }

            MediaDTO media = ( MediaDTO )item.getValue();
            byte[] obj;
            obj = getSession().getObject( media );
            if ( obj != null ) {
                Filedownload.save( obj, media.getMimeType(), media.getName() );
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Download" );
        }
    }

    private void refreshOtherAttachs( FormDTO current )
    {
        try {
            List<MediaDTO> medias = getSession().getMedias( getLoggedInUser(), current );
            listAttachsOther.setModel( new ListModelList( !SysUtils.isEmpty( medias ) ? medias : new ArrayList<MediaDTO>() ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Erro ao obter medias" );
        }
    }

    private void refreshUsers( FormDTO current )
    {
        try {
            List<ListUserDTO> users = getSession().getCompanies( getLoggedInUser(), current );
            listUsers.setModel( new ListModelList( !SysUtils.isEmpty( users ) ? users : new ArrayList<MediaDTO>() ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Erro ao obter medias" );
        }
    }

    private void configureLabels()
    {
        setLabel( btnAddAttach );
        setLabel( btnAddAttachOther );
        setLabel( btnRemoveAttach );
        setLabel( btnRemoveAttachOther );
        setLabel( btnProperties );

        setLabel( headerApplication );
        setLabel( listHeaderName );
        setLabel( listHeadeCodeAvailable );
        setLabel( listHeaderCode );
        setLabel( listHeaderNameOther );

        setLabel( labelAnotoFormTitle );
        setLabel( labelApplication );
        setLabel( labelEditApplication );
        setLabel( labelAvailable );
        setLabel( labelLinked );
        setLabel( labelIcrImage );
        setLabel( labelEditIcrImage );
        setLabel( labelEditImagePath );
        setLabel( labelImagePath );
        setLabel( labelConcatPgc );
        setLabel( labelEditConcatPgc );
        setLabel( labelFormUser );
        setLabel( labelEditFormUser );
        setLabel( labelUserCode );

        setLabel( panelLinks );
        setLabel( tabPadFile );
        setLabel( tabPen );
        setLabel( tabOtherFiles );

        setLabel( btnExport );
        setLabel( btnExportOther );

        setLabel( btnAddUser );
        setLabel( btnRemoveUser );
        setLabel( listUserCode );
        setLabel( listUserName );
        setLabel( tabFormUsers );
    }
}


