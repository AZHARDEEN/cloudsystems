package br.com.mcampos.ejb.inep.task;


import br.com.mcampos.inep.dto.InepTaskDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InepTaskUtil
{
    public InepTaskUtil()
    {
        super();
    }

    public static InepTask createEntity( InepTaskDTO dto )
    {
        if ( dto == null )
            return null;

        InepTask entity = new InepTask( dto.getId() );
        return update( entity, dto );
    }

    public static InepTask update( InepTask entity, InepTaskDTO dto )
    {
        if ( dto == null )
            return null;
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static InepTaskDTO copy( InepTask entity )
    {
        if ( entity == null )
            return null;
        InepTaskDTO dto = new InepTaskDTO( entity.getId(), entity.getDescription() );
        return dto;
    }

    public static List<InepTaskDTO> toDTOList( List<InepTask> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<InepTaskDTO> listDTO = new ArrayList<InepTaskDTO>( list.size() );
        for ( InepTask m : list )
        {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
