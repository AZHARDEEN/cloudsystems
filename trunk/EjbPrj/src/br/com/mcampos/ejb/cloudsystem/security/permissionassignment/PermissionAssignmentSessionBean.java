package br.com.mcampos.ejb.cloudsystem.security.permissionassignment;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;


@Stateless( name = "PermissionAssignmentSessionBean", mappedName = "CloudSystems-EjbPrj-PermissionAssignmentSessionBean" )
@Local
public class PermissionAssignmentSessionBean extends Crud<PermissionAssignmentPK, PermissionAssignment> implements PermissionAssignmentSessionLocal
{
	public PermissionAssignmentSessionBean()
	{
	}


	public void delete( PermissionAssignmentPK key ) throws ApplicationException
	{
		delete( PermissionAssignment.class, key );
	}

	public PermissionAssignment get( PermissionAssignmentPK key ) throws ApplicationException
	{
		return get( PermissionAssignment.class, key );
	}

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
}
