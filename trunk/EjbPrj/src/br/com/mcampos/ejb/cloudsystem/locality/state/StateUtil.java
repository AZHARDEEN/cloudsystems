package br.com.mcampos.ejb.cloudsystem.locality.state;


import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.State;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StateUtil
{
    public StateUtil()
    {
        super();
    }

    public static State createEntity( StateDTO dto )
    {
        if ( dto == null )
            return null;

        State entity = new State();
        entity.setId( dto.getId() );
        return update( entity, dto );
    }

    public static State update( State entity, StateDTO dto )
    {
        if ( dto == null )
            return null;
        DTOFactory.copy( entity );
        entity.setDescription( dto.getDescription() );
        return entity;
    }

    public static StateDTO copy( State entity )
    {
        if ( entity == null )
            return null;
        StateDTO dto = new StateDTO();
        dto.setId( entity.getId() );
        dto.setDescription( entity.getDescription() );
        dto.setAbbreviation( entity.getAbbreviation() );
        return dto;
    }

    public static List<StateDTO> toDTOList( List<State> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<StateDTO> listDTO = new ArrayList<StateDTO>( list.size() );
        for ( State m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }
}
