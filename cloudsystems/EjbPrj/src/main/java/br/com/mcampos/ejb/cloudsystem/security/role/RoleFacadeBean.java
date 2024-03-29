package br.com.mcampos.ejb.cloudsystem.security.role;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.cloudsystem.security.menu.MenuUtils;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentUtil;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "RoleFacade", mappedName = "RoleFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class RoleFacadeBean extends AbstractSecurity implements RoleFacade
{
    public static final Integer messageId = 14;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private TaskSessionLocal taskSession;

    @EJB
    RoleSessionLocal roleSession;

    @EJB
    PermissionAssignmentSessionLocal permissionSession;

    public RoleFacadeBean()
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

    public List<RoleDTO> getRoles( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        List<Role> roles = roleSession.getAll();
        return RoleUtils.toRoleDTOList( roles );
    }

    public RoleDTO getRootRole( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = roleSession.getRootRole();
        return role != null ? role.toDTO() : null;
    }

    public List<RoleDTO> getChildRoles( AuthenticationDTO auth, RoleDTO parent ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = roleSession.get( parent.getId() );
        return RoleUtils.toRoleDTOList( role.getChildRoles() );
    }

    public RoleDTO add( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = roleSession.get( dto.getId() );
        if ( role != null )
            throwException( 1 );
        role = RoleUtils.createEntity( dto );
        return roleSession.add( role ).toDTO();
    }

    public RoleDTO update( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = roleSession.get( dto.getId() );
        if ( role == null )
            throwException( 1 );
        role = RoleUtils.update( role, dto );
        return roleSession.update( role ).toDTO();
    }

    public Integer getRoleMaxId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return roleSession.getMaxId();
    }

    public void delete( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        roleSession.delete( dto.getId() );
    }


    public List<TaskDTO> getTasks( AuthenticationDTO auth, RoleDTO dto, Boolean bChildTasks ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = roleSession.get( dto.getId() );
        if ( role == null )
            return Collections.emptyList();
        List<Role> roles = new ArrayList<Role>();
        if ( bChildTasks )
            RoleUtils.getRoles( role, roles );
        else
            roles.add( role );
        List<PermissionAssignment> permissions = permissionSession.getAll( roles );
        List<Task> tasks = PermissionAssignmentUtil.toTaskList( permissions );
        return TaskUtil.toTaskDTOList( tasks );
    }

    public List<MenuDTO> getMenus( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = roleSession.get( dto.getId() );
        if ( role == null )
            return Collections.emptyList();
        List<Role> roles = new ArrayList<Role>();
        RoleUtils.getRoles( role, roles );
        List<PermissionAssignment> permissions = permissionSession.getPermissionsAssigments( roles );
        List<Task> tasks = PermissionAssignmentUtil.toTaskList( permissions );
        List<Menu> menus = TaskUtil.toMenuList( TaskUtil.getTasks( tasks ) );
        menus = MenuUtils.getParents( menus );
        return MenuUtils.organizeAndCopy( menus );
    }

    public List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return TaskUtil.toTaskDTOList( taskSession.getRoots() );
    }

    public void add( AuthenticationDTO auth, RoleDTO roleDTO, TaskDTO taskDTO ) throws ApplicationException
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

    public void remove( AuthenticationDTO auth, RoleDTO roleDTO, TaskDTO taskDTO ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = roleSession.get( roleDTO.getId() );
        Task task = taskSession.get( taskDTO.getId() );
        if ( role != null && task != null )
            permissionSession.delete( role, task );
    }

}
