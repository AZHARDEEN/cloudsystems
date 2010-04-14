package br.com.mcampos.controller.admin.system.config.menu;


import br.com.mcampos.controller.admin.system.config.task.TaskTreeModel;
import br.com.mcampos.controller.admin.system.config.task.TaskTreeRenderer;
import br.com.mcampos.controller.core.BasicTreeCRUDController;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;


public class MenuController extends BasicTreeCRUDController<MenuDTO> implements ListitemRenderer
{

    protected MenuLocator locator;


    protected Label recordId;
    protected Label recordDescription;
    protected Label recordParent;
    protected Label recordURL;
    protected Label recordSequence;
    protected Checkbox recordSeparator;
    protected Checkbox recordAutocheck;
    protected Checkbox recordChecked;
    protected Checkbox recordCheckmark;
    protected Checkbox recordDisabled;


    protected Intbox editId;
    protected Textbox editDescription;
    protected Intbox editParent;
    protected Textbox editURL;
    protected Intbox editSequence;
    protected Checkbox editSeparator;
    protected Checkbox editAutocheck;
    protected Checkbox editChecked;
    protected Checkbox editCheckmark;
    protected Checkbox editDisabled;

    protected Button cmdTasks;

    protected Tree treeTasks;


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
        recordEdit.addEventListener( Events.ON_DROP, new EventListener()
            {
                public void onEvent( Event event ) throws Exception
                {
                    onDrop( event );
                }
            } );
        getTreeList().setTreeitemRenderer( this );
        treeTasks.setTreeitemRenderer( new TaskTreeRenderer (true, false) );
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
            getTreeList().setModel( new MenuTreeModel( getLocator().getMenus( getLoggedInUser() ) ) );
            refreshTask();
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
            return getLocator().getNextMenuId( getLoggedInUser() );
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
        getTreeList().clearSelection();
    }

    protected MenuDTO prepareToUpdate( Object e )
    {
        MenuDTO dto = null;

        dto = getValue( ( ( Treeitem )e ) );

        if ( dto != null ) {
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
        return dto;
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
        Object dto = getValue( ( Treeitem )( ( Treerow )obj ).getParent() );
        if ( dto instanceof MenuDTO ) {
            MenuDTO fromDTO = (MenuDTO) dto;
            if ( de.getTarget() instanceof Treerow ) {
                MenuDTO toDTO = getValue( ( Treeitem )( ( Treerow )de.getTarget() ).getParent() );

                if ( obj instanceof Treerow && toDTO != null ) {
                    fromDTO.setParent( toDTO );
                    try {
                        getLocator().update( getLoggedInUser(), fromDTO );
                        moveTreeItem( ( ( Treeitem )de.getTarget().getParent() ), ( ( Treeitem )de.getDragged().getParent() ) );
                        MenuDTO parentDTO = toDTO.getParent();
                        if ( parentDTO != null )
                            parentDTO.removeSubMenu( fromDTO );
                        toDTO.addSubMenu( fromDTO );
                        showRecord( fromDTO );
                    }
                    catch ( ApplicationException e ) {
                        showErrorMessage( e.getMessage(), "OnDrop Error" );
                    }
                }
            }
            else if ( de.getTarget() instanceof Intbox || de.getTarget() instanceof Div ) {
                editParent.setValue( fromDTO.getId() );
                loadSequence();
            }
        }
        else if ( dto instanceof TaskDTO )
        {
            TaskDTO task = ( TaskDTO )dto;
            if ( de.getTarget() instanceof Treerow ) {
                MenuDTO toDTO = getValue( ( Treeitem )( ( Treerow )de.getTarget() ).getParent() );
                try {
                    getLocator().addMenuTask ( getLoggedInUser(), toDTO, task );
                }
                catch ( ApplicationException e ) {
                    showErrorMessage( e.getMessage(), "OnDrop Error" );
                }
            }
        }
    }

    protected void delete( Object e )
    {
        if ( e == null )
            return;

        MenuDTO dto = getValue( ( ( Treeitem )e ) );
        if ( dto != null ) {
            try {
                getLocator().delete( getLoggedInUser(), dto );
            }
            catch ( ApplicationException ex ) {
                showErrorMessage( ex.getMessage(), "Exclur Menu" );
            }
        }
    }

    protected void persist( Object treeItem )
    {
        MenuDTO dto = null;
        try {
            if ( isAddNewOperation() )
                dto = getLocator().add( getLoggedInUser(), getValue( ( Treeitem )treeItem ) );
            else
                dto = getLocator().update( getLoggedInUser(), getValue( ( ( Treeitem )treeItem ) ) );

            if ( dto != null )
                ( ( Treeitem )treeItem ).setValue( dto );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Inserir Menu" );
        }
    }

    protected void updateItem( Object treeItem )
    {
        /*
        try {
            if ( dto != null )
                ( ( Treeitem )treeItem ).setValue( dto );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Atualizar Menu" );
        }
        */
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

    protected MenuDTO copyTo( MenuDTO dto )
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
        return dto;
    }

    protected void configureTreeitem( Treeitem item )
    {
        MenuDTO data = getValue( item );
        Treerow row;

        row = item.getTreerow();
        row.appendChild( new Treecell( data.toString() ) );
        row.appendChild( new Treecell( data.getSequence().toString() ) );
        row.setDraggable( "true" );
        row.setDroppable( "true" );
        row.addEventListener( Events.ON_DROP, new EventListener()
            {
                public void onEvent( Event event ) throws Exception
                {
                    onDrop( event );
                }
            } );

    }

    protected Treeitem getParent( Treeitem child )
    {
        MenuDTO value;
        Treeitem item;

        value = ( MenuDTO )child.getValue();
        if ( value == null )
            return null;
        if ( SysUtils.isZero( value.getParentId() ) )
            return null;
        MenuDTO parent = new MenuDTO( value.getParentId() );
        item = ( Treeitem )getTreeMap().get( parent );
        return item;
    }

    public void render( Listitem item, Object data )
    {
        TaskDTO dto = ( TaskDTO )data;

        item.setValue( dto );
        item.getChildren().add( new Listcell( dto.getDescription() ) );
    }

    public void onClick$cmdTasks()
    {
        Treeitem item = getTreeList().getSelectedItem();

        if ( item != null && item.getValue() != null ) {
            MenuDTO dto = ( MenuDTO )item.getValue();
            Properties params = new Properties();
            params.put( "menuForTask", dto );
            gotoPage( "/private/admin/system/config/menu_task.zul", getRootParent().getParent(), params );
        }
    }

    protected void refreshTask()
    {
        try {
            treeTasks.setModel( new TaskTreeModel( getLoggedInUser(), getLocator().getRootTasks( getLoggedInUser() ) ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Refresh Menu" );
        }
    }
}
