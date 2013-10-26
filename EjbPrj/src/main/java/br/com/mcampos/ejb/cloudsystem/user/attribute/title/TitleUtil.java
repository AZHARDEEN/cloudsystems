package br.com.mcampos.ejb.cloudsystem.user.attribute.title;


import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.entity.Title;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TitleUtil
{
    public TitleUtil()
    {
        super();
    }

    public static Title createEntity( TitleDTO dto )
    {
        if ( dto == null )
            return null;

        Title entity = new Title( dto.getId() );
        return update( entity, dto );
    }

    public static Title update( Title entity, TitleDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        entity.setAbbreviation( dto.getAbbreviation() );
        return entity;
    }

    public static TitleDTO copy( Title entity )
    {
        if ( entity == null )
            return null;
        TitleDTO dto = new TitleDTO( entity.getId(), entity.getDescription(), entity.getAbbreviation() );
        return dto;
    }

    public static List<TitleDTO> toDTOList( List<Title> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<TitleDTO> listDTO = new ArrayList<TitleDTO>( list.size() );
        for ( Title m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
