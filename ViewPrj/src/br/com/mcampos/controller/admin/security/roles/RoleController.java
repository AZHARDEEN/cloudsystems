package br.com.mcampos.controller.admin.security.roles;


import br.com.mcampos.controller.admin.security.SecutityBaseController;
import br.com.mcampos.controller.admin.system.config.task.TaskListRenderer;
import br.com.mcampos.controller.admin.system.config.task.TaskTreeModel;
import br.com.mcampos.controller.admin.system.config.task.TaskTreeRenderer;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.system.IDropEvent;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;


public class RoleController extends SecutityBaseController implements IDropEvent
{

    private Label labelId;
    private Label labelDescription;
    private Label labelParent;

    private Intbox editId;
    private Textbox editDescription;
    private Combobox comboParent;

    private Tree treeTasks;
    private Listbox listTasks;

    protected Menubar roleMenuView;

    protected Toolbarbutton removeTask;

    public RoleController( char c )
    {
        super( c );
    }

    public RoleController()
    {
        super();
    }

    protected void refresh ()
    {
        try {
            getTree().setModel( new RoleModel ( getLoggedInUser(), getSession().getRootRole( getLoggedInUser() ) ) );
            comboParent.setItemRenderer(  new RoleComboRenderer() );
            comboParent.setModel( new ListModelList ( getSession().getRoles( getLoggedInUser() ) ) );
            treeTasks.setModel( new TaskTreeModel( getLoggedInUser(), getSession().getRootTasks( getLoggedInUser() ) ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Role" );
        }
    }

    protected void showRecord( Object record )
    {
        RoleDTO dto = ( RoleDTO )record;

        labelDescription.setValue(  dto.getDescription() );
        labelId.setValue( dto.getId().toString( ) );
        labelParent.setValue( dto.getParent() != null ? dto.getParent().toString() : "" );
        removeTask.setDisabled( true );
        try {
            loadTasks ( dto );
            showMenu ( dto );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "LoadTasks" );
        }
    }

    protected void loadTasks ( RoleDTO dto ) throws ApplicationException
    {
        List<TaskDTO> tasks = getSession().getTasks ( getLoggedInUser(), dto );
        if ( SysUtils.isEmpty( tasks ) )
            tasks = new ArrayList<TaskDTO> ();
        ListModelList model = new ListModelList (tasks, true);
        listTasks.setModel( model );
    }

    protected void clearRecord()
    {
        labelDescription.setValue(  "" );
        labelId.setValue( "" );
        labelParent.setValue( "" );
        editId.setRawValue( new Integer (0) );
        editDescription.setValue( "" );
        updateComboParent( null );
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete ( getLoggedInUser(), ((RoleDTO) currentRecord));
    }

    protected void afterDelete( Object currentRecord )
    {
        ListModelList model = ( ListModelList ) comboParent.getModel();
        RoleModel treeModel = (RoleModel)getTree().getModel();

        model.remove( currentRecord );
        treeModel.delete( ((RoleDTO) currentRecord) );
    }

    protected void afterPersist( Object currentRecord )
    {
        ListModelList model = ( ListModelList ) comboParent.getModel();
        RoleModel treeModel = (RoleModel)getTree().getModel();
        if ( isAddNewOperation() ) {
            model.add( currentRecord );
            treeModel.add( (RoleDTO) currentRecord );
        }
        else {
            int nIndex = model.indexOf( currentRecord );
            model.set( nIndex, currentRecord );
            treeModel.update( (RoleDTO) currentRecord );
        }
    }

    protected Object saveRecord( Object currentRecord )
    {
        RoleDTO dto = ( RoleDTO )currentRecord;
        dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue() );
        dto.setParent( ( RoleDTO )comboParent.getSelectedItem().getValue() );
        return currentRecord;
    }

    protected void prepareToInsert()
    {
        clearRecord();
        editId.setReadonly( false );
        editId.setDisabled( false );
        try {
            editId.setValue( getSession().getRoleMaxId( getLoggedInUser() ) );
            editDescription.setFocus( true );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Role" );
            editId.setValue( 1 );
        }
    }

    protected Object prepareToUpdate( Object currentRecord )
    {
        RoleDTO dto = ( RoleDTO )currentRecord;

        editId.setValue( dto.getId() );
        editId.setReadonly( true );
        editDescription.setValue( dto.getDescription() );
        editDescription.setFocus( true );
        updateComboParent( dto.getParent() );
        return currentRecord;
    }

    protected void updateComboParent ( RoleDTO parent )
    {
        if ( parent == null )
            parent = new RoleDTO ( 1 );
        ListModelList model = ( ListModelList ) comboParent.getModel();
        int nIndex = model.indexOf( parent );
        comboParent.setSelectedIndex( nIndex );
    }

    protected Object createNewRecord()
    {
        return new RoleDTO ();
    }

    protected void persist( Object obj ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), (RoleDTO) obj );
        else
            getSession().update( getLoggedInUser(), (RoleDTO) obj );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        listTasks.setItemRenderer( new TaskListRenderer() );
        treeTasks.setTreeitemRenderer( new TaskTreeRenderer ( null, true, false) );
        getTree().setTreeitemRenderer( new RoleRenderer( this, true, true) );
    }

    public void onDrop( DropEvent evt )
    {
        Treerow source = (Treerow) evt.getDragged();
        Treerow target = (Treerow) evt.getTarget();
        Object vSource = ((Treeitem)source.getParent()).getValue();
        Object vTarget = ((Treeitem)target.getParent()).getValue();
        if ( vSource instanceof TaskDTO ) {
            if ( vSource != null && vTarget != null ) {
                TaskDTO dSource = (TaskDTO)vSource;
                RoleDTO dTarget = ( RoleDTO)vTarget;
                try {
                    getSession().add( getLoggedInUser(), dSource, dTarget );
                    ListModelList model = ( ListModelList ) listTasks.getModel();
                    if ( model != null )
                        model.add( dSource );
                }
                catch ( ApplicationException e ) {
                    showErrorMessage( e.getMessage(), "Permission Assigment" );
                }
            }
        }
    }

    protected void showMenu ( RoleDTO role )
    {
        List<MenuDTO> menus;
        try {
            menus = getSession().getMenus ( getLoggedInUser(), role );
            if ( roleMenuView.getChildren() != null )
                roleMenuView.getChildren().clear();
            for ( MenuDTO item : menus ) {
                addMenu( item, roleMenuView );
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Montar Menu" );
        }
    }

    protected Component addMenu( MenuDTO item, Component parent )
    {

        if ( item.getSubMenu().size() > 0 ) {
            Menu menuItem;
            Menupopup menuBar;

            menuItem = new Menu( item.getDescription() );
            menuBar = new Menupopup();
            menuBar.setParent( menuItem );
            menuItem.appendChild( menuBar );
            menuItem.setParent( parent );
            parent.appendChild( menuItem );
            for ( MenuDTO submenu : item.getSubMenu() ) {
                addMenu( submenu, menuBar );
            }
            return menuBar;
        }
        else {
            Menuitem menuItem;

            menuItem = new Menuitem( item.getDescription() );
            if ( item.getSeparatorBefore() )
                parent.appendChild( new Menuseparator() );
            parent.appendChild( menuItem );
            menuItem.setParent( parent );
            menuItem.setAutocheck( item.getAutocheck() );
            menuItem.setChecked( item.getChecked() );
            menuItem.setCheckmark( item.getCheckmark() );
            menuItem.setDisabled( item.getDisabled() );
            return parent;
        }
    }

    public void onSelect$listTasks ()
    {
        removeTask.setDisabled( false );
    }

    public void onClick$removeTask ()
    {
        removeTask.setDisabled( true );
        if ( listTasks.getSelectedItem() != null ) {
            Object task = listTasks.getSelectedItem().getValue();
            if ( task != null && getTree().getSelectedItem() != null ) {
                RoleDTO role = ( RoleDTO ) getTree().getSelectedItem().getValue();
                try {
                    getSession().remove( getLoggedInUser(), ((TaskDTO)task), role);
                    ListModelList model = (ListModelList ) listTasks.getModel();
                    model.remove( task );
                }
                catch ( ApplicationException e ) {
                    showErrorMessage( e.getMessage(), "Excluir Tarefa" );
                }
            }
        }
    }
}