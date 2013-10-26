package br.com.mcampos.ejb.cloudsystem.locality.country;


import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountryUtil
{
    public CountryUtil()
    {
        super();
    }

    public static Country createEntity( CountryDTO dto )
    {
        if ( dto == null )
            return null;

        Country entity = new Country();
        entity.setId( dto.getId() );
        return update( entity, dto );
    }

    public static Country update( Country entity, CountryDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setCode3( dto.getCode3() );
        entity.setNumericCode( dto.getNumericCode() );
        return entity;
    }

    public static CountryDTO copy( Country entity )
    {
        if ( entity == null )
            return null;
        CountryDTO dto = new CountryDTO();
        dto.setId( entity.getId() );
        dto.setCode3( entity.getCode3() );
        dto.setNumericCode( entity.getNumericCode() );
        return dto;
    }

    public static List<CountryDTO> toDTOList( List<Country> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<CountryDTO> listDTO = new ArrayList<CountryDTO>( list.size() );
        for ( Country m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
