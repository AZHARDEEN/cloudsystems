package br.com.mcampos.ejb.cloudsystem.security.session;


import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.NoResultException;
import javax.persistence.Query;


@Stateless( name = "RoleSession", mappedName = "CloudSystems-EjbPrj-RoleSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class RoleSessionBean extends Crud<Integer, Role> implements RoleSessionLocal
{
    public RoleSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        if ( SysUtils.isZero( key ) )
            return;
        super.delete( Role.class, key );
    }

    public Role get( Integer key ) throws ApplicationException
    {
        if ( SysUtils.isZero( key ) )
            return null;
        return super.get( Role.class, key );
    }

    public List<Role> getAll() throws ApplicationException
    {
        return (List<Role>) getResultList( Role.roleGetAll );
    }


    public Role getRootRole() throws ApplicationException
    {
        return (Role) getSingleResult( Role.roleGetRoot );
    }

    public List<Role> getChildRoles( Role role ) throws ApplicationException
    {
        List<Role> roles = (List<Role>) getResultList( Role.roleGetChilds, role );
        return roles;
    }

    public Integer getMaxId ( )
    {
        Integer sequence;
        try {
            Query q;

            q = getEntityManager().createNamedQuery( Role.roleMaxId );
            sequence = ( Integer )q.getSingleResult();
        }
        catch ( NoResultException e ) {
            sequence = 1;
            e = null;
        }
        return sequence;
    }
}
