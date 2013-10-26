package br.com.mcampos.ejb.cloudsystem.security.permissionassignment;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PermissionAssignmentSessionBean", mappedName = "PermissionAssignmentSessionBean" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PermissionAssignmentSessionBean extends Crud<PermissionAssignmentPK, PermissionAssignment> implements PermissionAssignmentSessionLocal
{
    public PermissionAssignmentSessionBean()
    {
    }


    public void delete( PermissionAssignmentPK key ) throws ApplicationException
    {
        delete( PermissionAssignment.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public PermissionAssignment get( PermissionAssignmentPK key ) throws ApplicationException
    {
        return get( PermissionAssignment.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PermissionAssignment> getAll( Role role ) throws ApplicationException
    {
        List<PermissionAssignment> permissions;
        permissions = ( List<PermissionAssignment> )getResultList( PermissionAssignment.findByRole, role );
        return permissions;
    }

    public void add( Role role, Task task ) throws ApplicationException
    {
        PermissionAssignmentPK pk = new PermissionAssignmentPK( role.getId(), task.getId() );
        PermissionAssignment entity = get( PermissionAssignment.class, pk );
        if ( entity == null ) {
            entity = new PermissionAssignment( role, task );
            getEntityManager().persist( entity );
            getEntityManager().refresh( role );
            getEntityManager().refresh( task );
        }
    }

    public void delete( Role role, Task task ) throws ApplicationException
    {
        PermissionAssignmentPK pk = new PermissionAssignmentPK( role.getId(), task.getId() );
        PermissionAssignment entity = get( PermissionAssignment.class, pk );
        if ( entity != null ) {
            getEntityManager().remove( entity );
            getEntityManager().refresh( role );
            getEntityManager().refresh( task );
        }
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PermissionAssignment> getPermissionsAssigments( List<Role> roles ) throws ApplicationException
    {
        ArrayList<PermissionAssignment> permissions = new ArrayList<PermissionAssignment>();
        for ( Role role : roles ) {
            List<PermissionAssignment> list = getAll( role );
            if ( SysUtils.isEmpty( list ) )
                continue;
            for ( PermissionAssignment p : list ) {
                if ( permissions.contains( p ) )
                    continue;
                permissions.add( p );
            }
        }
        return permissions;
    }

    public List<PermissionAssignment> getAll( List<Role> roles ) throws ApplicationException
    {
        if ( SysUtils.isEmpty( roles ) )
            return Collections.emptyList();
        ArrayList<PermissionAssignment> permissions = new ArrayList<PermissionAssignment>();
        for ( Role r : roles ) {
            List<PermissionAssignment> pl = getAll( r );
            if ( SysUtils.isEmpty( pl ) == false ) {
                for ( PermissionAssignment p : pl ) {
                    if ( permissions.contains( p ) )
                        continue;
                    permissions.add( p );
                }
            }
        }
        return permissions;
    }

}
