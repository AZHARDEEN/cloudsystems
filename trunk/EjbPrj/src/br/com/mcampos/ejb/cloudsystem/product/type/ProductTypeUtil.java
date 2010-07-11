package br.com.mcampos.ejb.cloudsystem.product.type;


import br.com.mcampos.dto.ProductTypeDTO;
import br.com.mcampos.ejb.cloudsystem.product.type.entity.ProductType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ProductTypeUtil
{
    public static ProductType createEntity( ProductTypeDTO dto )
    {
        if ( dto == null )
            return null;

        ProductType entity = new ProductType( dto.getId() );
        return update( entity, dto );
    }

    public static ProductType update( ProductType entity, ProductTypeDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        entity.setIsDefault( dto.getIsDefault() );
        return entity;
    }

    public static ProductTypeDTO copy( ProductType entity )
    {
        if ( entity == null )
            return null;

        ProductTypeDTO dto = new ProductTypeDTO( entity.getId(), entity.getDescription() );
        dto.setIsDefault( entity.getIsDefault() );
        return dto;
    }

    public static List<ProductTypeDTO> toDTOList( List<ProductType> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<ProductTypeDTO> listDTO = new ArrayList<ProductTypeDTO>( list.size() );
        for ( ProductType m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
