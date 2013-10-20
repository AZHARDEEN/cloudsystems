package br.com.mcampos.ejb.security.role;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.security.menu.Menu;
import br.com.mcampos.ejb.security.menu.MenuFacadeLocal;
import br.com.mcampos.ejb.security.task.Task;
import br.com.mcampos.ejb.security.task.TaskSessionLocal;

/**
 * Session Bean implementation class RoleSessionBean
 */
@Stateless( name = "RoleSession", mappedName = "RoleSession" )
@LocalBean
public class RoleSessionBean extends SimpleSessionBean<Role> implements RoleSession, RoleSessionLocal
{

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
		return get( new Integer( 1 ) );
	}

	@Override
	public Role merge( Role newEntity )
	{
		Role parent = newEntity.getParent( ) != null ? get( newEntity.getParent( ).getId( ) ) : get( 1 );

		newEntity.setParent( null );
		Role e = super.merge( newEntity );
		e.setParent( parent );
		return e;
	}

	@Override
	public void changeParent( Role entity, Role newParent )
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
		for ( Role item : entity.getChilds( ) )
		{
			addTask( tasks, item );
		}
		for ( Task task : entity.getTasks( ) )
		{
			if ( tasks.contains( task ) == false ) {
				tasks.add( task );
			}
		}

	}

	@Override
	public Role remove( Role entity )
	{
		if ( entity == null ) {
			return entity;
		}
		Role toDelete = get( entity.getId( ) );
		if ( toDelete == null ) {
			return toDelete;
		}
		removeChilds( toDelete );
		super.remove( toDelete );
		return toDelete;
	}

	private void removeChilds( Role entity )
	{
		for ( Role item : entity.getChilds( ) ) {
			removeChilds( item );
			item.setParent( null );
			for ( Task task : getTaks( entity ) )
			{
				task.remove( entity );
			}
			super.remove( item );
		}
		entity.setChilds( null );
		entity.setTasks( null );
	}

	@Override
	public List<Task> getRootTask( )
	{
		return this.taskSession.getRootTasks( );
	}

	@Override
	public Role add( Role item, List<Task> tasks )
	{
		for ( Task task : tasks ) {
			add( item, task );
		}
		return item;
	}

	@Override
	public Role add( Role item, Task task )
	{
		Role merged = get( item.getId( ) );
		if ( merged != null )
		{
			Task taskMerged = this.taskSession.get( task.getId( ) );
			merged.add( taskMerged );
		}
		return merged;
	}

	@Override
	public Role remove( Role role, Task task )
	{
		Role merged = get( role.getId( ) );
		if ( merged != null )
		{
			Task taskMerged = this.taskSession.get( task.getId( ) );
			merged.remove( taskMerged );
		}
		return merged;
	}

	@Override
	public List<Menu> getMenus( Role role ) throws ApplicationException
	{
		List<Menu> menus = new ArrayList<Menu>( );
		Role m = get( role.getId( ) );
		if ( m != null ) {
			this.menuSession.addRoleToMenu( m, menus );
		}
		return menus;
	}
}