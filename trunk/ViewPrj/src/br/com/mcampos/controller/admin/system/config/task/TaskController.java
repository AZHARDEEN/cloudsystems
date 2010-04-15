package br.com.mcampos.controller.admin.system.config.task;


import br.com.mcampos.controller.admin.security.roles.RoleListRenderer;
import br.com.mcampos.controller.admin.system.config.menu.MenuListRenderer;
import br.com.mcampos.controller.core.BasicTreeCRUDController;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;


public class TaskController extends BasicTreeCRUDController<TaskDTO>
{
    protected TaskLocator locator;

    Label recordId;
    Label recordDescription;
    Label recordParent;


    Intbox editId;
    Textbox editDescription;
    Intbox editParent;

    protected Listbox listMenu;
    protected Listbox listRole;

    public TaskController( char c )
    {
        super( c );
    }

    public TaskController()
    {
        super();
    }

    protected void showRecord( TaskDTO record ) throws ApplicationException
    {
        recordId.setValue( record.getId().toString() );
        recordDescription.setValue( record.getDescription() );
        if ( record.getParent() != null )
            recordParent.setValue( record.getParent().toString() );
        else
            recordParent.setValue( "" );
        showTaskMenu( record );
        showTaskRole( record );
    }

    protected void showTaskMenu( TaskDTO task ) throws ApplicationException
    {
        if ( task == null )
            return;
        System.out.println( "ShowTaskMenu: " + task.toString() );
        List<MenuDTO> menus = getLocator().getMenus( getLoggedInUser(), task );
        listMenu.setModel( new ListModelList( menus, true ) );
        System.out.println( "ShowTaskMenu is done!!! " + task.toString() );
    }

    protected void showTaskRole( TaskDTO task ) throws ApplicationException
    {
        if ( task == null )
            return;
        System.out.println( "ShowTaskRole: " + task.toString() );
        List<RoleDTO> roles = getLocator().getRoles( getLoggedInUser(), task );
        listRole.setModel( new ListModelList( roles, true ) );
        System.out.println( "ShowTaskRole is done!!! " + task.toString() );
    }

    protected TaskDTO copyTo( TaskDTO dto )
    {
        dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue() );
        if ( editParent.getValue() != 0 ) {
            try {
                dto.setParent( getLocator().getTask( getLoggedInUser(), editParent.getValue() ) );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Obter Tarefa Associada" );
            }
        }
        return dto;
    }

    protected TaskDTO createDTO()
    {
        return new TaskDTO();
    }

    protected void configureTreeitem( Treeitem item )
    {
        TaskDTO data = getValue( item );
        Treerow row;

        row = item.getTreerow();
        row.appendChild( new Treecell( data.toString() ) );
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

    protected Treeitem getParent( Treeitem newChild )
    {
        return null;
    }

    protected void refresh()
    {
        try {
            getTreeList().setModel( new TaskTreeModel( getLoggedInUser(), getLocator().getRootTasks( getLoggedInUser() ) ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Refresh Menu" );
        }
    }

    protected void delete( Object currentRecord )
    {
        if ( currentRecord == null )
            return;

        TaskDTO dto = getValue( ( Treeitem )currentRecord );
        if ( dto != null ) {
            try {
                getLocator().delete( getLoggedInUser(), dto );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage(), "Exclur Task" );
            }
        }
    }

    protected void prepareToInsert()
    {
        editId.setRawValue( getNextId() );
        editId.setReadonly( false );
        editDescription.setFocus( true );
        editDescription.setValue( "" );
        editParent.setRawValue( 0 );
    }

    protected int getNextId()
    {
        try {
            int nextVal = getLocator().getNextTaskId( getLoggedInUser() );
            return nextVal;
        }
        catch ( ApplicationException e ) {
            e = null;
            return 0;
        }

    }


    protected TaskDTO prepareToUpdate( Object currentRecord )
    {
        TaskDTO dto = null;

        dto = getValue( ( Treeitem )currentRecord );
        if ( dto != null ) {
            editId.setValue( dto.getId() );
            editId.setReadonly( true );
            editDescription.setValue( dto.getDescription() );
            editDescription.setFocus( true );
            editParent.setValue( dto.getParent() == null ? 0 : dto.getParent().getId() );
        }
        return dto;
    }

    protected void persist( Object e )
    {
        try {
            TaskDTO dto = getLocator().add( getLoggedInUser(), getValue( ( Treeitem )e ) );
            if ( dto != null )
                ( ( Treeitem )e ).setValue( dto );
        }
        catch ( ApplicationException ex ) {
            showErrorMessage( ex.getMessage(), "Inserir" );
        }
    }

    protected void updateItem( Object e )
    {
        try {
            TaskDTO dto = getLocator().update( getLoggedInUser(), getValue( ( ( Treeitem )e ) ) );
            if ( dto != null )
                ( ( Treeitem )e ).setValue( dto );
        }
        catch ( ApplicationException ex ) {
            showErrorMessage( ex.getMessage(), "Atualizar" );
        }
    }

    protected TaskLocator getLocator()
    {
        if ( locator == null )
            locator = new TaskLocator();
        return locator;
    }


    public void onDrop( Event evt )
    {

    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        getTreeList().setTreeitemRenderer( this );
        listMenu.setItemRenderer( new MenuListRenderer() );
        listRole.setItemRenderer( new RoleListRenderer() );
        refresh();
    }
}
