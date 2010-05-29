package br.com.mcampos.ejb.cloudsystem.security.role;


import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RoleUtils
{
	public RoleUtils()
	{
		super();
	}

	public static List<RoleDTO> toRoleDTOList( List<Role> roles )
	{
		if ( SysUtils.isEmpty( roles ) )
			return Collections.emptyList();
		List<RoleDTO> dtos = new ArrayList<RoleDTO>( roles.size() );
		for ( Role role : roles )
			dtos.add( role.toDTO() );
		return dtos;
	}

	public static Role createEntity( RoleDTO source )
	{
		Role role = new Role();
		role.setId( source.getId() );
		return update( role, source );
	}

	public static Role update( Role role, RoleDTO source )
	{
		role.setDescription( source.getDescription() );
		if ( source.getParent() != null )
			role.setParentRole( createEntity( source.getParent() ) );
		return role;
	}
}
