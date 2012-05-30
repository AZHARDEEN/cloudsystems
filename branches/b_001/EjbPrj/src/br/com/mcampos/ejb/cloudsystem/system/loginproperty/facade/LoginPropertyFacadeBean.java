package br.com.mcampos.ejb.cloudsystem.system.loginproperty.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.LoginPropertyDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.cloudsystem.security.menu.MenuUtils;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentUtil;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleUtils;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.ejb.cloudsystem.security.task.TaskUtil;
import br.com.mcampos.ejb.cloudsystem.system.loginproperty.Session.LoginPropertySessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.loginproperty.entity.LoginProperty;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.SystemUserPropertyUtil;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.entity.SystemUserProperty;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.session.SystemUserPropertySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.CollaboratorRoleUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.entity.CollaboratorRole;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.role.session.CollaboratorRoleSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "LoginPropertyFacade", mappedName = "CloudSystems-EjbPrj-LoginPropertyFacade" )
@Remote
public class LoginPropertyFacadeBean extends AbstractSecurity implements LoginPropertyFacade
{
    public static final Integer messageId = 40;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private LoginPropertySessionLocal loginPropertySession;

    @EJB
    private SystemUserPropertySessionLocal propertySession;

    @EJB
    private CollaboratorRoleSessionLocal collaboratorRoleSession;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private PermissionAssignmentSessionLocal permissionAssignmentSession;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public List<LoginPropertyDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Collaborator collaborator = collaboratorSession.get( auth.getCurrentCompany(), auth.getUserId() );
        List<SystemUserProperty> properties = propertySession.getAll();
        List<LoginPropertyDTO> loginProperties = new ArrayList<LoginPropertyDTO>();
        for ( SystemUserProperty p : properties ) {
            LoginPropertyDTO dto = new LoginPropertyDTO( SystemUserPropertyUtil.copy( p ) );
            LoginProperty prop = loginPropertySession.get( collaborator, p );
            if ( prop != null )
                dto.setValue( prop.getValue() );
            loginProperties.add( dto );
        }
        return loginProperties;
    }

    public List<MenuDTO> getMenus( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Collaborator collaborator = collaboratorSession.get( auth.getCurrentCompany(), auth.getUserId() );
        if ( collaborator == null )
            throwException( 1 );
        List<CollaboratorRole> collaboratorRoles = collaboratorRoleSession.getAll( collaborator );
        if ( SysUtils.isEmpty( collaboratorRoles ) )
            throwException( 2 );
        List<Role> roles = RoleUtils.getRoles( CollaboratorRoleUtil.toRoleList( collaboratorRoles ) );
        List<PermissionAssignment> permissions = permissionAssignmentSession.getPermissionsAssigments( roles );
        List<Task> tasks = PermissionAssignmentUtil.toTaskList( permissions );
        List<Menu> menus = TaskUtil.toMenuList( TaskUtil.getTasks( tasks ) );
        return MenuUtils.toMenuDTOList( menus );
    }

    public void update( AuthenticationDTO auth, Integer propertyId, String value ) throws ApplicationException
    {
        authenticate( auth );
        Collaborator collaborator = collaboratorSession.get( auth.getCurrentCompany(), auth.getUserId() );
        SystemUserProperty type = propertySession.get( propertyId );
        LoginProperty property = loginPropertySession.get( collaborator, type );
        if ( property == null ) {
            if ( SysUtils.isEmpty( value ) )
                return;
            property = new LoginProperty();
            property.setCollaborator( collaborator );
            property.setSystemUserProperty( type );
            property.setValue( value );
            loginPropertySession.add( property );
        }
        else {
            if ( SysUtils.isEmpty( value ) == false )
                property.setValue( value );
            else
                loginPropertySession.delete( property );
        }

    }
}
