package br.com.mcampos.ejb.cloudsystem.product;


import br.com.mcampos.dto.product.ProductDTO;
import br.com.mcampos.ejb.cloudsystem.product.entity.Product;
import br.com.mcampos.ejb.cloudsystem.product.type.ProductTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ProductUtil
{
    public static final ProductDTO copy( Product entity )
    {
        if ( entity == null )
            return null;

        ProductDTO dto = new ProductDTO();
        dto.setCode( entity.getCode() );
        dto.setId( entity.getId() );
        dto.setDescription( entity.getDescription() );
        dto.setName( entity.getName() );
        dto.setObs( entity.getObs() );
        dto.setType( ProductTypeUtil.copy( entity.getType() ) );
        dto.setVisible( entity.getVisible() );
        return dto;
    }


    public static final Product update( Product entity, ProductDTO dto )
    {
        if ( entity == null )
            return null;
        if ( dto == null )
            return entity;

        entity.setCode( dto.getCode() );
        entity.setDescription( dto.getDescription() );
        entity.setName( dto.getName() );
        entity.setObs( dto.getObs() );
        entity.setType( ProductTypeUtil.createEntity( dto.getType() ) );
        entity.setVisible( dto.getVisible() );
        return entity;
    }

    public static final Product createEntity( ProductDTO dto, Company company )
    {
        if ( dto == null )
            return null;
        Product entity = new Product( company );
        entity.setId( dto.getId() );
        return update( entity, dto );
    }

    public static List<ProductDTO> toDTOList( List<Product> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<ProductDTO> listDTO = new ArrayList<ProductDTO>( list.size() );
        for ( Product m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }


}
