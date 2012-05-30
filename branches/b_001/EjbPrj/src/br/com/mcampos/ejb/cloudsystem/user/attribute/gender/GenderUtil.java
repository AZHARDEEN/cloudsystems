package br.com.mcampos.ejb.cloudsystem.user.attribute.gender;


import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.TitleUtil;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenderUtil
{
    public GenderUtil()
    {
        super();
    }

    public static Gender createEntity( GenderDTO dto )
    {
        if ( dto == null )
            return null;

        Gender entity = new Gender( dto.getId() );
        return update( entity, dto );
    }

    public static Gender update( Gender entity, GenderDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static GenderDTO copy( Gender entity )
    {
        if ( entity == null )
            return null;
        GenderDTO dto = new GenderDTO( entity.getId(), entity.getDescription() );
        dto.setTitles( ( ArrayList<TitleDTO> )TitleUtil.toDTOList( entity.getTitles() ) );
        return dto;
    }

    public static List<GenderDTO> toDTOList( List<Gender> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<GenderDTO> listDTO = new ArrayList<GenderDTO>( list.size() );
        for ( Gender m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
