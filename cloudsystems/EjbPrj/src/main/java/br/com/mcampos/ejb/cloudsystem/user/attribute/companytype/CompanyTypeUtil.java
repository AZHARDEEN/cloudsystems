package br.com.mcampos.ejb.cloudsystem.user.attribute.companytype;


import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompanyTypeUtil
{
    public CompanyTypeUtil()
    {
        super();
    }

    public static CompanyType createEntity( CompanyTypeDTO dto )
    {
        if ( dto == null )
            return null;

        CompanyType entity = new CompanyType( dto.getId() );
        return update( entity, dto );
    }

    public static CompanyType update( CompanyType entity, CompanyTypeDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static CompanyTypeDTO copy( CompanyType entity )
    {
        if ( entity != null )
            return new CompanyTypeDTO( entity.getId(), entity.getDescription() );
        else
            return null;
    }

    public static List<CompanyTypeDTO> toDTOList( List<CompanyType> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<CompanyTypeDTO> listDTO = new ArrayList<CompanyTypeDTO>( list.size() );
        for ( CompanyType m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
