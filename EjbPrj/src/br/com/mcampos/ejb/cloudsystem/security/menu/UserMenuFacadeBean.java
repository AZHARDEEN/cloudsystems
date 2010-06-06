package br.com.mcampos.ejb.cloudsystem.security.menu;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentUtil;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleUtils;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.CollaboratorRole;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.CollaboratorRoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.CollaboratorRoleUtil;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "UserMenuFacade", mappedName = "CloudSystems-EjbPrj-UserMenuFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class UserMenuFacadeBean extends AbstractSecurity implements UserMenuFacade
{
	public static final Integer messageId = 17;

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager em;

	@EJB
	private CollaboratorRoleSessionLocal collaboratorRoleSession;

	@EJB
	private NewCollaboratorSessionLocal collaboratorSession;

	@EJB
	private PermissionAssignmentSessionLocal permissionAssignmentSession;


	public UserMenuFacadeBean()
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

	public List<MenuDTO> getMenus( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth );
		Collaborator collaborator = collaboratorSession.get( auth.getUserId() );
		if ( collaborator == null )
			throwException( 1 );
		List<CollaboratorRole> collaboratorRoles = collaboratorRoleSession.getAll( collaborator );
		if ( SysUtils.isEmpty( collaboratorRoles ) )
			throwException( 2 );
		List<Role> roles = RoleUtils.getRoles( CollaboratorRoleUtil.toRoleList( collaboratorRoles ) );
		List<PermissionAssignment> permissions = permissionAssignmentSession.getPermissionsAssigments( roles );
		List<Task> tasks = PermissionAssignmentUtil.toTaskList( permissions );
		List<Menu> menus = TaskUtil.toMenuList( TaskUtil.getTasks( tasks ) );
		menus = MenuUtils.getParents( menus );
		return MenuUtils.organizeAndCopy( menus );
	}

}
