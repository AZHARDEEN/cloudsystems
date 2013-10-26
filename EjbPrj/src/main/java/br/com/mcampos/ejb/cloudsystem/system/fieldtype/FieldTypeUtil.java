package br.com.mcampos.ejb.cloudsystem.system.fieldtype;


import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FieldTypeUtil
{
    public FieldTypeUtil()
    {
        super();
    }

    public static FieldType createEntity( FieldTypeDTO dto )
    {
        FieldType target = new FieldType( dto.getId() );
        return update( target, dto );
    }


    public static FieldType update( FieldType target, FieldTypeDTO dto )
    {
        target.setDescription( dto.getDescription() );
        return target;
    }

    public static FieldTypeDTO copy( FieldType entity )
    {
        if ( entity == null )
            return null;
        FieldTypeDTO dto = new FieldTypeDTO( entity.getId(), entity.getDescription() );
        return dto;

    }

    public static List<FieldTypeDTO> toDTOList( List<FieldType> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<FieldTypeDTO> listDTO = new ArrayList<FieldTypeDTO>( list.size() );
        for ( FieldType m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
