package br.com.mcampos.ejb.cloudsystem.security.task;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.cloudsystem.security.menu.MenuSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.menu.MenuUtils;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenuSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenuUtil;
import br.com.mcampos.ejb.cloudsystem.security.util.SecurityUtils;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "TaskFacade", mappedName = "CloudSystems-EjbPrj-TaskFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class TaskFacadeBean extends AbstractSecurity implements TaskFacade
{
	public static final Integer messageId = 14;

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager em;

	@EJB
	private TaskSessionLocal taskSession;

	@EJB
	private MenuSessionLocal menuSession;

	@EJB
	TaskMenuSessionLocal taskMenuSession;

	@EJB
	RoleSessionLocal roleSession;

	@EJB
	PermissionAssignmentSessionLocal permissionSession;

	public TaskFacadeBean()
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
	public List<TaskDTO> getTasks( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return TaskUtil.toTaskDTOList( taskSession.getAll() );
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return TaskUtil.toTaskDTOList( taskSession.getRoots() );
	}

	public TaskDTO update( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );

		Task entity = taskSession.get( dto.getId() );
		if ( entity == null )
			throwException( 1 );
		entity = TaskUtil.update( entity, dto );
		entity = taskSession.update( entity );
		return entity.toDTO();
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public Integer getNextTaskId( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		return taskSession.getNextTaskId();
	}

	public TaskDTO add( AuthenticationDTO auth, TaskDTO dto ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );

		Task entity = taskSession.get( dto.getId() );
		if ( entity != null )
			throwException( 2 );
		entity = TaskUtil.createEntity( dto );
		entity = taskSession.add( entity );
		return entity.toDTO();
	}

	public Boolean validate( TaskDTO dto, Boolean isNew )
	{
		return null;
	}

	public void delete( AuthenticationDTO auth, TaskDTO id ) throws ApplicationException
	{
		taskSession.delete( id.getId() );
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public TaskDTO getTask( AuthenticationDTO auth, Integer taskId ) throws ApplicationException
	{
		Task task = taskSession.get( taskId );
		return task.toDTO();
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

	protected TaskSessionLocal getTaskSession()
	{
		return taskSession;
	}

	protected MenuSessionLocal getMenuSession()
	{
		return menuSession;
	}


	@TransactionAttribute( TransactionAttributeType.NEVER )
	public List<MenuDTO> getParentMenus( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		List<Menu> menus = getMenuSession().getAll();
		return MenuUtils.toMenuDTOList( menus );
	}

	public void add( AuthenticationDTO auth, TaskDTO dtoTask, MenuDTO menuDTO ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Menu menu = menuSession.get( menuDTO.getId() );
		Task task = taskSession.get( dtoTask.getId() );
		taskMenuSession.add( menu, task );
	}


	public void remove( AuthenticationDTO auth, TaskDTO dtoTask, MenuDTO menuDTO ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Menu menu = menuSession.get( menuDTO.getId() );
		Task task = taskSession.get( dtoTask.getId() );
		taskMenuSession.delete( menu, task );
	}

	@TransactionAttribute( TransactionAttributeType.NEVER )
	public RoleDTO getRootRole( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Role role = roleSession.getRootRole();
		return role != null ? role.toDTO() : null;
	}

	public void add( AuthenticationDTO auth, TaskDTO taskDTO, RoleDTO roleDTO ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Role role = roleSession.get( roleDTO.getId() );
		if ( role == null )
			return;
		Task task = taskSession.get( taskDTO.getId() );
		if ( task == null )
			return;
		permissionSession.add( role, task );
	}

	public void remove( AuthenticationDTO auth, TaskDTO taskDTO, RoleDTO roleDTO ) throws ApplicationException
	{
		authenticate( auth, Role.systemAdmimRoleLevel );
		Role role = roleSession.get( roleDTO.getId() );
		Task task = taskSession.get( taskDTO.getId() );
		if ( role != null && task != null )
			permissionSession.delete( role, task );
	}

}
