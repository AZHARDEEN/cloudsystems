package br.com.mcampos.ejb.cloudsystem.security.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.cloudsystem.security.session.RoleSessionLocal;
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

    public List<RoleDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
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

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return 8;
    }
}
