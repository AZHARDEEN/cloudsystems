package br.com.mcampos.ejb.security.role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.security.menu.MenuFacadeLocal;
import br.com.mcampos.ejb.security.task.TaskSessionLocal;
import br.com.mcampos.jpa.security.Menu;
import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.jpa.security.Task;

/**
 * Session Bean implementation class RoleSessionBean
 */
@Stateless( name = "RoleSession", mappedName = "RoleSession" )
@LocalBean
public class RoleSessionBean extends SimpleSessionBean<Role> implements RoleSession, RoleSessionLocal
{

	private static final Integer rootId = 1;

	@EJB
	TaskSessionLocal taskSession;

	@EJB
	MenuFacadeLocal menuSession;

	@Override
	protected Class<Role> getEntityClass( )
	{
		return Role.class;
	}

	@Override
	public Role getRootRole( )
	{
		return get( rootId );
	}

	@Override
	public Role merge( Role newEntity )
	{
		Role parent = newEntity.getParent( ) != null ? get( newEntity.getParent( ).getId( ) ) : getRootRole( );

		newEntity.setParent( null );
		Role e = super.merge( newEntity );
		e.setParent( parent );
		return e;
	}

	@Override
	public void changeParent( PrincipalDTO auth, Role entity, Role newParent )
	{
		Role targetEntity = get( entity.getId( ) );
		Role targetParent = get( newParent.getId( ) );

		Role oldParent = targetEntity.getParent( );
		targetParent.add( oldParent.remove( targetEntity ) );
	}

	@Override
	public List<Task> getTaks( Role entity )
	{
		List<Task> tasks = new ArrayList<Task>( );
		Role merged = get( entity.getId( ) );
		addTask( tasks, merged );
		return tasks;
	}

	private void addTask( List<Task> tasks, Role entity )
	{
		for( Role item : entity.getChilds( ) )
		{
			addTask( tasks, item );
		}
		for( Task task : entity.getTasks( ) )
		{
			if( tasks.contains( task ) == false ) {
				tasks.add( task );
			}
		}

	}

	@Override
	public Role remove( PrincipalDTO auth, Serializable key )
	{
		Role toDelete = get( key );
		if( toDelete == null ) {
			return toDelete;
		}
		removeChilds( auth, toDelete );
		super.remove( auth, toDelete.getId( ) );
		return toDelete;
	}

	private void removeChilds( PrincipalDTO auth, Role entity )
	{
		for( Role item : entity.getChilds( ) ) {
			removeChilds( auth, item );
			item.setParent( null );
			for( Task task : getTaks( entity ) )
			{
				task.remove( entity );
			}
			super.remove( auth, item.getId( ) );
		}
		entity.setChilds( null );
		entity.setTasks( null );
	}

	@Override
	public Role add( PrincipalDTO auth, Role item, List<Task> tasks )
	{
		for( Task task : tasks ) {
			add( auth, item, task );
		}
		return item;
	}

	@Override
	public Role add( PrincipalDTO auth, Role item, Task task )
	{
		Role merged = get( item.getId( ) );
		if( merged != null )
		{
			Task taskMerged = taskSession.get( task.getId( ) );
			merged.add( taskMerged );
		}
		return merged;
	}

	@Override
	public Role remove( PrincipalDTO auth, Role role, Task task )
	{
		Role merged = get( role.getId( ) );
		if( merged != null )
		{
			Task taskMerged = taskSession.get( task.getId( ) );
			merged.remove( taskMerged );
		}
		return merged;
	}

	@Override
	public List<Menu> getMenus( Role role ) throws ApplicationException
	{
		if( role != null ) {
			List<Menu> menus = new ArrayList<Menu>( );
			Role m = get( role.getId( ) );
			if( m != null ) {
				menuSession.addRoleToMenu( m, menus );
			}
			return menus;
		}
		else
			return Collections.emptyList( );
	}

	@Override
	public Task getRootTask( )
	{
		return taskSession.getRootTask( );
	}

	@Override
	public Role add( PrincipalDTO auth, Role newEntity )
	{
		Role parent = newEntity.getParent( ) != null ? get( newEntity.getParent( ).getId( ) ) : getRootRole( );
		Role e = super.add( auth, newEntity );
		e.setParent( parent );
		return e;
	}
}
