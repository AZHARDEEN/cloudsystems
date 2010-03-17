package br.com.mcampos.ejb.cloudsystem.security.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.cloudsystem.security.session.RoleSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
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
import javax.persistence.PersistenceContext;


@Stateless( name = "SecurityFacade", mappedName = "CloudSystems-EjbPrj-SecurityFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW)
public class SecurityFacadeBean extends AbstractSecurity implements SecurityFacade
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;


    @EJB RoleSessionLocal roleSession;


    public SecurityFacadeBean()
    {
    }

    public List<RoleDTO> getRoles( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        List<Role> roles = roleSession.getAll();
        if ( SysUtils.isEmpty( roles ))
            return Collections.emptyList();
        List<RoleDTO> dtos = new ArrayList<RoleDTO> ( roles.size() );
        for ( Role role : roles)
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

    public List<RoleDTO> getChildRoles ( AuthenticationDTO auth, RoleDTO parent ) throws ApplicationException
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

    public RoleDTO add ( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = DTOFactory.copy (dto );
        return roleSession.add( role ).toDTO();
    }

    public RoleDTO update ( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        Role role = DTOFactory.copy (dto );
        return roleSession.update( role ).toDTO();
    }


    public void delete ( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        roleSession.delete( dto.getId() );
    }

    public Integer getRoleMaxId ( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth, Role.systemAdmimRoleLevel );
        return roleSession.getMaxId();
    }
}
