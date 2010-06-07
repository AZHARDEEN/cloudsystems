package br.com.mcampos.ejb.cloudsystem.locality.region;


import br.com.mcampos.dto.address.RegionDTO;
import br.com.mcampos.ejb.cloudsystem.locality.country.CountryUtil;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.cloudsystem.locality.region.entity.Region;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegionUtil
{
    public RegionUtil()
    {
        super();
    }

    public static Region createEntity( RegionDTO dto )
    {
        return createEntity( CountryUtil.createEntity( dto.getCountry() ), dto );
    }

    public static Region createEntity( Country country, RegionDTO dto )
    {
        if ( dto == null || country == null )
            return null;

        Region entity = new Region( country, dto.getId() );
        return update( entity, dto );
    }

    public static Region update( Region entity, RegionDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        entity.setAbbreviation( dto.getAbbreviation() );
        return entity;
    }

    public static RegionDTO copy( Region entity )
    {
        if ( entity == null )
            return null;
        RegionDTO dto = new RegionDTO();
        dto.setId( entity.getId() );
        dto.setCountry( CountryUtil.copy( entity.getCountry() ) );
        dto.setAbbreviation( entity.getAbbreviation() );
        dto.setDescription( dto.getDescription() );
        return dto;
    }

    public static List<RegionDTO> toDTOList( List<Region> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<RegionDTO> listDTO = new ArrayList<RegionDTO>( list.size() );
        for ( Region m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
