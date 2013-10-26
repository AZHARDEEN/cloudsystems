package br.com.mcampos.ejb.cloudsystem.system.systemuserproperty;


import br.com.mcampos.dto.system.SystemUserPropertyDTO;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.FieldTypeUtil;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.entity.SystemUserProperty;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SystemUserPropertyUtil
{
    public static SystemUserProperty createEntity( SystemUserPropertyDTO dto )
    {
        if ( dto == null )
            return null;

        SystemUserProperty entity = new SystemUserProperty( dto.getId() );
        return update( entity, dto );
    }

    public static SystemUserProperty update( SystemUserProperty entity, SystemUserPropertyDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        entity.setType( FieldTypeUtil.createEntity( dto.getType() ) );
        return entity;
    }

    public static SystemUserPropertyDTO copy( SystemUserProperty entity )
    {
        if ( entity == null )
            return null;
        SystemUserPropertyDTO dto = new SystemUserPropertyDTO( entity.getId(), entity.getDescription() );
        dto.setType( FieldTypeUtil.copy( entity.getType() ) );
        return dto;
    }

    public static List<SystemUserPropertyDTO> toDTOList( List<SystemUserProperty> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<SystemUserPropertyDTO> listDTO = new ArrayList<SystemUserPropertyDTO>( list.size() );
        for ( SystemUserProperty m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
