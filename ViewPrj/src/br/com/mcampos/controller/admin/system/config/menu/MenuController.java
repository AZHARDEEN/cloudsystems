package br.com.mcampos.controller.admin.system.config.menu;

import br.com.mcampos.controller.core.BasicTreeCRUDController;

import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.MenuLocator;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

public class MenuController extends BasicTreeCRUDController<MenuDTO>
{

    protected MenuLocator locator;


    Label recordId;
    Label recordDescription;
    Label recordParent;
    Label recordURL;
    Label recordSequence;
    Checkbox recordSeparator;
    Checkbox recordAutocheck;
    Checkbox recordChecked;
    Checkbox recordCheckmark;
    Checkbox recordDisabled;


    Intbox editId;
    Textbox editDescription;
    Intbox editParent;
    Textbox editURL;
    Intbox editSequence;
    Checkbox editSeparator;
    Checkbox editAutocheck;
    Checkbox editChecked;
    Checkbox editCheckmark;
    Checkbox editDisabled;


    public MenuController( char c )
    {
        super( c );
    }

    public MenuController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        editParent.addEventListener( Events.ON_DROP, new EventListener()
            {
                public void onEvent( Event event ) throws Exception
                {
                    onDrop( event );
                }
            } );
        getTreeList().setTreeitemRenderer( this );
        refresh();
    }


    protected MenuLocator getLocator()
    {
        if ( locator == null )
            locator = new MenuLocator();
        return locator;
    }

    protected void refresh()
    {
        try {
            getTreeList().setModel( new MenuTreeModel( getLocator().get( getLoggedInUser() ) ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Refresh Menu" );
        }
    }

    protected void showRecord( MenuDTO dto ) throws ApplicationException
    {
        recordId.setValue( dto.getId().toString() );
        recordDescription.setValue( dto.getDescription() );
        if ( dto.getParent() != null )
            recordParent.setValue( dto.getParent().toString() );
        else
            recordParent.setValue( "" );
        recordURL.setValue( dto.getTargetURL() );
        recordSequence.setValue( dto.getSequence().toString() );
        recordSeparator.setChecked( dto.getSeparatorBefore() );
        recordAutocheck.setChecked( dto.getAutocheck() );
        recordChecked.setChecked( dto.getChecked() );
        recordCheckmark.setChecked( dto.getCheckmark() );
        recordDisabled.setChecked( dto.getDisabled() );
    }

    protected int getNextId()
    {
        try {
            return getLocator().getNextId( getLoggedInUser() );
        }
        catch ( ApplicationException e ) {
            e = null;
            return 0;
        }

    }

    protected void prepareToInsert()
    {
        editId.setValue( getNextId() );
        editId.setReadonly( false );
        editDescription.setFocus( true );
        editDescription.setValue( "" );
        editParent.setRawValue( 0 );
        editURL.setValue( "" );
        editSequence.setRawValue( 1 );
        editSeparator.setChecked( false );
        editAutocheck.setChecked( false );
        editChecked.setChecked( false );
        editCheckmark.setChecked( false );
        editDisabled.setChecked( false );
    }

    protected void prepareToUpdate( Treeitem currentRecord )
    {
        MenuDTO dto = null;

        dto = getValue( currentRecord );

        editId.setValue( dto.getId() );
        editId.setReadonly( true );
        editDescription.setValue( dto.getDescription() );
        editDescription.setFocus( true );
        editParent.setValue( dto.getParent() == null ? 0 : dto.getParent().getId() );
        editURL.setValue( dto.getTargetURL() );
        editSequence.setValue( dto.getSequence() );
        editSeparator.setChecked( dto.getSeparatorBefore() );
        editAutocheck.setChecked( dto.getAutocheck() );
        editChecked.setChecked( dto.getChecked() );
        editCheckmark.setChecked( dto.getCheckmark() );
        editDisabled.setChecked( dto.getDisabled() );
    }

    /*
     * TODO melhorar a função onDrop
     */

    public void onDrop( Event evt )
    {
        DropEvent de;

        if ( !( evt instanceof DropEvent ) )
            return;
        de = ( DropEvent )evt;
        Object obj = de.getDragged();
        MenuDTO fromDTO = getValue( ( Treeitem )( ( Treerow )obj ).getParent() );
        if ( de.getTarget() instanceof Treerow ) {
            MenuDTO toDTO = getValue( ( Treeitem )( ( Treerow )de.getTarget() ).getParent() );

            if ( obj instanceof Treerow && toDTO != null ) {
                fromDTO.setParent( toDTO );
                try {
                    getLocator().update( getLoggedInUser(), fromDTO );
                }
                catch ( ApplicationException e ) {
                    showErrorMessage( e.getMessage(), "OnDrop Error" );
                }
                moveTreeItem( ( ( Treeitem )de.getTarget().getParent() ), ( ( Treeitem )de.getDragged().getParent() ) );
                MenuDTO parentDTO = toDTO.getParent();
                if ( parentDTO != null )
                    parentDTO.removeSubMenu( fromDTO );
                toDTO.addSubMenu( fromDTO );
                try {
                    showRecord( fromDTO );
                }
                catch ( ApplicationException e ) {
                    showErrorMessage( e.getMessage(), "Show Value" );
                }
            }
        }
        else if ( de.getTarget() instanceof Intbox ) {
            ( ( Intbox )de.getTarget() ).setValue( fromDTO.getId() );
            loadSequence();
        }
    }

    protected void delete( Treeitem treeItem )
    {
        if ( treeItem == null )
            return;

        MenuDTO dto = getValue( treeItem );
        if ( dto != null ) {
            try {
                getLocator().delete( getLoggedInUser(), dto.getId() );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Exclur Menu" );
            }
        }
    }

    protected void insertItem( Treeitem treeItem )
    {
        try {
            getLocator().add( getLoggedInUser(), getValue( treeItem ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Inserir Menu" );
        }
    }

    protected void updateItem( Treeitem treeItem )
    {
        try {
            getLocator().update( getLoggedInUser(), getValue( treeItem ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Atualizar Menu" );
        }
    }

    protected void loadSequence()
    {
        if ( editSequence == null ) {
            showErrorMessage( "Um campo no formulário(editSequence) não está mapeado", "Erro Interno" );
            return;
        }
        if ( isAddNewOperation() ) {
            if ( editSequence.getValue() != 0 ) {
                try {
                    editSequence.setValue( getLocator().getNextSequence( getLoggedInUser(), editParent.getValue() ) );
                }
                catch ( ApplicationException e ) {
                    e = null;
                    editSequence.setValue( 1 );
                }
            }
        }
    }

    public void onBlur$editSequence()
    {
        loadSequence();
    }

    protected MenuDTO createDTO()
    {
        return new MenuDTO();
    }

    protected void copyTo( MenuDTO dto )
    {
        dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue() );
        dto.setParentId( editParent.getValue() );
        dto.setTargetURL( editURL.getValue() );
        dto.setSequence( editSequence.getValue() );
        dto.setSeparatorBefore( editSeparator.isChecked() );
        dto.setAutocheck( editAutocheck.isChecked() );
        dto.setChecked( editChecked.isChecked() );
        dto.setCheckmark( editCheckmark.isChecked() );
        dto.setDisabled( editDisabled.isChecked() );
    }

    public void render( Treeitem item, Object data ) throws Exception
    {
        item.setValue( data );

        // Construct treecells
        Treecell tcNamn = new Treecell( data.toString() );
        Treerow tr = null;

        if ( item.getTreerow() == null ) {
            tr = new Treerow();
            tr.setDraggable( "true" );
            tr.setDroppable( "true" );
            tr.setParent( item );
            tr.addEventListener( Events.ON_DROP, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        onDrop( event );
                    }
                } );
        }
        else {
            tr = item.getTreerow();
            tr.getChildren().clear();
        }
        // Attach treecells to treerow
        tcNamn.setParent( tr );
        item.setOpen( true );
    }
}
