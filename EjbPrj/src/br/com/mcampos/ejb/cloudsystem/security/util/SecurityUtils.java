package br.com.mcampos.ejb.cloudsystem.security.util;


import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.ejb.cloudsystem.security.taskmenu.TaskMenu;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.security.PermissionAssignment;
import br.com.mcampos.ejb.entity.security.Subtask;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SecurityUtils
{
    public SecurityUtils()
    {
        super();
    }


    public static List<TaskDTO> toTaskDTOListFromTaskMenu( List<TaskMenu> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>( list.size() );
        for ( TaskMenu m : list ) {
            listDTO.add( DTOFactory.copy( m.getTask() ) );
        }
        return listDTO;
    }


    public static List<RoleDTO> toRoleDTOListFromPermission( List<PermissionAssignment> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<RoleDTO> listDTO = new ArrayList<RoleDTO>( list.size() );
        for ( PermissionAssignment m : list ) {
            listDTO.add( m.getRole().toDTO() );
        }
        return listDTO;
    }


    public static List<TaskDTO> toTaskDTOListFromSubtask( List<Subtask> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<TaskDTO> listDTO = new ArrayList<TaskDTO>( list.size() );
        for ( Subtask m : list ) {
            listDTO.add( DTOFactory.copy( m.getSubTask() ) );
        }
        return listDTO;
    }
}
