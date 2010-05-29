package br.com.mcampos.ejb.cloudsystem.security.menu;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskUtil;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenu;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenuSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenuUtil;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "MenuFacade", mappedName = "CloudSystems-EjbPrj-MenuFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class MenuFacadeBean extends AbstractSecurity implements MenuFacade
{
	public static final Integer messageId = 12;

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager em;

	@EJB
	private MenuSessionLocal menuSession;

	@EJB
	private TaskMenuSessionLocal taskMenuSession;

	@EJB
	private TaskSessionLocal taskSession;

	public MenuFacadeBean()
	{
	}

	protected EntityManager getEntityManager()
	{
		return em;
	}

	public Integer getMessageTypeId()
	{
		return messageId;
	}


	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<TaskDTO> getMenuTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Menu menu = menuSession.get( menuId );
		if ( menu == null )
			return Collections.emptyList();
		List<TaskMenu> list = taskMenuSession.getAll( menu );
		return TaskMenuUtil.toTaskDTOList( list );
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Integer getNextSequence( AuthenticationDTO auth, Integer parentId ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return menuSession.getNextSequence( parentId );
	}

	public MenuDTO update( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Menu menu = menuSession.get( dto.getId() );
		if ( menu == null )
			throwException( 2 );
		changeParent( menu, dto );
		MenuUtils.update( menu, dto );
		menuSession.update( menu );
		return MenuUtils.copy( menu, true );
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public Integer getNextMenuId( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return menuSession.getNextId();
	}

	public MenuDTO add( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Menu menu = MenuUtils.createEntity( dto );
		menu = menuSession.add( menu );
		return MenuUtils.copy( menu, true );
	}

	public Boolean validate( MenuDTO dto, Boolean isNew ) throws ApplicationException
	{
		return true;
	}

	public void delete( AuthenticationDTO auth, MenuDTO id ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		menuSession.delete( id.getId() );
	}

	public void addMenuTask( AuthenticationDTO auth, MenuDTO menuDTO, TaskDTO taskDTO ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Task task = getTaskSession().get( taskDTO.getId() );
		if ( task == null )
			throwException( 1 );
		Menu menu = menuSession.get( menuDTO.getId() );
		if ( menu == null )
			throwException( 2 );
		taskMenuSession.add( menu, task );
	}

	public void removeMenuTask( AuthenticationDTO auth, MenuDTO menuDTO, TaskDTO taskDTO ) throws ApplicationException
	{
		authenticate( auth );
		Task task = getTaskSession().get( taskDTO.getId() );
		if ( task == null )
			throwException( 1 );
		Menu menu = menuSession.get( menuDTO.getId() );
		if ( menu == null )
			throwException( 2 );
		taskMenuSession.delete( menu, task );
	}

	private void changeParent( Menu entity, MenuDTO dto ) throws ApplicationException
	{
		Menu newParent = null, oldParent;

		/*Get old parent and new parent, if any*/
		oldParent = entity.getParentMenu();
		if ( oldParent != null && oldParent.equals( newParent ) == false ) {
			oldParent.removeMenu( entity );
		}
		if ( SysUtils.isZero( dto.getParentId() ) == false ) {
			newParent = getEntityManager().find( Menu.class, dto.getParentId() );
			if ( newParent == null )
				throwRuntimeException( 3 );
		}
		entity.setParentMenu( newParent );
		if ( oldParent != null )
			getEntityManager().merge( oldParent );
	}

	protected TaskSessionLocal getTaskSession()
	{
		return taskSession;
	}

	protected MenuSessionLocal getMenuSession()
	{
		return menuSession;
	}


	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return TaskUtil.toTaskDTOList( getTaskSession().getRoots() );
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<MenuDTO> getParentMenus( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		List<Menu> menus = getMenuSession().getAll();
		return MenuUtils.toMenuDTOList( menus );
	}
}
