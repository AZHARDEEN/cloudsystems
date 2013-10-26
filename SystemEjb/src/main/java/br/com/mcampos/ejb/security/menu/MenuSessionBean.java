package br.com.mcampos.ejb.security.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.security.LoginSessionLocal;
import br.com.mcampos.ejb.security.task.TaskSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.jpa.security.Menu;
import br.com.mcampos.jpa.security.Role;
import br.com.mcampos.jpa.security.Task;
import br.com.mcampos.jpa.user.Collaborator;

/**
 * Session Bean implementation class MenuFacadeBean
 */
@Stateless( name = "MenuFacade", mappedName = "MenuFacade" )
@LocalBean
public class MenuSessionBean extends SimpleSessionBean<Menu> implements MenuFacade, MenuSessionLocal
{
	private static final long serialVersionUID = -2522273966532535171L;

	@SuppressWarnings( "unused" )
	private static final Logger logger = LoggerFactory.getLogger( MenuSessionBean.class );

	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@EJB
	private TaskSessionLocal taskSession;

	@EJB
	private LoginSessionLocal loginSession;

	@Override
	protected Class<Menu> getEntityClass( )
	{
		return Menu.class;
	}

	@Override
	public List<Menu> getMenus( PrincipalDTO auth ) throws ApplicationException
	{
		if ( auth == null ) {
			return Collections.emptyList( );
		}
		return this.getMenus( this.collaboratorSession.find( auth ) );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public List<Menu> getMenus( Collaborator collaborator ) throws ApplicationException
	{
		List<Menu> availableMenus = Collections.emptyList( );
		if ( collaborator == null ) {
			return Collections.emptyList( );
		}
		collaborator = this.collaboratorSession.merge( collaborator );
		availableMenus = new ArrayList<Menu>( );
		for ( Role role : collaborator.getRoles( ) ) {
			this.addRoleToMenu( role, availableMenus );
		}
		return availableMenus;
	}

	@Override
	public void addRoleToMenu( Role role, List<Menu> availableMenus )
	{
		if ( role != null ) {
			for ( Role sub : role.getChilds( ) ) {
				this.addRoleToMenu( sub, availableMenus );
			}
			for ( Task task : role.getTasks( ) ) {
				this.addTaskToMenu( task, availableMenus );
			}
		}
	}

	private void addTaskToMenu( Task task, List<Menu> availableMenus )
	{
		if ( task != null ) {
			for ( Task child : task.getChilds( ) ) {
				this.addTaskToMenu( child, availableMenus );
			}
			for ( Menu menu : task.getMenus( ) ) {
				this.addMenus( menu, availableMenus );
			}
		}
	}

	private void addMenus( Menu item, List<Menu> availableMenus )
	{
		if ( item != null ) {
			this.addMenus( item.getParent( ), availableMenus );
			if ( availableMenus.contains( item ) == false ) {
				availableMenus.add( item );
			}
		}
	}

	@Override
	public List<Menu> getTopContextMenu( ) throws ApplicationException
	{
		return this.findByNamedQuery( Menu.getTopMenu );
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
		Menu merged = this.get( entity.getId( ) );
		this.addTask( tasks, merged );
		return tasks;
	}

	private void addTask( List<Task> tasks, Menu entity )
	{
		for ( Menu item : entity.getChilds( ) ) {
			this.addTask( tasks, item );
		}
		for ( Task task : entity.getTasks( ) ) {
			if ( tasks.contains( task ) == false ) {
				tasks.add( task );
			}
		}
	}

	@Override
	public void changeParent( Menu entity, Menu newParent )
	{
		Menu targetEntity = this.get( entity.getId( ) );
		Menu targetParent = this.get( newParent.getId( ) );

		Menu oldParent = targetEntity.getParent( );
		if ( oldParent != null ) {
			targetEntity = oldParent.remove( targetEntity );
		}
		targetParent.add( targetEntity );
	}

	@Override
	public Menu add( Menu item, List<Task> tasks )
	{
		for ( Task task : tasks ) {
			this.add( item, task );
		}
		return item;
	}

	@Override
	public Menu add( Menu item, Task task )
	{
		Menu merged = this.get( item.getId( ) );
		if ( merged != null ) {
			Task taskMerged = this.taskSession.get( task.getId( ) );
			merged.add( taskMerged );
		}
		return merged;
	}

	@Override
	public Menu remove( Menu item, Task task )
	{
		Menu merged = this.get( item.getId( ) );
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
		Menu menu = this.getByNamedQuery( Menu.getByUrl, Url );
		return menu;
	}

	@Override
	public Task getRootTask( )
	{
		return this.taskSession.getRootTask( );
	}

	@Override
	public Menu add( PrincipalDTO auth, Menu newEntity )
	{
		Menu m = super.add( auth, newEntity );
		m.add( this.taskSession.getRootTask( ) );
		return m;
	}

	@Override
	public Menu update( PrincipalDTO auth, Menu newEntity )
	{
		Menu m = super.update( auth, newEntity );
		m.add( this.taskSession.getRootTask( ) );
		return m;
	}
}
