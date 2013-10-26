package br.com.mcampos.ejb.cloudsystem.user.attribute.usertype;

import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserTypeUtil
{
	public UserTypeUtil()
	{
		super();
	}

	public static UserType createEntity( UserTypeDTO dto )
	{
		if ( dto == null )
			return null;

		UserType entity = new UserType( dto.getId() );
		return update( entity, dto );
	}

	public static UserType update( UserType entity, UserTypeDTO dto )
	{
		if ( dto == null )
			return null;
		entity.setDescription( dto.getDescription() );
		return entity;
	}

	public static UserTypeDTO copy( UserType entity )
	{
		if ( entity != null )
			return new UserTypeDTO( entity.getId(), entity.getDescription() );
		else
			return null;
	}

	public static List<UserTypeDTO> toDTOList( List<UserType> list )
	{
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList();
		ArrayList<UserTypeDTO> listDTO = new ArrayList<UserTypeDTO>( list.size() );
		for ( UserType m : list ) {
			listDTO.add( copy( m ) );
		}
		return listDTO;
	}
}
