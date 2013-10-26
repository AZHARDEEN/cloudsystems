package br.com.mcampos.ejb.cloudsystem.security.permissionassignment;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class PermissionAssignmentUtil implements Serializable
{
	public PermissionAssignmentUtil()
	{
		super();
	}

	public static List<Role> toRoleList( List<PermissionAssignment> list )
	{
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList();
		ArrayList<Role> listDTO = new ArrayList<Role>( list.size() );
		for ( PermissionAssignment m : list ) {
			listDTO.add( m.getRole() );
		}
		return listDTO;
	}

	public static List<Task> toTaskList( List<PermissionAssignment> list )
	{
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList();
		ArrayList<Task> listDTO = new ArrayList<Task>( list.size() );
		for ( PermissionAssignment m : list ) {
			listDTO.add( m.getTask() );
		}
		return listDTO;
	}
}
