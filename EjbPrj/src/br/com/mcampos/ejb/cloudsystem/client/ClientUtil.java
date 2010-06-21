package br.com.mcampos.ejb.cloudsystem.client;


import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ClientUtil
{
    public ClientUtil()
    {
        super();
    }


    public static List<ListUserDTO> toUserDTOList( List<Client> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<ListUserDTO> listDTO = new ArrayList<ListUserDTO>( list.size() );
        for ( Client m : list ) {
            listDTO.add( UserUtil.copy( m.getClient() ) );
        }
        return listDTO;
    }


    public static List<ClientDTO> toDTOList( List<Client> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<ClientDTO> listDTO = new ArrayList<ClientDTO>( list.size() );
        for ( Client m : list ) {
            listDTO.add( copy( m ) );
        }
        return listDTO;
    }


    public static ClientDTO copy( Client client )
    {
        ClientDTO dto = new ClientDTO();
        dto.setClient( UserUtil.copy( client.getClient() ) );
        dto.setClientId( client.getClientId() );
        dto.setCompanyId( client.getCompanyId() );
        dto.setInsertDate( client.getInsertDate() );
        return dto;
    }
}
