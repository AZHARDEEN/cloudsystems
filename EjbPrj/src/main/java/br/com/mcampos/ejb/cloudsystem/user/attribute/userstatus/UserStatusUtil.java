package br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus;


import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.entity.UserStatus;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UserStatusUtil
{
	public UserStatusUtil()
	{
		super();
	}

	public static UserStatus createEntity( UserStatusDTO dto )
	{
		if ( dto == null )
			return null;
		UserStatus entity = new UserStatus( dto.getId() );
		return update( entity, dto );
	}

	public static UserStatusDTO copy( UserStatus entity )
	{
		if ( entity == null )
			return null;

		UserStatusDTO dto = new UserStatusDTO( entity.getId(), entity.getDescription() );
		dto.setAllowLogin( entity.getAllowLogin() );
		return dto;
	}

	public static UserStatus update( UserStatus entity, UserStatusDTO dto )
	{
		if ( dto == null || entity == null )
			return null;

		entity.setDescription( dto.getDescription() );
		entity.setAllowLogin( dto.getAllowLogin() );
		return entity;
	}

	public static List<UserStatusDTO> toDTOList( List<UserStatus> list )
	{
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList();
		ArrayList<UserStatusDTO> listDTO = new ArrayList<UserStatusDTO>( list.size() );
		for ( UserStatus m : list ) {
			listDTO.add( copy( m ) );
		}
		return listDTO;
	}
}
