package br.com.mcampos.ejb.cloudsystem.security.role;


import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "RoleSession", mappedName = "CloudSystems-EjbPrj-RoleSession" )
@TransactionAttribute( TransactionAttributeType.SUPPORTS )
public class RoleSessionBean extends Crud<Integer, Role> implements RoleSessionLocal
{
	@EJB
	PermissionAssignmentSessionLocal permissionSession;

	public RoleSessionBean()
	{
	}

	@TransactionAttribute( TransactionAttributeType.MANDATORY )
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
		Role role = super.get( Role.class, key );
		return role;
	}

	public List<Role> getAll() throws ApplicationException
	{
		return ( List<Role> )getResultList( Role.roleGetAll );
	}


	public Role getRootRole() throws ApplicationException
	{
		return ( Role )getSingleResult( Role.roleGetRoot );
	}

	public List<Role> getChildRoles( Role role ) throws ApplicationException
	{
		List<Role> roles = ( List<Role> )getResultList( Role.roleGetChilds, role );
		return roles;
	}

	public Integer getMaxId() throws ApplicationException
	{
		return nextIntegerId( Role.roleMaxId );
	}

	public List<Task> getTasks( Integer key ) throws ApplicationException
	{
		Role role = get( key );
		if ( role == null )
			return Collections.emptyList();
		List<Task> tasks = new ArrayList<Task>();
		return getTasks( role, tasks );
	}


	protected List<Task> getTasks( Role role, List<Task> tasks ) throws ApplicationException
	{
		if ( role == null )
			return Collections.emptyList();
		List<PermissionAssignment> permissions;
		permissions = permissionSession.getAll( role );
		if ( SysUtils.isEmpty( permissions ) == false ) {
			for ( PermissionAssignment permission : permissions ) {
				if ( tasks.contains( permission.getTask() ) == false )
					tasks.add( permission.getTask() );
			}
		}
		for ( Role item : role.getChildRoles() )
			getTasks( item, tasks );
		return tasks;
	}
}
