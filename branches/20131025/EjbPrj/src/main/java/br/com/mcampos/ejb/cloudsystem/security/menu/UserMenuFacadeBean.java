package br.com.mcampos.ejb.cloudsystem.security.menu;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentUtil;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleUtils;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskUtil;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.session.SystemUserPropertySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.CollaboratorRoleUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.entity.CollaboratorRole;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.session.CollaboratorRoleSessionLocal;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "UserMenuFacade", mappedName = "UserMenuFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class UserMenuFacadeBean extends AbstractSecurity implements UserMenuFacade
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4522487980714102423L;

	public static final Integer messageId = 17;

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager em;

	@EJB
	private CollaboratorRoleSessionLocal collaboratorRoleSession;

	@EJB
	private NewCollaboratorSessionLocal collaboratorSession;

	@EJB
	private PermissionAssignmentSessionLocal permissionAssignmentSession;

	@EJB
	private SystemUserPropertySessionLocal propertySession;

	public UserMenuFacadeBean( )
	{

	}

	@Override
	protected EntityManager getEntityManager( )
	{
		return em;
	}

	@Override
	public Integer getMessageTypeId( )
	{
		return messageId;
	}

	@Override
	public List<MenuDTO> getMenus( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth );
		Collaborator collaborator = collaboratorSession.get( auth.getCurrentCompany( ), auth.getUserId( ) );
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

	@Override
	public String getInitialPage( AuthenticationDTO auth ) throws ApplicationException
	{
		return null;
	}
}
