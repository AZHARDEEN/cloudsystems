package br.com.mcampos.ejb.cloudsystem.security.permissionassignment;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PermissionAssignmentSessionLocal extends Serializable
{
	PermissionAssignment add( PermissionAssignment entity ) throws ApplicationException;

	void delete( PermissionAssignmentPK key ) throws ApplicationException;

	PermissionAssignment get( PermissionAssignmentPK key ) throws ApplicationException;

	List<PermissionAssignment> getAll( Role role ) throws ApplicationException;

	void add( Role role, Task task ) throws ApplicationException;

	void delete( Role role, Task task ) throws ApplicationException;
}
