package br.com.mcampos.web.controller.admin.security;

import java.util.Collections;
import java.util.List;

import org.omg.CORBA.portable.ApplicationException;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.security.task.TaskSession;
import br.com.mcampos.entity.security.Menu;
import br.com.mcampos.entity.security.Role;
import br.com.mcampos.entity.security.Task;
import br.com.mcampos.web.controller.admin.security.treenode.BaseTreeNode;
import br.com.mcampos.web.controller.admin.security.treenode.MenuNode;
import br.com.mcampos.web.controller.admin.security.treenode.RoleNode;
import br.com.mcampos.web.controller.admin.security.treenode.TaskNode;
import br.com.mcampos.web.core.event.IDropEvent;
import br.com.mcampos.web.core.tree.AdvancedTreeModel;
import br.com.mcampos.web.core.tree.BaseTreeController;
import br.com.mcampos.web.renderer.MenuListItemRenderer;
import br.com.mcampos.web.renderer.MenuTreeItemRenderer;
import br.com.mcampos.web.renderer.RoleListItemRenderer;
import br.com.mcampos.web.renderer.RoleTreeItemRenderer;
import br.com.mcampos.web.renderer.TaskTreeItemRenderer;

public class TaskController extends BaseTreeController<TaskSession, Task> implements IDropEvent
{
	private static final long serialVersionUID = 6601791720884472082L;

	@Wire
	Label recordDescription;

	@Wire
	Label recordId;

	@Wire
	Textbox editDescription;

	@Wire
	Intbox editId;

	@Wire
	Listbox listMenu;

	@Wire
	Listbox listRole;

	@Wire
	private Tree treeRoles;

	@Wire
	private Tree treeMenus;

	@Override
	protected Class<TaskSession> getSessionClass( )
	{
		return TaskSession.class;
	}

	@Override
	protected TreeitemRenderer<?> getTreeRenderer( )
	{
		return new TaskTreeItemRenderer( this );
	}

	@Override
	public BaseTreeNode<Task> getRootNode( )
	{
		return TaskNode.createNode( getSession( ).getRootTask( ) );
	}

	@Override
	protected void showRecord( Task node )
	{
		List<Role> roles;
		List<Menu> menus;

		if ( node != null ) {
			recordId.setValue( node.getId( ).toString( ) );
			recordDescription.setValue( node.getDescription( ) );
			roles = getSession( ).getRoles( node.getId( ) );
			menus = getSession( ).getMenus( node.getId( ) );
		}
		else {
			roles = Collections.emptyList( );
			menus = Collections.emptyList( );
			recordId.setValue( "" );
			recordDescription.setValue( "" );
		}
		listMenu.setModel( new ListModelList<Menu>( menus ) );
		listRole.setModel( new ListModelList<Role>( roles ) );
	}

	@Override
	protected void prepareForInsert( )
	{
		editId.setRawValue( new Integer( getSession( ).getNextId( ) ) );
		editId.setReadonly( false );
		editDescription.setFocus( true );
		editDescription.setRawValue( "" );

	}

	@Override
	protected void prepareForUpdate( Task data )
	{
		editId.setRawValue( data.getId( ) );
		editId.setReadonly( true );
		editDescription.setRawValue( data.getDescription( ) );
		editDescription.setFocus( true );
	}

	@Override
	protected boolean validateRecord( )
	{
		if ( editDescription.isValid( ) == false ) {
			return false;
		}
		return true;
	}

	@Override
	protected TaskNode insert( )
	{
		Task task = new Task( );
		update( getSession( ), task );
		return TaskNode.createNode( task );
	}

	@Override
	protected void update( TaskSession session, Task data )
	{
		data.setId( editId.getValue( ) );
		data.setDescription( editDescription.getValue( ) );
		getSession( ).merge( data );
		session.merge( data );
	}

	@Override
	protected void remove( TaskSession session, Task data )
	{
		session.remove( data );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		listMenu.setItemRenderer( new MenuListItemRenderer( "listPopupRemoveItem" ) );
		listRole.setItemRenderer( new RoleListItemRenderer( "listPopupRemoveItem" ) );
		treeMenus.setItemRenderer( new MenuTreeItemRenderer( this ) );
		treeRoles.setItemRenderer( new RoleTreeItemRenderer( this ) );
		treeRoles.setModel( new AdvancedTreeModel<Role>( getRootRoleNode( ) ) );
		treeMenus.setModel( new AdvancedTreeModel<Menu>( getRootMenuNode( ) ) );
	}

	private RoleNode getRootRoleNode( )
	{
		return RoleNode.createNode( getSession( ).getRootRole( ) );
	}

	protected MenuNode getRootMenuNode( )
	{
		Menu rootMenu = new Menu( );
		try {
			rootMenu.setChilds( getSession( ).getTopContextMenu( ) );
		}
		catch ( ApplicationException e ) {
			e.printStackTrace( );
		}
		return MenuNode.createNode( rootMenu );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public void onDrop( DropEvent evt )
	{
		if ( evt != null )
		{
			Treerow source = (Treerow) evt.getDragged( );
			Treerow target = (Treerow) evt.getTarget( );
			Object vSource = ( (Treeitem) source.getParent( ) ).getValue( );
			TaskNode vTarget = ( (Treeitem) target.getParent( ) ).getValue( );
			if ( vSource instanceof MenuNode || vSource instanceof RoleNode )
			{
				BaseTreeNode<?> t;
				t = (BaseTreeNode<?>) vSource;
				if ( t != null ) {
					try {
						if ( t instanceof MenuNode )
						{
							getSession( ).add( vTarget.getData( ), (Menu) t.getData( ) );
							ListModelList<Menu> model = (ListModelList<Menu>) (Object) listMenu.getModel( );
							if ( model.contains( t.getData( ) ) == false ) {
								model.add( (Menu) t.getData( ) );
							}
						}
						else
						{
							getSession( ).add( vTarget.getData( ), (Role) t.getData( ) );
							ListModelList<Role> model = (ListModelList<Role>) (Object) listRole.getModel( );
							if ( model.contains( t.getData( ) ) == false ) {
								model.add( (Role) t.getData( ) );
							}
						}
					}
					catch ( Exception e )
					{
						showErrorMessage( "Erro ao adicionar item à tarefa", "Adicionar" );
					}

				}
			}
			else {
				super.onDrop( evt );
			}
		}
	}

	@Override
	protected void changeParent( TaskSession session, Task source, Task target )
	{
		session.changeParent( source, target );
	}
}
