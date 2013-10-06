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
import br.com.mcampos.jpa.security.Menu;
import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.jpa.security.Task;
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
		return TaskNode.createNode( this.getSession( ).getRootTask( ) );
	}

	@Override
	protected void showRecord( Task node )
	{
		List<Role> roles;
		List<Menu> menus;

		if ( node != null ) {
			this.recordId.setValue( node.getId( ).toString( ) );
			this.recordDescription.setValue( node.getDescription( ) );
			roles = this.getSession( ).getRoles( node.getId( ) );
			menus = this.getSession( ).getMenus( node.getId( ) );
		}
		else {
			roles = Collections.emptyList( );
			menus = Collections.emptyList( );
			this.recordId.setValue( "" );
			this.recordDescription.setValue( "" );
		}
		this.listMenu.setModel( new ListModelList<Menu>( menus ) );
		this.listRole.setModel( new ListModelList<Role>( roles ) );
	}

	@Override
	protected void prepareForInsert( )
	{
		this.editId.setRawValue( new Integer( this.getSession( ).getNextId( ) ) );
		this.editId.setReadonly( false );
		this.editDescription.setFocus( true );
		this.editDescription.setRawValue( "" );

	}

	@Override
	protected void prepareForUpdate( Task data )
	{
		this.editId.setRawValue( data.getId( ) );
		this.editId.setReadonly( true );
		this.editDescription.setRawValue( data.getDescription( ) );
		this.editDescription.setFocus( true );
	}

	@Override
	protected boolean validateRecord( )
	{
		if ( this.editDescription.isValid( ) == false ) {
			return false;
		}
		return true;
	}

	@Override
	protected TaskNode insert( )
	{
		Task task = new Task( );
		this.update( task );
		task = this.getSession( ).add( this.getPrincipal( ), task );
		return TaskNode.createNode( task );
	}

	private void update( Task data )
	{
		data.setId( this.editId.getValue( ) );
		data.setDescription( this.editDescription.getValue( ) );
	}

	@Override
	protected void update( TaskSession session, Task data )
	{
		this.update( data );
		this.getSession( ).update( this.getPrincipal( ), data );
	}

	@Override
	protected void remove( TaskSession session, Task data )
	{
		session.remove( this.getPrincipal( ), data.getId( ) );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.listMenu.setItemRenderer( new MenuListItemRenderer( "listPopupRemoveItem" ) );
		this.listRole.setItemRenderer( new RoleListItemRenderer( "listPopupRemoveItem" ) );
		this.treeMenus.setItemRenderer( new MenuTreeItemRenderer( this ) );
		this.treeRoles.setItemRenderer( new RoleTreeItemRenderer( this ) );
		this.treeRoles.setModel( new AdvancedTreeModel<Role>( this.getRootRoleNode( ) ) );
		this.treeMenus.setModel( new AdvancedTreeModel<Menu>( this.getRootMenuNode( ) ) );
	}

	private RoleNode getRootRoleNode( )
	{
		return RoleNode.createNode( this.getSession( ).getRootRole( ) );
	}

	protected MenuNode getRootMenuNode( )
	{
		Menu rootMenu = new Menu( );
		try {
			rootMenu.setChilds( this.getSession( ).getTopContextMenu( ) );
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
							this.getSession( ).add( this.getPrincipal( ), vTarget.getData( ), (Menu) t.getData( ) );
							ListModelList<Menu> model = (ListModelList<Menu>) (Object) this.listMenu.getModel( );
							if ( model.contains( t.getData( ) ) == false ) {
								model.add( (Menu) t.getData( ) );
							}
						}
						else
						{
							this.getSession( ).add( this.getPrincipal( ), vTarget.getData( ), (Role) t.getData( ) );
							ListModelList<Role> model = (ListModelList<Role>) (Object) this.listRole.getModel( );
							if ( model.contains( t.getData( ) ) == false ) {
								model.add( (Role) t.getData( ) );
							}
						}
					}
					catch ( Exception e )
					{
						this.showErrorMessage( "Erro ao adicionar item à tarefa", "Adicionar" );
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
		session.changeParent( this.getPrincipal( ), source, target );
	}
}
