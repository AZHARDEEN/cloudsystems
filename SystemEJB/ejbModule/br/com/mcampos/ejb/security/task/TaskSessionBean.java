package br.com.mcampos.ejb.security.task;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.security.menu.MenuFacadeLocal;
import br.com.mcampos.ejb.security.role.RoleSessionLocal;
import br.com.mcampos.entity.security.Menu;
import br.com.mcampos.entity.security.Role;
import br.com.mcampos.entity.security.Task;

/**
 * Session Bean implementation class TaskSessionBean
 */
@Stateless( name = "TaskSession", mappedName = "TaskSession" )
@LocalBean
public class TaskSessionBean extends SimpleSessionBean<Task> implements TaskSession, TaskSessionLocal
{

	private static final Integer rootId = 1;

	@EJB
	RoleSessionLocal roleSession;

	@EJB
	MenuFacadeLocal menuSession;

	@Override
	protected Class<Task> getEntityClass( )
	{
		return Task.class;
	}

	@Override
	public List<Menu> getMenus( Integer id )
	{
		Task tsk = get( id );
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
		Task tsk = get( id );
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
	public void changeParent( Task entity, Task newParent )
	{
		Task targetEntity = get( entity.getId( ) );
		Task targetParent = get( newParent.getId( ) );

		Task oldParent = targetEntity.getParent( );
		if ( oldParent != null ) {
			oldParent.remove( targetEntity );
		}
		targetParent.add( targetEntity );
	}

	@Override
	public Role getRootRole( )
	{
		return roleSession.getRootRole( );
	}

	@Override
	public List<Menu> getTopContextMenu( ) throws ApplicationException
	{
		return menuSession.getTopContextMenu( );
	}

	@Override
	public Task add( Task task, Role role )
	{
		roleSession.add( role, task );
		return task;
	}

	@Override
	public Task remove( Task task, Role role )
	{
		roleSession.remove( role, task );
		return task;
	}

	@Override
	public Task add( Task task, Menu menu )
	{
		menuSession.add( menu, task );
		return task;
	}

	@Override
	public Task remove( Task task, Menu menu )
	{
		menuSession.remove( menu, task );
		return task;
	}

	@Override
	public Task remove( Task entity )
	{
		if ( entity == null ) {
			return entity;
		}
		Task toDelete = get( entity.getId( ) );
		if ( toDelete == null ) {
			return toDelete;
		}
		removeChilds( toDelete );
		return toDelete;
	}

	private void removeChilds( Task entity )
	{
		for ( Task item : entity.getChilds( ) ) {
			removeChilds( item );
		}
		entity.setParent( null );
		removeRoles( entity );
		removeMenus( entity );
		entity.setChilds( null );
		super.remove( entity );
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
		return get( rootId );
	}

	@Override
	public Task merge( Task newEntity )
	{
		Task parent = newEntity.getParent( ) != null ? get( newEntity.getParent( ).getId( ) ) : getRootTask( );
		Task e = super.merge( newEntity );
		e.setParent( parent );
		return e;
	}

	@Override
	public Task add( Task newEntity )
	{
		Task parent = newEntity.getParent( ) != null ? get( newEntity.getParent( ).getId( ) ) : getRootTask( );
		Task e = super.add( newEntity );
		e.setParent( parent );
		return e;
	}
}
