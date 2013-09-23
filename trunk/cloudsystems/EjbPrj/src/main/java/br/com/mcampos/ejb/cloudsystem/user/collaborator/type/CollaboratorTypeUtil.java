package br.com.mcampos.ejb.cloudsystem.user.collaborator.type;


import br.com.mcampos.dto.user.collaborator.CollaboratorTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.entity.CollaboratorType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CollaboratorTypeUtil
{
    public CollaboratorTypeUtil()
    {
        super();
    }


    public static CollaboratorType createEntity( CollaboratorTypeDTO dto )
    {
        if ( dto == null )
            return null;

        CollaboratorType entity = new CollaboratorType( dto.getId() );
        return update( entity, dto );
    }

    public static CollaboratorType update( CollaboratorType entity, CollaboratorTypeDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        entity.setInheritRole( dto.getInheritRole() );
        return entity;
    }

    public static CollaboratorTypeDTO copy( CollaboratorType entity )
    {
        if ( entity == null )
            return null;
        CollaboratorTypeDTO dto = new CollaboratorTypeDTO( entity.getId(), entity.getDescription() );
        dto.setInheritRole( entity.getInheritRole() );
        return dto;
    }

    public static List<CollaboratorTypeDTO> toDTOList( List<CollaboratorType> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<CollaboratorTypeDTO> listDTO = new ArrayList<CollaboratorTypeDTO>( list.size() );
        for ( CollaboratorType m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
