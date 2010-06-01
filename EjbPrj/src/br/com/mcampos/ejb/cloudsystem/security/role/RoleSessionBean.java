package br.com.mcampos.ejb.cloudsystem.security.role;


import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentSessionLocal;
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
		Role role = get( key );
		if ( role != null ) {
			Role parent;

			parent = role.getParentRole();
			if ( parent != null )
				parent.removeRole( role );
			getEntityManager().remove( role );
		}
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
		Role role;

		role = ( Role )getSingleResult( Role.roleGetRoot );
		if ( role.getChildRoles() != null ) {
			int childs = role.getChildRoles().size();
			childs = 0;
		}
		return role;
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

	@Override
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public Role add( Role entity ) throws ApplicationException
	{
		Role parent = null;

		if ( entity.getParentRole() != null ) {
			parent = get( entity.getParentRole().getId() );
			if ( parent != null ) {
				entity.setParentRole( parent );
			}
		}
		Role added = super.add( entity );
		if ( parent != null )
			parent.addRole( added );
		return added;
	}

	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	private void refreshParent( Role role ) throws ApplicationException
	{
		if ( role == null )
			return;
		Role parent = role.getParentRole();
		if ( parent != null ) {
			refresh( parent );
		}
	}
}
