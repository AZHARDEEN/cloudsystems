package br.com.mcampos.ejb.cloudsystem.client;


import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
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
            listDTO.add( DTOFactory.copy( m.getClient() ) );
        }
        return listDTO;
    }
}
