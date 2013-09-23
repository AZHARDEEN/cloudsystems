package br.com.mcampos.ejb.cloudsystem.locality.city;


import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.cloudsystem.locality.state.StateUtil;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CityUtil
{
    public CityUtil()
    {
        super();
    }

    public static City createEntity( CityDTO dto )
    {
        if ( dto == null )
            return null;

        City entity = new City();
        entity.setId( dto.getId() );
        return update( entity, dto );
    }

    public static City update( City entity, CityDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        entity.setCountryCapital( dto.getCountryCapital() );
        entity.setState( StateUtil.createEntity( dto.getState() ) );
        entity.setStateCapital( dto.getStateCapital() );
        return entity;
    }

    public static CityDTO copy( City entity )
    {
        if ( entity == null )
            return null;
        CityDTO dto = new CityDTO();
        dto.setId( entity.getId() );
        dto.setDescription( entity.getDescription() );
        dto.setCountryCapital( entity.isCountryCapital() );
        dto.setStateCapital( entity.isStateCapital() );
        dto.setState( StateUtil.copy( entity.getState() ) );
        return dto;
    }

    public static List<CityDTO> toDTOList( List<City> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<CityDTO> listDTO = new ArrayList<CityDTO>( list.size() );
        for ( City m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
