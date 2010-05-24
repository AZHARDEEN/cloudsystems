package br.com.mcampos.controller.admin.system.config.menu;


import br.com.mcampos.controller.admin.system.config.task.TaskListRenderer;
import br.com.mcampos.controller.admin.system.config.task.TaskTreeModel;
import br.com.mcampos.controller.admin.system.config.task.TaskTreeRenderer;
import br.com.mcampos.controller.core.BasicTreeCRUDController;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.List;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
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
    protected Button removeTask;

    protected Tree treeTasks;
    protected Listbox listTasks;


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
        getRecordEdit().addEventListener( Events.ON_DROP, new EventListener()
            {
                public void onEvent( Event event ) throws Exception
                {
                    onDrop( event );
                }
            } );
        getTreeList().setTreeitemRenderer( this );
        treeTasks.setTreeitemRenderer( new TaskTreeRenderer( null, true, false ) );
        listTasks.setItemRenderer( new TaskListRenderer() );
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
            getTreeList().setModel( new MenuTreeModel( getLocator().getParentMenus( getLoggedInUser() ) ) );

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
        showMenuTasks( dto );
    }

    protected void showMenuTasks( MenuDTO dto ) throws ApplicationException
    {
        System.out.println( "ShowMenuTask: " + dto.toString() );
        List<TaskDTO> tasks = getLocator().getMenuTasks( getLoggedInUser(), dto.getId() );
        System.out.println( "ShowMenuTask list was gotten: " + dto.toString() );
        if ( SysUtils.isEmpty( tasks ) )
            tasks = new ArrayList<TaskDTO>();
        listTasks.setModel( new ListModelList( tasks, true ) );
        System.out.println( "ShowMenuTask model created: " + dto.toString() );
        removeTask.setDisabled( true );
        System.out.println( "ShowMenuTask is done!!!: " + dto.toString() );

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
            MenuDTO fromDTO = ( MenuDTO )dto;
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
        else if ( dto instanceof TaskDTO ) {
            TaskDTO task = ( TaskDTO )dto;
            if ( de.getTarget() instanceof Treerow ) {
                MenuDTO toDTO = getValue( ( Treeitem )( ( Treerow )de.getTarget() ).getParent() );
                try {
                    getLocator().add( getLoggedInUser(), task, toDTO );
                    ListModelList model = ( ListModelList )listTasks.getModel();
                    model.add( task );
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
                getLocator().delete( getLoggedInUser(), dto.getId() );
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

    protected void refreshTask()
    {
        try {
            treeTasks.setModel( new TaskTreeModel( getLoggedInUser(), getLocator().getRootTasks( getLoggedInUser() ) ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Refresh Menu" );
        }
    }

    public void onSelect$listTasks()
    {
        removeTask.setDisabled( false );
    }

    public void onClick$removeTask()
    {
        Listitem item = listTasks.getSelectedItem();
        MenuDTO menu = null;
        if ( getTreeList().getSelectedItem() != null )
            menu = ( MenuDTO )getTreeList().getSelectedItem().getValue();
        if ( item != null && menu != null ) {
            try {
                TaskDTO task = ( TaskDTO )item.getValue();
                getLocator().remove( getLoggedInUser(), task, menu );
                if ( task != null ) {
                    ListModelList model = ( ListModelList )listTasks.getModel();
                    if ( model != null )
                        model.remove( task );
                    removeTask.setDisabled( true );
                }
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Remover Tarefa do Menu" );
            }
        }
    }
}
