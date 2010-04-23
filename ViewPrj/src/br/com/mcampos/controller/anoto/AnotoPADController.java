package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.model.AnotoPageFieldComparator;
import br.com.mcampos.controller.anoto.model.PenListModel;
import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.anoto.renderer.PageFieldRowRenderer;
import br.com.mcampos.controller.anoto.renderer.PenListRenderer;
import br.com.mcampos.controller.anoto.util.IAnotoPageFieldEvent;
import br.com.mcampos.controller.anoto.util.PadFile;
import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;
import br.com.mcampos.dto.anoto.PadDTO;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.system.UploadMedia;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;


public class AnotoPADController extends AnotoBaseController<AnotoPageDTO> implements IAnotoPageFieldEvent
{
    public static final String padIdParameterName = "padId";

    private PadDTO padParam;
    protected Label recordId;
    protected Label recordDescription;
    protected Textbox editId;
    protected Textbox editDescription;
    protected Button btnAddAttach;
    protected Button btnRemoveAttach;
    protected Button btnProperties;
    protected Listbox listAttachs;
    protected Listbox listAvailable;
    protected Listbox listAdded;
    protected Grid gridFields;
    protected Column headerName;
    protected Column headerType;
    protected Column headerIcr;


    public AnotoPADController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        cmdCreate.setVisible( false );
        cmdDelete.setVisible( false );
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
        if ( gridFields != null ) {
            gridFields.setRowRenderer( new PageFieldRowRenderer( this, getSession().getFieldTypes( getLoggedInUser() ) ) );
            if ( headerIcr != null ) {
                headerIcr.setSortAscending( new AnotoPageFieldComparator( true, AnotoPageFieldComparator.headerIcr ) );
                headerIcr.setSortDescending( new AnotoPageFieldComparator( false, AnotoPageFieldComparator.headerIcr ) );
            }
            if ( headerType != null ) {
                headerType.setSortAscending( new AnotoPageFieldComparator( true, AnotoPageFieldComparator.headerType ) );
                headerType.setSortDescending( new AnotoPageFieldComparator( false, AnotoPageFieldComparator.headerType ) );
            }
            if ( headerName != null ) {
                headerName.setSortAscending( new AnotoPageFieldComparator( true, AnotoPageFieldComparator.headerName ) );
                headerName.setSortDescending( new AnotoPageFieldComparator( false, AnotoPageFieldComparator.headerName ) );
            }
        }
    }

    @Override
    public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
    {
        Object param = getParameter();

        if ( param != null )
            return super.doBeforeCompose( page, parent, compInfo );
        else
            return null;
    }

    protected Object getParameter()
    {
        Object param;

        Map args = Executions.getCurrent().getArg();
        if ( args == null || args.size() == 0 )
            args = Executions.getCurrent().getParameterMap();
        param = args.get( padIdParameterName );
        if ( param instanceof PadDTO ) {
            padParam = ( PadDTO )param;
            return padParam;
        }
        else
            return null;
    }

    @Override
    public void render( Listitem item, Object value )
    {
        AnotoPageDTO dto = ( AnotoPageDTO )value;

        if ( dto != null ) {
            item.setValue( value );
            item.getChildren().add( new Listcell( dto.getPageAddress() ) );
            item.getChildren().add( new Listcell( dto.getDescription() ) );
        }
    }

    @Override
    protected void showRecord( AnotoPageDTO record ) throws ApplicationException
    {
        if ( record != null ) {
            recordId.setValue( record.getPageAddress() );
            recordDescription.setValue( record.getDescription() );
            listAttachs.setModel( getMediaModel( record ) );
            refreshAttachs( record );
            listAdded.setModel( getPenModel( record ) );
            refreshFields( record );
        }
        else {
            listAvailable.getItems().clear();
            listAdded.getItems().clear();
        }
        btnAddAttach.setDisabled( record == null );
    }

    @Override
    protected Object createNewRecord()
    {
        return new AnotoPageDTO();
    }

    protected AnotoPageDTO copyTo( AnotoPageDTO dto )
    {
        dto.setDescription( editDescription.getValue() );
        return dto;
    }

    @Override
    protected List getRecordList()
    {
        try {
            return getSession().getPages( getLoggedInUser(), padParam );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Pages" );
            return Collections.emptyList();
        }
    }

    protected void delete( Listitem currentRecord )
    {
    }

    @Override
    protected void clearRecordInfo()
    {
        editId.setValue( "" );
        editDescription.setValue( "" );
    }

    @Override
    protected void prepareToInsert()
    {
        clearRecordInfo();
        editId.setDisabled( true );
    }

    @Override
    protected Object prepareToUpdate( Object currentRecord )
    {
        AnotoPageDTO dto = ( AnotoPageDTO )currentRecord;

        editId.setValue( dto.getPageAddress() );
        editId.setDisabled( true );
        editDescription.setValue( dto.getDescription() );
        return null;
    }

    @Override
    protected void persist( Object e )
    {
    }

    protected void updateItem( Object e )
    {
    }


    protected AbstractListModel getMediaModel( AnotoPageDTO current )
    {
        if ( current == null )
            return null;
        List<MediaDTO> list;
        ListModelList model = null;
        try {
            list = getSession().getImages( current );
            model = new ListModelList( list );
            return model;
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Lista de Formulários" );
            return null;
        }
    }


    public void onClick$btnRemoveAttach()
    {
        AnotoPageDTO current = getValue( getListboxRecord().getSelectedItem() );
        Set selected = listAttachs.getSelectedItems();
        if ( selected.isEmpty() )
            return;
        List al = new ArrayList( selected );
        try {
            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                getSession().removeFromPage( getLoggedInUser(), current, ( ( MediaDTO )li.getValue() ) );
                ( ( ListModelList )listAttachs.getModel() ).remove( li.getValue() );
            }
            btnRemoveAttach.setDisabled( true );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Remover Media" );
        }
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
            showErrorMessage( e.getMessage(), "Upload Media" );
        }
        if ( dto == null )
            return;
        AnotoPageDTO page = getValue( getListboxRecord().getSelectedItem() );

        MediaDTO addedDTO;
        try {
            addedDTO = getSession().addToPage( getLoggedInUser(), page, dto );
            ListModelList model = ( ListModelList )listAttachs.getModel();
            model.add( addedDTO );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Adicinar Imagem de Fundo" );
        }
    }


    protected void moveListitem( Listbox toListbox, Listbox fromListbox )
    {
        if ( getListboxRecord().getSelectedCount() != 1 )
            return;
        AnotoPageDTO currentForm = getValue( getListboxRecord().getSelectedItem() );
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
                getSession().removePens( getLoggedInUser(), currentForm, itens );
            else
                getSession().addPens( getLoggedInUser(), currentForm, itens );
            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                li.setSelected( false );
                toListbox.appendChild( li );
            }
        }
        catch ( Exception e ) {
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
            //we are adding forms into this pen
            moveListitem( listAdded, listAvailable );
        }
    }


    protected AbstractListModel getAvailablePensListModel( AnotoPageDTO current )
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

    protected void refreshAttachs( AnotoPageDTO current )
    {
        listAvailable.setModel( getAvailablePensListModel( current ) );
        listAvailable.invalidate();
    }

    protected AbstractListModel getPenModel( AnotoPageDTO current )
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

    public void onSelect$listAttachs()
    {
        btnRemoveAttach.setDisabled( false );
    }

    @Override
    protected void delete( Object currentRecord )
    {
    }

    @Override
    protected void afterDelete( Object currentRecord )
    {
    }

    @Override
    protected Object saveRecord( Object currentRecord )
    {
        return null;
    }

    public void onClick$btnRefreshFields()
    {
        Listitem item = getListboxRecord().getSelectedItem();

        if ( item == null ) {
            showErrorMessage( "Por favor selecione um endereço de página na lista", "Atualizar Campos" );
            return;
        }
        AnotoPageDTO page = ( AnotoPageDTO )item.getValue();
        if ( page == null ) {
            showErrorMessage( "Não existem dados da página associados", "Atualizar Campos" );
            return;
        }
        byte[] padByte;
        try {
            padByte = getSession().getObject( page.getPad().getMedia() );
            if ( padByte != null ) {
                PadFile padFile;
                padFile = new PadFile( page.getPad().getForm(), padByte );
                List<AnotoPageFieldDTO> fields = padFile.getFields( page );
                if ( SysUtils.isEmpty( fields ) == false ) {
                    for ( AnotoPageFieldDTO field : fields )
                        field.setPage( page );
                    getSession().refreshFields( getLoggedInUser(), fields );
                    refreshFields( fields );
                }
            }
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Atualizar Campos" );
        }
    }

    protected void refreshFields( List<AnotoPageFieldDTO> fields )
    {
        if ( SysUtils.isEmpty( fields ) )
            fields = new ArrayList<AnotoPageFieldDTO>();
        gridFields.setModel( new ListModelList( fields, true ) );
    }

    protected void refreshFields( AnotoPageDTO record ) throws ApplicationException
    {
        List<AnotoPageFieldDTO> fields;

        fields = getSession().getFields( getLoggedInUser(), record );
        refreshFields( fields );
    }

    public void onSelect( org.zkoss.zk.ui.event.SelectEvent evt )
    {
        if ( evt.getTarget() != null && evt.getTarget() instanceof Combobox ) {
            Combobox target = ( Combobox )evt.getTarget();
            if ( target.getParent() instanceof Row ) {
                Row row = ( ( Row )target.getParent() );
                Object value = row.getValue();
                if ( value instanceof AnotoPageFieldDTO ) {
                    Comboitem item = ( Comboitem )evt.getReference();
                    FieldTypeDTO type = ( FieldTypeDTO )item.getValue();
                    AnotoPageFieldDTO dto = ( AnotoPageFieldDTO )value;
                    dto.setType( type );
                    tryUpdate( dto );
                }
            }
        }
    }

    public void onCheck( org.zkoss.zk.ui.event.CheckEvent evt )
    {
        if ( evt.getTarget() != null && evt.getTarget() instanceof Checkbox ) {
            Checkbox target = ( Checkbox )evt.getTarget();
            if ( target.getParent() instanceof Row ) {
                Row row = ( ( Row )target.getParent() );
                Object value = row.getValue();
                if ( value instanceof AnotoPageFieldDTO ) {
                    AnotoPageFieldDTO dto = ( AnotoPageFieldDTO )value;
                    dto.setIcr( evt.isChecked() );
                    tryUpdate( dto );
                }
            }
        }
    }

    protected void tryUpdate( AnotoPageFieldDTO dto )
    {
        try {
            getSession().update( getLoggedInUser(), dto );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Atualizar Campo" );
        }
    }
}
