package br.com.imstecnologia.ejb.sped.fiscal.cfop;


import br.com.imstecnologia.dto.sped.CfopDTO;
import br.com.imstecnologia.ejb.sped.fiscal.cfop.entity.Cfop;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public final class CfopUtil
{
    public static Cfop createEntity( CfopDTO dto )
    {
        if ( dto == null )
            return null;

        Cfop entity = new Cfop( dto.getId() );
        return update( entity, dto );
    }

    public static Cfop update( Cfop entity, CfopDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        entity.setFrom( new Date() );
        return entity;
    }

    public static CfopDTO copy( Cfop entity )
    {
        if ( entity == null )
            return null;

        CfopDTO dto = new CfopDTO();
        dto.setDescription( entity.getDescription() );
        dto.setId( entity.getId() );
        return dto;
    }

    public static List<CfopDTO> toDTOList( List<Cfop> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<CfopDTO> listDTO = new ArrayList<CfopDTO>( list.size() );
        for ( Cfop m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
