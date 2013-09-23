package br.com.mcampos.ejb.cloudsystem.security.role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignment;
import br.com.mcampos.ejb.cloudsystem.security.permissionassignment.PermissionAssignmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.sysutils.SysUtils;

@Stateless( name = "RoleSession", mappedName = "RoleSession" )
@TransactionAttribute( TransactionAttributeType.SUPPORTS )
@SuppressWarnings( "unchecked" )
public class RoleSessionBean extends Crud<Integer, Role> implements RoleSessionLocal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5882383243902530766L;
	@EJB
	PermissionAssignmentSessionLocal permissionSession;

	public RoleSessionBean( )
	{
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public void delete( Integer key ) throws ApplicationException
	{
		if ( SysUtils.isZero( key ) )
			return;
		Role role = get( key );
		if ( role != null ) {
			Role parent;

			parent = role.getParentRole( );
			if ( parent != null )
				parent.removeRole( role );
			getEntityManager( ).remove( role );
		}
	}

	@Override
	public Role get( Integer key ) throws ApplicationException
	{
		if ( SysUtils.isZero( key ) )
			return null;
		Role role = super.get( Role.class, key );
		return role;
	}

	@Override
	public List<Role> getAll( ) throws ApplicationException
	{
		return (List<Role>) getResultList( Role.roleGetAll );
	}

	@Override
	public Role getRootRole( ) throws ApplicationException
	{
		Role role;

		role = (Role) getSingleResult( Role.roleGetRoot );
		if ( role.getChildRoles( ) != null ) {
			int childs = role.getChildRoles( ).size( );
			childs = 0;
		}
		return role;
	}

	@Override
	public List<Role> getChildRoles( Role role ) throws ApplicationException
	{
		List<Role> roles = (List<Role>) getResultList( Role.roleGetChilds, role );
		return roles;
	}

	@Override
	public Integer getMaxId( ) throws ApplicationException
	{
		return nextIntegerId( Role.roleMaxId );
	}

	@Override
	public List<Task> getTasks( Integer key ) throws ApplicationException
	{
		Role role = get( key );
		if ( role == null )
			return Collections.emptyList( );
		List<Task> tasks = new ArrayList<Task>( );
		return getTasks( role, tasks );
	}

	protected List<Task> getTasks( Role role, List<Task> tasks ) throws ApplicationException
	{
		if ( role == null )
			return Collections.emptyList( );
		List<PermissionAssignment> permissions;
		permissions = permissionSession.getAll( role );
		if ( SysUtils.isEmpty( permissions ) == false ) {
			for ( PermissionAssignment permission : permissions ) {
				if ( tasks.contains( permission.getTask( ) ) == false )
					tasks.add( permission.getTask( ) );
			}
		}
		for ( Role item : role.getChildRoles( ) )
			getTasks( item, tasks );
		return tasks;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public Role add( Role entity ) throws ApplicationException
	{
		Role parent = null;

		if ( entity.getParentRole( ) != null ) {
			parent = get( entity.getParentRole( ).getId( ) );
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
		Role parent = role.getParentRole( );
		if ( parent != null ) {
			refresh( parent );
		}
	}

	@Override
	public List<Role> getDefaultRoles( ) throws ApplicationException
	{
		return (List<Role>) getResultList( Role.roleDefaults );
	}
}
