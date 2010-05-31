package br.com.mcampos.ejb.cloudsystem.user.collaborator.role;


import br.com.mcampos.ejb.cloudsystem.security.role.Role;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CollaboratorRoleUtil
{
	public CollaboratorRoleUtil()
	{
		super();
	}

	public static List<Role> toRoleList( List<CollaboratorRole> list )
	{
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList();
		ArrayList<Role> listDTO = new ArrayList<Role>( list.size() );
		for ( CollaboratorRole m : list ) {
			listDTO.add( m.getRole() );
		}
		return listDTO;
	}
}
