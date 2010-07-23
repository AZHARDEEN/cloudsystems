package br.com.mcampos.ejb.cloudsystem.resale.dealer.type;


import br.com.mcampos.dto.resale.DealerTypeDTO;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.type.entity.DealerType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DealerTypeUtil
{
    public static DealerType createEntity( DealerTypeDTO dto )
    {
        if ( dto == null )
            return null;

        DealerType entity = new DealerType( dto.getId() );
        return update( entity, dto );
    }

    public static DealerType update( DealerType entity, DealerTypeDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static DealerTypeDTO copy( DealerType entity )
    {
        if ( entity == null )
            return null;

        DealerTypeDTO dto = new DealerTypeDTO( entity.getId(), entity.getDescription() );
        return dto;
    }

    public static List<DealerTypeDTO> toDTOList( List<DealerType> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<DealerTypeDTO> listDTO = new ArrayList<DealerTypeDTO>( list.size() );
        for ( DealerType m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
