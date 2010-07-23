package br.com.mcampos.ejb.cloudsystem.resale;


import br.com.mcampos.dto.resale.ResaleDTO;
import br.com.mcampos.ejb.cloudsystem.client.ClientUtil;
import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ResaleUtil
{
    public static Resale createEntity( Company company, ResaleDTO dto )
    {
        if ( dto == null )
            return null;

        Resale entity = new Resale( company );
        return update( entity, dto );
    }

    public static Resale update( Resale entity, ResaleDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setCode( dto.getCode() );
        return entity;
    }

    public static ResaleDTO copy( Resale entity )
    {
        if ( entity == null )
            return null;
        ResaleDTO dto = new ResaleDTO();
        dto.setCode( entity.getCode() );
        dto.setResale( ClientUtil.copy( entity.getResale() ) );
        dto.setSequence( entity.getSequence() );
        return dto;
    }

    public static List<ResaleDTO> toDTOList( List<Resale> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<ResaleDTO> listDTO = new ArrayList<ResaleDTO>( list.size() );
        for ( Resale m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }

}
