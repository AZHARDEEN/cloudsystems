package br.com.mcampos.ejb.security.task;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.security.menu.MenuSessionLocal;
import br.com.mcampos.ejb.security.role.RoleSessionLocal;
import br.com.mcampos.jpa.security.Menu;
import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.jpa.security.Task;

/**
 * Session Bean implementation class TaskSessionBean
 */
@Stateless( name = "TaskSession", mappedName = "TaskSession" )
@LocalBean
public class TaskSessionBean extends SimpleSessionBean<Task> implements TaskSession, TaskSessionLocal
{
	private static final long serialVersionUID = -1783324606644700893L;

	private static final Integer rootId = 1;

	@EJB
	RoleSessionLocal roleSession;

	@EJB
	MenuSessionLocal menuSession;

	@Override
	protected Class<Task> getEntityClass( )
	{
		return Task.class;
	}

	@Override
	public List<Menu> getMenus( Integer id )
	{
		Task tsk = this.get( id );
		if ( tsk == null ) {
			return Collections.emptyList( );
		}
		else
		{
			tsk.getMenus( ).size( );
			return tsk.getMenus( );
		}
	}

	@Override
	public List<Role> getRoles( Integer id )
	{
		Task tsk = this.get( id );
		if ( tsk == null ) {
			return Collections.emptyList( );
		}
		else
		{
			tsk.getRoles( ).size( );
			return tsk.getRoles( );
		}
	}

	@Override
	public void changeParent( PrincipalDTO auth, Task entity, Task newParent )
	{
		Task targetEntity = this.get( entity.getId( ) );
		Task targetParent = this.get( newParent.getId( ) );

		Task oldParent = targetEntity.getParent( );
		if ( oldParent != null ) {
			oldParent.remove( targetEntity );
		}
		targetParent.add( targetEntity );
	}

	@Override
	public Role getRootRole( )
	{
		return this.roleSession.getRootRole( );
	}

	@Override
	public List<Menu> getTopContextMenu( ) throws ApplicationException
	{
		return this.menuSession.getTopContextMenu( );
	}

	@Override
	public Task add( PrincipalDTO auth, Task task, Role role )
	{
		this.roleSession.add( auth, role, task );
		return task;
	}

	@Override
	public Task remove( PrincipalDTO auth, Task task, Role role )
	{
		this.roleSession.remove( auth, role, task );
		return task;
	}

	@Override
	public Task add( PrincipalDTO auth, Task task, Menu menu )
	{
		this.menuSession.add( menu, task );
		return task;
	}

	@Override
	public Task remove( PrincipalDTO auth, Task task, Menu menu )
	{
		this.menuSession.remove( menu, task );
		return task;
	}

	public Task remove( PrincipalDTO auth, Integer id )
	{
		Task toDelete = this.get( id );
		if ( toDelete == null ) {
			return toDelete;
		}
		this.removeChilds( auth, toDelete );
		return toDelete;
	}

	private void removeChilds( PrincipalDTO auth, Task entity )
	{
		for ( Task item : entity.getChilds( ) ) {
			this.removeChilds( auth, item );
		}
		entity.setParent( null );
		this.removeRoles( entity );
		this.removeMenus( entity );
		entity.setChilds( null );
		super.remove( auth, entity );
	}

	private void removeRoles( Task entity )
	{
		List<Role> list = entity.getRoles( );
		entity.setRoles( null );
		for ( Role item : list )
		{
			item.remove( entity );
		}
	}

	private void removeMenus( Task entity )
	{
		List<Menu> list = entity.getMenus( );
		entity.setMenus( null );
		for ( Menu item : list )
		{
			item.remove( entity );
		}
	}

	@Override
	public Task getRootTask( )
	{
		return this.get( rootId );
	}

	@Override
	public Task merge( Task newEntity )
	{
		throw new InvalidParameterException( "Deprecated" );
	}

	@Override
	public Task add( PrincipalDTO auth, Task newEntity )
	{
		Task parent = newEntity.getParent( ) != null ? this.get( newEntity.getParent( ).getId( ) ) : this.getRootTask( );
		Task e = super.add( auth, newEntity );
		e.setParent( parent );
		return e;
	}
}
