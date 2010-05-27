package br.com.mcampos.ejb.cloudsystem.security.taskmenu;


import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.security.menu.MenuUtils;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TaskMenuUtil
{
    public TaskMenuUtil()
    {
        super();
    }

    public static List<MenuDTO> toMenuDTOList( List<TaskMenu> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<MenuDTO> listDTO = new ArrayList<MenuDTO>( list.size() );
        for ( TaskMenu m : list ) {
            listDTO.add( MenuUtils.copy( m.getMenu(), false ) );
        }
        return listDTO;
    }

    public static List<TaskDTO> toTaskDTOList( List<TaskMenu> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>( list.size() );
        for ( TaskMenu m : list ) {
            listDTO.add( DTOFactory.copy( m.getTask() ) );
        }
        return listDTO;
    }

}
