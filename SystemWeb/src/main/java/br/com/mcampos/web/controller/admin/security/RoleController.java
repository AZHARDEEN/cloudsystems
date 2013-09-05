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
import br.com.mcampos.entity.security.Role;
import br.com.mcampos.entity.security.Task;
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
		return(TaskNode.createNode( getSession( ).getRootTask( ) ));
	}

	@Override
	protected TreeitemRenderer<?> getTreeRenderer( )
	{
		return new RoleTreeItemRenderer( this );
	}

	@Override
	protected RoleNode getRootNode( )
	{
		return RoleNode.createNode( getSession( ).getRootRole( ) );
	}

	@Override
	protected void showRecord( Role data )
	{
		List<Task> tasks;

		if( data != null ) {
			labelId.setValue( data.getId( ).toString( ) );
			labelDescription.setValue( data.getDescription( ) );
			tasks = getSession( ).getTaks( data );
		}
		else {
			tasks = Collections.emptyList( );
			labelId.setValue( "" );
			labelDescription.setValue( "" );
		}
		showMenus( data );
		getListTasks( ).setModel( new ListModelList<Task>( tasks ) );
	}

	private void showMenus( Role data )
	{
		if( mainMenu == null ) {
			return;
		}
		if( mainMenu.getChildren( ) != null ) {
			mainMenu.getChildren( ).clear( );
		}
		List<br.com.mcampos.entity.security.Menu> menus;
		try {
			menus = getSession( ).getMenus( data );
			for( br.com.mcampos.entity.security.Menu item : menus ) {
				getDynamicMenu( ).getParentComponent( item );
			}
		}
		catch( ApplicationException e ) {
			e = null;
		}
	}

	@Override
	protected boolean validateRecord( )
	{
		if( SysUtils.isEmpty( description.getValue( ) ) ) {
			Messagebox.show( "A descrição deve ser preenchida", "Descrição Inválida", Messagebox.OK, Messagebox.ERROR );
			description.setFocus( true );
			return false;
		}
		return true;
	}

	@Override
	public void prepareForInsert( )
	{
		getId( ).setRawValue( new Integer( getSession( ).getNextId( ) ) );
		getId( ).setReadonly( false );
		getDescription( ).setFocus( true );
		getDescription( ).setRawValue( "" );
	}

	@Override
	public void prepareForUpdate( Role data )
	{
		getId( ).setRawValue( data.getId( ) );
		getId( ).setReadonly( true );
		getDescription( ).setRawValue( data.getDescription( ) );
		getDescription( ).setFocus( true );
	}

	private Textbox getDescription( )
	{
		return description;
	}

	private Intbox getId( )
	{
		return id;
	}

	@Override
	protected RoleNode insert( )
	{
		Role role = new Role( getId( ).getValue( ), getDescription( ).getValue( ) );
		role.setParent( getSession( ).getRootRole( ) );
		getSession( ).merge( role );
		return RoleNode.createNode( role );
	}

	@Override
	protected void deleteTask( RoleSession session, Role entity, Task task )
	{
		session.remove( getPrincipal( ), entity, task );
	}

	@Override
	protected void addTask( RoleSession session, Role entity, Task task )
	{
		session.add( getPrincipal( ), entity, task );
	}

	@Override
	protected void update( RoleSession session, Role data )
	{
		data.setDescription( getDescription( ).getValue( ) );
		getSession( ).merge( data );
	}

	@Override
	protected void remove( RoleSession session, Role data )
	{
		if( data != null ) {
			session.remove( getPrincipal( ), data.getId( ) );
		}

	}

	@Override
	protected void changeParent( RoleSession session, Role source, Role target )
	{
		getSession( ).changeParent( getPrincipal( ), source, target );
	}

	protected DynamicMenu getDynamicMenu( )
	{
		if( dynamicMenu == null ) {
			dynamicMenu = new DynamicMenu( mainMenu, null );
		}
		return dynamicMenu;
	}
}
