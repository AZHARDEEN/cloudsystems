package br.com.mcampos.controller.admin.security.task;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import br.com.mcampos.controller.admin.security.menu.MenuListRenderer;
import br.com.mcampos.controller.admin.security.menu.MenuTreeModel;
import br.com.mcampos.controller.admin.security.menu.MenuTreeRenderer;
import br.com.mcampos.controller.admin.security.roles.RoleListRenderer;
import br.com.mcampos.controller.admin.security.roles.RoleModel;
import br.com.mcampos.controller.admin.security.roles.RoleRenderer;
import br.com.mcampos.controller.core.BasicTreeCRUDController;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskFacade;
import br.com.mcampos.util.system.IDropEvent;

public class TaskController extends BasicTreeCRUDController<TaskDTO> implements IDropEvent
{
	private TaskFacade locator;

	private Label recordId;
	private Label recordDescription;
	private Label recordParent;

	/*Labels*/
	private Button removeMenu;
	private Button removeRole;
	private Label labelTaskTitle;
	private Label labelCode;
	private Label labelDescription;
	private Label labelParent;
	private Label labelEditCode;
	private Label labelEditDescription;
	private Label labelEditParent;
	private Listheader listHeaderMenuId;
	private Listheader listHeaderMenuDesc;
	private Listheader listHeaderMenuUrl;
	private Listheader listHeaderMenuParent;
	private Listheader listHeaderRoleId;
	private Listheader listHeaderRoleDesc;
	private Listheader listHeaderRoleParent;

	private Tab tabData;
	private Tab tabFreeRole;
	private Tab tabFreeMenus;
	private Treecol treecolTask;
	private Treecol treecolMenu;
	private Treecol treecolRole;

	private Intbox editId;
	private Textbox editDescription;
	private Intbox editParent;

	private Listbox listMenu;
	private Listbox listRole;
	private Tree treeMenu;
	private Tree treeRole;

	public TaskController( char c )
	{
		super( c );
	}

	public TaskController( )
	{
		super( );
	}

	@Override
	protected void showRecord( TaskDTO record ) throws ApplicationException
	{
		recordId.setValue( record.getId( ).toString( ) );
		recordDescription.setValue( record.getDescription( ) );
		if ( record.getParent( ) != null )
			recordParent.setValue( record.getParent( ).toString( ) );
		else
			recordParent.setValue( "" );
		showTaskMenu( record );
		showTaskRole( record );
	}

	protected void showTaskMenu( TaskDTO task ) throws ApplicationException
	{
		if ( task == null )
			return;
		System.out.println( "ShowTaskMenu: " + task.toString( ) );
		List<MenuDTO> menus = getLocator( ).getMenus( getLoggedInUser( ), task );
		listMenu.setModel( new ListModelList( menus, true ) );
		System.out.println( "ShowTaskMenu is done!!! " + task.toString( ) );
	}

	protected void showTaskRole( TaskDTO task ) throws ApplicationException
	{
		if ( task == null )
			return;
		System.out.println( "ShowTaskRole: " + task.toString( ) );
		List<RoleDTO> roles = getLocator( ).getRoles( getLoggedInUser( ), task );
		listRole.setModel( new ListModelList( roles, true ) );
		System.out.println( "ShowTaskRole is done!!! " + task.toString( ) );
	}

	@Override
	protected TaskDTO copyTo( TaskDTO dto )
	{
		dto.setId( editId.getValue( ) );
		dto.setDescription( editDescription.getValue( ) );
		if ( editParent.getValue( ) != 0 )
			try {
				dto.setParent( getLocator( ).getTask( getLoggedInUser( ), editParent.getValue( ) ) );
			}
			catch ( ApplicationException e ) {
				showErrorMessage( e.getMessage( ), "Obter Tarefa Associada" );
			}
		return dto;
	}

	@Override
	protected TaskDTO createDTO( )
	{
		return new TaskDTO( );
	}

	@Override
	protected void configureTreeitem( Treeitem item )
	{
		TaskDTO data = getValue( item );
		Treerow row;

		row = item.getTreerow( );
		row.appendChild( new Treecell( data.toString( ) ) );
		row.setDraggable( "true" );
		row.setDroppable( "true" );
		row.addEventListener( Events.ON_DROP, new EventListener( )
		{
			@Override
			public void onEvent( Event event ) throws Exception
			{
				onDrop( event );
			}
		} );
	}

	@Override
	protected Treeitem getParent( Treeitem newChild )
	{
		return null;
	}

	@Override
	protected void refresh( )
	{
		try {
			getTreeList( ).setModel( new TaskTreeModel( getLoggedInUser( ), getLocator( ).getRootTasks( getLoggedInUser( ) ) ) );
			treeMenu.setModel( new MenuTreeModel( getLocator( ).getParentMenus( getLoggedInUser( ) ) ) );
			treeRole.setModel( new RoleModel( getLoggedInUser( ), getLocator( ).getRootRole( getLoggedInUser( ) ) ) );
		}
		catch ( ApplicationException e ) {
			showErrorMessage( e.getMessage( ), "Refresh Menu" );
		}
	}

	@Override
	protected void delete( Object currentRecord )
	{
		if ( currentRecord == null )
			return;

		TaskDTO dto = getValue( (Treeitem) currentRecord );
		if ( dto != null )
			try {
				getLocator( ).delete( getLoggedInUser( ), dto );
			}
			catch ( ApplicationException e ) {
				showErrorMessage( e.getMessage( ), "Exclur Task" );
			}
	}

	@Override
	protected void prepareToInsert( )
	{
		editId.setRawValue( getNextId( ) );
		editId.setReadonly( false );
		editDescription.setFocus( true );
		editDescription.setValue( "" );
		editParent.setRawValue( 0 );
	}

	protected int getNextId( )
	{
		try {
			int nextVal = getLocator( ).getNextTaskId( getLoggedInUser( ) );
			return nextVal;
		}
		catch ( ApplicationException e ) {
			e = null;
			return 0;
		}

	}

	@Override
	protected TaskDTO prepareToUpdate( Object currentRecord )
	{
		TaskDTO dto = null;

		dto = getValue( (Treeitem) currentRecord );
		if ( dto != null ) {
			editId.setValue( dto.getId( ) );
			editId.setReadonly( true );
			editDescription.setValue( dto.getDescription( ) );
			editDescription.setFocus( true );
			editParent.setValue( dto.getParent( ) == null ? 0 : dto.getParent( ).getId( ) );
		}
		return dto;
	}

	@Override
	protected void persist( Object e )
	{
		try {
			TaskDTO dto = getLocator( ).add( getLoggedInUser( ), getValue( (Treeitem) e ) );
			if ( dto != null )
				( (Treeitem) e ).setValue( dto );
		}
		catch ( ApplicationException ex ) {
			showErrorMessage( ex.getMessage( ), "Inserir" );
		}
	}

	protected void updateItem( Object e )
	{
		try {
			TaskDTO dto = getLocator( ).update( getLoggedInUser( ), getValue( ( (Treeitem) e ) ) );
			if ( dto != null )
				( (Treeitem) e ).setValue( dto );
		}
		catch ( ApplicationException ex ) {
			showErrorMessage( ex.getMessage( ), "Atualizar" );
		}
	}

	protected TaskFacade getLocator( )
	{
		if ( locator == null )
			locator = (TaskFacade) getRemoteSession( TaskFacade.class );
		return locator;
	}

	public void onDrop( Event evt )
	{

	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setLabel( );
		getTreeList( ).setItemRenderer( new TaskTreeRenderer( this, true, true ) );
		listMenu.setItemRenderer( new MenuListRenderer( ) );
		listRole.setItemRenderer( new RoleListRenderer( ) );
		treeRole.setItemRenderer( new RoleRenderer( null, true, false ) );
		treeMenu.setItemRenderer( new MenuTreeRenderer( null, true, false ) );
		refresh( );
	}

	@Override
	public void onDrop( org.zkoss.zk.ui.event.DropEvent evt )
	{
		Object source = evt.getDragged( );
		if ( getTreeList( ).getSelectedItem( ) == null )
			return;
		TaskDTO task = (TaskDTO) getTreeList( ).getSelectedItem( ).getValue( );
		if ( source instanceof Treerow ) {
			Treerow row = (Treerow) source;
			Treeitem item = (Treeitem) row.getParent( );
			Object value = item.getValue( );
			try {
				if ( value instanceof MenuDTO ) {
					MenuDTO menu = (MenuDTO) value;
					getLocator( ).add( getLoggedInUser( ), task, menu );
					ListModelList model = (ListModelList) listMenu.getModel( );
					model.add( menu );
				}
				else if ( value instanceof RoleDTO ) {
					RoleDTO role = (RoleDTO) value;
					getLocator( ).add( getLoggedInUser( ), task, role );
					ListModelList model = (ListModelList) listRole.getModel( );
					model.add( role );
				}
			}
			catch ( ApplicationException e ) {
				showErrorMessage( e.getMessage( ), "Associar" );
			}
		}
	}

	private void setLabel( )
	{
		setLabel( removeMenu );
		setLabel( removeRole );
		setLabel( labelTaskTitle );
		setLabel( labelCode );
		setLabel( labelDescription );
		setLabel( labelParent );
		setLabel( labelEditCode );
		setLabel( labelEditDescription );
		setLabel( labelEditParent );
		setLabel( listHeaderMenuId );
		setLabel( listHeaderMenuDesc );
		setLabel( listHeaderMenuUrl );
		setLabel( listHeaderMenuParent );
		setLabel( listHeaderRoleId );
		setLabel( listHeaderRoleDesc );
		setLabel( listHeaderRoleParent );

		setLabel( tabData );
		setLabel( tabFreeRole );
		setLabel( tabFreeMenus );
		setLabel( treecolTask );
		setLabel( treecolMenu );
		setLabel( treecolRole );

	}

	@Override
	public void render( Treeitem arg0, Object arg1, int arg2 ) throws Exception
	{
		// TODO Auto-generated method stub

	}
}
