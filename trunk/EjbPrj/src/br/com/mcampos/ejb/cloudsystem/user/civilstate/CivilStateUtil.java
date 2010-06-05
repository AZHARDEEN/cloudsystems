package br.com.mcampos.ejb.cloudsystem.user.civilstate;


import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.ejb.cloudsystem.user.civilstate.entity.CivilState;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CivilStateUtil
{
	public CivilStateUtil()
	{
		super();
	}

	public static CivilState createEntity( CivilStateDTO dto )
	{
		if ( dto == null )
			return null;

		CivilState entity = new CivilState( dto.getId() );
		return update( entity, dto );
	}

	public static CivilState update( CivilState entity, CivilStateDTO dto )
	{
		if ( dto == null )
			return null;
		entity.setDescription( dto.getDescription() );
		return entity;
	}

	public static CivilStateDTO copy( CivilState entity )
	{
		if ( entity != null )
			return new CivilStateDTO( entity.getId(), entity.getDescription() );
		else
			return null;
	}

	public static List<CivilStateDTO> toDTOList( List<CivilState> list )
	{
		if ( SysUtils.isEmpty( list ) )
			return Collections.emptyList();
		ArrayList<CivilStateDTO> listDTO = new ArrayList<CivilStateDTO>( list.size() );
		for ( CivilState m : list ) {
			listDTO.add( copy( m ) );
		}
		return listDTO;
	}
}
