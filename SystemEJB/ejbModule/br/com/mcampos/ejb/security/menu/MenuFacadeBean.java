package br.com.mcampos.ejb.security.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.omg.CORBA.portable.ApplicationException;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.security.role.Role;
import br.com.mcampos.ejb.security.task.Task;
import br.com.mcampos.ejb.security.task.TaskSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;

/**
 * Session Bean implementation class MenuFacadeBean
 */
@Stateless( name = "MenuFacade", mappedName = "MenuFacade" )
@LocalBean
public class MenuFacadeBean extends SimpleSessionBean<Menu> implements MenuFacade, MenuFacadeLocal
{
	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@EJB
	TaskSessionLocal taskSession;

	@Override
	protected Class<Menu> getEntityClass( )
	{
		return Menu.class;
	}

	@Override
	public List<Menu> getMenus( Collaborator collaborator ) throws ApplicationException
	{
		List<Menu> availableMenus = Collections.emptyList( );
		if ( collaborator == null ) {
			return Collections.emptyList( );
		}
		collaborator = this.collaboratorSession.merge( collaborator );
		availableMenus = new ArrayList<Menu>( );
		for( Role role : collaborator.getRoles( ) )
		{
			addRoleToMenu( role, availableMenus );
		}
		return availableMenus;
	}

	@Override
	public void addRoleToMenu( Role role, List<Menu> availableMenus )
	{
		if ( role != null ) {
			for( Role sub : role.getChilds( ) ) {
				addRoleToMenu( sub, availableMenus );
			}
			for( Task task : role.getTasks( ) ) {
				addTaskToMenu( task, availableMenus );
			}
		}
	}

	private void addTaskToMenu( Task task, List<Menu> availableMenus )
	{
		if ( task != null ) {
			for( Task child : task.getChilds( ) ) {
				addTaskToMenu( child, availableMenus );
			}
			for( Menu menu : task.getMenus( ) ) {
				addMenus( menu, availableMenus );
			}
		}
	}

	private void addMenus( Menu item, List<Menu> availableMenus )
	{
		if ( item != null ) {
			addMenus( item.getParent( ), availableMenus );
			if ( availableMenus.contains( item ) == false ) {
				availableMenus.add( item );
			}
		}
	}

	@Override
	public List<Menu> getTopContextMenu( ) throws ApplicationException
	{
		return findByNamedQuery( Menu.getTopMenu );
	}

	@Override
	public List<Task> getRootTask( )
	{
		return getTaskSession( ).getRootTasks( );
	}

	protected CollaboratorSessionLocal getCollaboratorSession( )
	{
		return this.collaboratorSession;
	}

	protected TaskSessionLocal getTaskSession( )
	{
		return this.taskSession;
	}

	@Override
	public List<Task> getTaks( Menu entity )
	{
		List<Task> tasks = new ArrayList<Task>( );
		Menu merged = get( entity.getId( ) );
		addTask( tasks, merged );
		return tasks;
	}

	private void addTask( List<Task> tasks, Menu entity )
	{
		for( Menu item : entity.getChilds( ) )
		{
			addTask( tasks, item );
		}
		for( Task task : entity.getTasks( ) )
		{
			if ( tasks.contains( task ) == false ) {
				tasks.add( task );
			}
		}
	}

	@Override
	public void changeParent( Menu entity, Menu newParent )
	{
		Menu targetEntity = get( entity.getId( ) );
		Menu targetParent = get( newParent.getId( ) );

		Menu oldParent = targetEntity.getParent( );
		if ( oldParent != null ) {
			targetEntity = oldParent.remove( targetEntity );
		}
		targetParent.add( targetEntity );
	}

	@Override
	public Menu add( Menu item, List<Task> tasks )
	{
		for( Task task : tasks ) {
			add( item, task );
		}
		return item;
	}

	@Override
	public Menu add( Menu item, Task task )
	{
		Menu merged = get( item.getId( ) );
		if ( merged != null ) {
			Task taskMerged = this.taskSession.get( task.getId( ) );
			merged.add( taskMerged );
		}
		return merged;
	}

	@Override
	public Menu remove( Menu item, Task task )
	{
		Menu merged = get( item.getId( ) );
		if ( merged != null ) {
			Task taskMerged = this.taskSession.get( task.getId( ) );
			merged.remove( taskMerged );
		}
		return merged;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.mcampos.ejb.security.menu.MenuFacadeLocal#get(java.lang.String)
	 */
	@Override
	public Menu get( String Url )
	{
		Menu menu = getByNamedQuery( Menu.getByUrl, Url );
		return menu;
	}
}
