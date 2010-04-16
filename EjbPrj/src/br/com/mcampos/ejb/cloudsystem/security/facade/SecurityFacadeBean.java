package br.com.mcampos.ejb.cloudsystem.security.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.security.entity.Menu;
import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.ejb.cloudsystem.security.entity.TaskMenu;
import br.com.mcampos.ejb.cloudsystem.security.session.MenuSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.session.PermissionAssignmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.session.RoleSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.session.TaskMenuSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.session.TaskSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.util.SecurityUtils;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.security.PermissionAssignment;
import br.com.mcampos.ejb.entity.security.Subtask;
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
    PermissionAssignmentSessionLocal permissionSession;
    @EJB
    MenuSessionLocal menuSession;
    @EJB
    TaskMenuSessionLocal taskMenuSession;


    public SecurityFacadeBean()
    {
    }

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

    public RoleDTO getRootRole( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = roleSession.getRootRole();
        if ( role == null )
            return null;
        return role.toDTO();
    }


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return 8;
    }

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

    public RoleDTO add( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = DTOFactory.copy( dto );
        return roleSession.add( role ).toDTO();
    }

    public RoleDTO update( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = DTOFactory.copy( dto );
        return roleSession.update( role ).toDTO();
    }


    public void delete( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        roleSession.delete( dto.getId() );
    }

    public Integer getRoleMaxId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return roleSession.getMaxId();
    }


    /*
     *  TASKS
     */

    public List<TaskDTO> getSubtasks( AuthenticationDTO auth, TaskDTO task ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Task entity = DTOFactory.copy( task );
        List<Subtask> subtasks;
        try {
            subtasks = getEntityManager().createNamedQuery( Subtask.findbyTask ).setParameter( 1, entity ).getResultList();
            return toTaskDTOFromSubtask( subtasks );
        }
        catch ( NoResultException e ) {
            return Collections.emptyList();
        }
    }

    protected List<TaskDTO> toTaskDTOFromSubtask( List<Subtask> subtasks )
    {
        if ( SysUtils.isEmpty( subtasks ) )
            return Collections.emptyList();
        List<TaskDTO> tasks = new ArrayList<TaskDTO>( subtasks.size() );
        for ( Subtask s : subtasks )
            tasks.add( s.getSubTask().toDTO() );
        return tasks;
    }


    public List<TaskDTO> getRootTasks( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        List<Task> tasks = taskSession.getRoots();
        return toTaskDTO( tasks );
    }


    protected List<TaskDTO> toTaskDTO( List<Task> tasks )
    {
        if ( SysUtils.isEmpty( tasks ) )
            return Collections.emptyList();
        List<TaskDTO> dtos = new ArrayList<TaskDTO>( tasks.size() );
        for ( Task t : tasks )
            dtos.add( DTOFactory.copy( t ) );
        return dtos;
    }

    public List<TaskDTO> getTasks( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        List<Task> tasks;
        tasks = roleSession.getTasks( dto.getId() );
        return toTaskDTO( tasks );
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


    /*
     * OPERACOES EM MENUS
     */


    public List<TaskDTO> getMenuTasks( AuthenticationDTO auth, Integer menuId ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        try {
            System.out.println( "SecuritySessionBean.getMenuTasks" );
            Menu menu = menuSession.get( menuId );
            if ( menu != null ) {
                System.out.println( "SecuritySessionBean.getting Task List" );
                return SecurityUtils.toTaskDTOListFromTaskMenu( menu.getTasks() );
            }
        }
        catch ( NoResultException e ) {
            e = null;
        }
        return Collections.emptyList();
    }

    public Integer getNextSequence( AuthenticationDTO auth, Integer parentId ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return menuSession.getNextSequence( parentId );
    }

    public List<MenuDTO> getParentMenus( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return SecurityUtils.toMenuDTOList( menuSession.getAll() );
    }

    public MenuDTO update( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Menu menu = menuSession.get( dto.getId() );
        if ( menu == null ) {
            return null;
        }
        DTOFactory.copy( menu, dto );
        if ( dto.getParentId() != null )
            menu.setParentMenu( menuSession.get( dto.getParentId() ) );
        menu = menuSession.update( menu );
        return menu.toDTO();
    }

    public Integer getNextMenuId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return null;
    }

    public MenuDTO add( AuthenticationDTO auth, MenuDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Menu menu = DTOFactory.copy( dto );
        if ( dto.getParentId() != null )
            menu.setParentMenu( menuSession.get( dto.getParentId() ) );
        menu = menuSession.add( menu );
        return menu.toDTO();
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

    public void addMenuTask( AuthenticationDTO auth, MenuDTO menu, TaskDTO task ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Menu menuEntity = menuSession.get( menu.getId() );
        Task taskEntity = taskSession.get( task.getId() );
        if ( menuEntity != null && taskEntity != null ) {
            taskMenuSession.add( menuEntity, taskEntity );
        }
    }

    public void removeMenuTask( AuthenticationDTO auth, MenuDTO menu, TaskDTO task ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Menu menuEntity = menuSession.get( menu.getId() );
        Task taskEntity = taskSession.get( task.getId() );
        if ( menuEntity != null && taskEntity != null ) {
            taskMenuSession.delete( menuEntity, taskEntity );
        }
    }

    public List<MenuDTO> getMenus ( AuthenticationDTO auth, RoleDTO dto )throws ApplicationException
    {
        authenticate( auth );
        List<Menu> menus = new ArrayList<Menu> ();

        Role role = roleSession.get( dto.getId() );
        if ( role != null )
        {
            getMenus ( role, menus );
            if ( menus.size() > 0  )
            {
                List<MenuDTO> dtoList = new ArrayList<MenuDTO>( menus.size());
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
            parentDTO = DTOFactory.copy( newMenu.getParentMenu(), false );
            nIndex = menuList.indexOf( parentDTO );
            if ( nIndex == -1 ) {
                //parent is not in list. Must Add.
                parentDTO = addMenu( menuList, newMenu.getParentMenu() );
            }
            else {
                parentDTO = ( MenuDTO )menuList.get( nIndex );
            }
            dto = DTOFactory.copy( newMenu, false );
            return addMenu( parentDTO.getSubMenu(), dto );
        }
        else {
            dto = DTOFactory.copy( newMenu, false );
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


    protected void getMenus ( Role role, List<Menu> menus )
    {
        for ( Role r : role.getChildRoles() )
        {
            getEntityManager().refresh( r );
            getMenus ( r, menus );
        }
        List<PermissionAssignment> assignmentList = role.getPermissionAssignmentList();
        for ( PermissionAssignment p : assignmentList )
        {
            Task t = p.getTask();
            getEntityManager().refresh( t );
            if ( t != null )
                getMenus ( t, menus );
        }
    }

    protected void getMenus ( Task t, List<Menu> menus  )
    {
        if ( t != null ) {
            List<Subtask> subtasks = t.getSubtasks();
            for ( Subtask s : subtasks ) {
                getMenus( s.getSubTask(), menus );
            }
            List<TaskMenu> tmList = t.getTaskMenuList();
            for ( TaskMenu tm : tmList )
            {
                Menu menu = tm.getMenu();
                if ( menu != null )
                    getMenu ( menu, menus );
            }
        }
    }

    protected void getMenu ( Menu menu, List<Menu> menus )
    {
        if ( menu != null )
        {
            for ( Menu subMenu : menu.getSubMenus() )
                getMenu ( subMenu, menus );
            Menu parent = menu.getParentMenu();
            while ( parent != null )
            {
                if ( menus.contains( parent ) == false )
                    menus.add ( parent );
                parent = parent.getParentMenu();
            }
            if ( menus.contains( menu ) == false )
                menus.add( menu );
        }
    }
}
