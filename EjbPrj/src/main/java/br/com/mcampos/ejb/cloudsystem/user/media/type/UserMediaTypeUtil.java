package br.com.mcampos.ejb.cloudsystem.user.media.type;


import br.com.mcampos.dto.user.attributes.UserMediaTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.media.type.entity.UserMediaType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class UserMediaTypeUtil
{

    public static UserMediaType createEntity( UserMediaTypeDTO dto )
    {
        if ( dto == null )
            return null;

        UserMediaType entity = new UserMediaType( dto.getId() );
        return update( entity, dto );
    }

    public static UserMediaType update( UserMediaType entity, UserMediaTypeDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static UserMediaTypeDTO copy( UserMediaType entity )
    {
        if ( entity == null )
            return null;
        UserMediaTypeDTO dto = new UserMediaTypeDTO( entity.getId(), entity.getDescription() );
        return dto;
    }

    public static List<UserMediaTypeDTO> toDTOList( List<UserMediaType> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<UserMediaTypeDTO> listDTO = new ArrayList<UserMediaTypeDTO>( list.size() );
        for ( UserMediaType m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
