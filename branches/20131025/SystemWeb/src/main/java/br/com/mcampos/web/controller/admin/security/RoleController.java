package br.com.mcampos.web.controller.admin.security;

import java.util.Collections;
import java.util.List;

import org.omg.CORBA.portable.ApplicationException;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.TreeitemRenderer;

import br.com.mcampos.ejb.security.role.RoleSession;
import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.jpa.security.Task;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.controller.admin.security.treenode.RoleNode;
import br.com.mcampos.web.controller.admin.security.treenode.TaskNode;
import br.com.mcampos.web.controller.logged.DynamicMenu;
import br.com.mcampos.web.core.event.IDropEvent;
import br.com.mcampos.web.renderer.RoleTreeItemRenderer;

public class RoleController extends BasicTaskAssociatedTreeController<RoleSession, Role> implements IDropEvent
{
	private static final long serialVersionUID = 2278207931357505346L;

	@Wire( "#recordCode" )
	private Label labelId;

	@Wire( "#recordDescription" )
	private Label labelDescription;

	@Wire( "#editDescription" )
	private Textbox description;

	@Wire( "#editId" )
	private Intbox id;

	@Wire( "#roleMenuView" )
	private Menubar mainMenu;

	private DynamicMenu dynamicMenu = null;

	@Override
	protected Class<RoleSession> getSessionClass( )
	{
		return RoleSession.class;
	}

	@Override
	protected TaskNode createRootTaskNode( )
	{
		return ( TaskNode.createNode( this.getSession( ).getRootTask( ) ) );
	}

	@Override
	protected TreeitemRenderer<?> getTreeRenderer( )
	{
		return new RoleTreeItemRenderer( this );
	}

	@Override
	protected RoleNode getRootNode( )
	{
		return RoleNode.createNode( this.getSession( ).getRootRole( ) );
	}

	@Override
	protected void showRecord( Role data )
	{
		List<Task> tasks;

		if ( data != null ) {
			this.labelId.setValue( data.getId( ).toString( ) );
			this.labelDescription.setValue( data.getDescription( ) );
			tasks = this.getSession( ).getTaks( data );
		}
		else {
			tasks = Collections.emptyList( );
			this.labelId.setValue( "" );
			this.labelDescription.setValue( "" );
		}
		this.showMenus( data );
		this.getListTasks( ).setModel( new ListModelList<Task>( tasks ) );
	}

	private void showMenus( Role data )
	{
		if ( this.mainMenu == null ) {
			return;
		}
		if ( this.mainMenu.getChildren( ) != null ) {
			this.mainMenu.getChildren( ).clear( );
		}
		List<br.com.mcampos.jpa.security.Menu> menus;
		try {
			menus = this.getSession( ).getMenus( data );
			for ( br.com.mcampos.jpa.security.Menu item : menus ) {
				this.getDynamicMenu( ).getParentComponent( item );
			}
		}
		catch ( ApplicationException e ) {
			e = null;
		}
	}

	@Override
	protected boolean validateRecord( )
	{
		if ( SysUtils.isEmpty( this.description.getValue( ) ) ) {
			Messagebox.show( "A descrição deve ser preenchida", "Descrição Inválida", Messagebox.OK, Messagebox.ERROR );
			this.description.setFocus( true );
			return false;
		}
		return true;
	}

	@Override
	public void prepareForInsert( )
	{
		this.getId( ).setRawValue( new Integer( this.getSession( ).getNextId( ) ) );
		this.getId( ).setReadonly( false );
		this.getDescription( ).setFocus( true );
		this.getDescription( ).setRawValue( "" );
	}

	@Override
	public void prepareForUpdate( Role data )
	{
		this.getId( ).setRawValue( data.getId( ) );
		this.getId( ).setReadonly( true );
		this.getDescription( ).setRawValue( data.getDescription( ) );
		this.getDescription( ).setFocus( true );
	}

	private Textbox getDescription( )
	{
		return this.description;
	}

	private Intbox getId( )
	{
		return this.id;
	}

	@Override
	protected RoleNode insert( )
	{
		Role role = new Role( this.getId( ).getValue( ), this.getDescription( ).getValue( ) );
		role.setParent( this.getSession( ).getRootRole( ) );
		this.getSession( ).add( this.getPrincipal( ), role );
		return RoleNode.createNode( role );
	}

	@Override
	protected void deleteTask( RoleSession session, Role entity, Task task )
	{
		session.remove( this.getPrincipal( ), entity, task );
	}

	@Override
	protected void addTask( RoleSession session, Role entity, Task task )
	{
		session.add( this.getPrincipal( ), entity, task );
	}

	@Override
	protected void update( RoleSession session, Role data )
	{
		data.setDescription( this.getDescription( ).getValue( ) );
		this.getSession( ).update( this.getPrincipal( ), data );
	}

	@Override
	protected void remove( RoleSession session, Role data )
	{
		if ( data != null ) {
			session.remove( this.getPrincipal( ), data.getId( ) );
		}
	}

	@Override
	protected void changeParent( RoleSession session, Role source, Role target )
	{
		this.getSession( ).changeParent( this.getPrincipal( ), source, target );
	}

	protected DynamicMenu getDynamicMenu( )
	{
		if ( this.dynamicMenu == null ) {
			this.dynamicMenu = new DynamicMenu( this.mainMenu, null );
		}
		return this.dynamicMenu;
	}
}
