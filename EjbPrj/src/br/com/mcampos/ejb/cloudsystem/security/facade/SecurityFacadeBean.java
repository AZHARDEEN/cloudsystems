package br.com.mcampos.ejb.cloudsystem.security.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.cloudsystem.security.menu.MenuSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.menu.MenuUtils;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskUtil;
import br.com.mcampos.ejb.cloudsystem.security.task.subtask.Subtask;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenu;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenuSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenuUtil;
import br.com.mcampos.ejb.cloudsystem.security.util.SecurityUtils;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


@Stateless( name = "SecurityFacade", mappedName = "CloudSystems-EjbPrj-SecurityFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class SecurityFacadeBean extends AbstractSecurity implements SecurityFacade
{
	@PersistenceContext( unitName = "EjbPrj" )
	private EntityManager em;


	@EJB
	RoleSessionLocal roleSession;
	@EJB
	TaskSessionLocal taskSession;
	@EJB
	MenuSessionLocal menuSession;
	@EJB
	TaskMenuSessionLocal taskMenuSession;


	public SecurityFacadeBean()
	{
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<RoleDTO> getRoles( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		List<Role> roles = roleSession.getAll();
		if ( SysUtils.isEmpty( roles ) )
			return Collections.emptyList();
		List<RoleDTO> dtos = new ArrayList<RoleDTO>( roles.size() );
		for ( Role role : roles )
			dtos.add( role.toDTO() );
		return dtos;
	}

	protected EntityManager getEntityManager()
	{
		return em;
	}

	public Integer getMessageTypeId()
	{
		return 8;
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<RoleDTO> getChildRoles( AuthenticationDTO auth, RoleDTO parent ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Role role = roleSession.get( parent.getId() );
		List<Role> list = roleSession.getChildRoles( role );
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList();
		for ( Role r : list )
			parent.add( r.toDTO() );
		return parent.getChildRoles();
	}


	public void delete( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		roleSession.delete( dto.getId() );
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public Integer getRoleMaxId( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return roleSession.getMaxId();
	}


	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return TaskUtil.toTaskDTOList( taskSession.getRoots() );
	}


	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<TaskDTO> getTasks( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return TaskUtil.toTaskDTOList( roleSession.getTasks( dto.getId() ) );
	}


	/*
     * OPERACOES EM MENUS
     */


	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<TaskDTO> getMenuTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		try {
			Menu menu = menuSession.get( menuId );
			if ( menu != null ) {
				return SecurityUtils.toTaskDTOListFromTaskMenu( menu.getTasks() );
			}
		}
		catch ( NoResultException e ) {
			e = null;
		}
		return Collections.emptyList();
	}


	@TransactionAttribute( TransactionAttributeType.NEVER )
	public Integer getNextSequence( AuthenticationDTO auth, Integer parentId ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return menuSession.getNextSequence( parentId );
	}


	public Boolean validate( MenuDTO dto, Boolean isNew ) throws ApplicationException
	{
		return null;
	}

	public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		menuSession.delete( id );
	}

	public List<MenuDTO> getMenus( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
	{
		authenticate( auth );
		List<Menu> menus = new ArrayList<Menu>();

		Role role = roleSession.get( dto.getId() );
		if ( role != null ) {
			getMenus( role, menus );
			if ( menus.size() > 0 ) {
				List<MenuDTO> dtoList = new ArrayList<MenuDTO>( menus.size() );
				for ( Menu m : menus ) {
					addMenu( dtoList, m );
				}
				return dtoList;
			}
		}
		return Collections.emptyList();
	}

	protected MenuDTO addMenu( List menuList, Menu newMenu )
	{
		MenuDTO dto, parentDTO = null;
		int nIndex;

		if ( newMenu.getParentMenu() != null ) {
			//this menu has a parent menu.
			parentDTO = MenuUtils.copy( newMenu.getParentMenu(), false );
			nIndex = menuList.indexOf( parentDTO );
			if ( nIndex == -1 ) {
				//parent is not in list. Must Add.
				parentDTO = addMenu( menuList, newMenu.getParentMenu() );
			}
			else {
				parentDTO = ( MenuDTO )menuList.get( nIndex );
			}
			dto = MenuUtils.copy( newMenu, false );
			return addMenu( parentDTO.getSubMenu(), dto );
		}
		else {
			dto = MenuUtils.copy( newMenu, false );
			return addMenu( menuList, dto );
		}
	}

	protected MenuDTO addMenu( List menuList, MenuDTO dto )
	{
		int nIndex;

		nIndex = menuList.indexOf( dto );
		if ( nIndex == -1 ) {
			for ( MenuDTO sibling : ( List<MenuDTO> )menuList ) {
				if ( dto.getSequence() > sibling.getSequence() )
					continue;
				nIndex = menuList.indexOf( sibling );
				break;
			}
			if ( nIndex == -1 )
				menuList.add( dto );
			else
				menuList.add( nIndex, dto );
			return dto;
		}
		else
			return ( MenuDTO )menuList.get( nIndex );
	}


	protected void getMenus( Role role, List<Menu> menus )
	{
		for ( Role r : role.getChildRoles() ) {
			getEntityManager().refresh( r );
			getMenus( r, menus );
		}
		List<PermissionAssignment> assignmentList = role.getPermissionAssignmentList();
		for ( PermissionAssignment p : assignmentList ) {
			Task t = p.getTask();
			getEntityManager().refresh( t );
			if ( t != null )
				getMenus( t, menus );
		}
	}

	protected void getMenus( Task t, List<Menu> menus )
	{
		if ( t != null ) {
			List<Subtask> subtasks = t.getSubtasks();
			for ( Subtask s : subtasks ) {
				getMenus( s.getSubTask(), menus );
			}
			List<TaskMenu> tmList = t.getTaskMenuList();
			for ( TaskMenu tm : tmList ) {
				Menu menu = tm.getMenu();
				if ( menu != null )
					getMenu( menu, menus );
			}
		}
	}

	protected void getMenu( Menu menu, List<Menu> menus )
	{
		if ( menu != null ) {
			for ( Menu subMenu : menu.getSubMenus() )
				getMenu( subMenu, menus );
			Menu parent = menu.getParentMenu();
			while ( parent != null ) {
				if ( menus.contains( parent ) == false )
					menus.add( parent );
				parent = parent.getParentMenu();
			}
			if ( menus.contains( menu ) == false )
				menus.add( menu );
		}
	}


	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<TaskDTO> getTasks( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return TaskUtil.toTaskDTOList( taskSession.getAll() );
	}


	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<MenuDTO> getMenus( AuthenticationDTO auth, TaskDTO taskId ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Task task = taskSession.get( taskId.getId() );
		return TaskMenuUtil.toMenuDTOList( task.getTaskMenuList() );
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<RoleDTO> getRoles( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Task task = taskSession.get( dtoTask.getId() );
		return SecurityUtils.toRoleDTOListFromPermission( task.getPermissionAssignmentList() );
	}

	public void delete( AuthenticationDTO auth, TaskDTO id ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		taskSession.delete( id.getId() );
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<TaskDTO> getSubTasks( AuthenticationDTO auth, TaskDTO dtoTask ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Task task = taskSession.get( dtoTask.getId() );
		if ( task != null ) {
			return SecurityUtils.toTaskDTOListFromSubtask( task.getSubtasks() );
		}
		return Collections.emptyList();
	}

}
