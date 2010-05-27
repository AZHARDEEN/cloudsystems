package br.com.mcampos.ejb.cloudsystem.security.menu;


import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MenuUtils
{
    public MenuUtils()
    {
        super();
    }

    public static List<MenuDTO> toMenuDTOList( List<Menu> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        ArrayList<MenuDTO> listDTO = new ArrayList<MenuDTO>( list.size() );
        for ( Menu m : list ) {
            listDTO.add( copy( m, true ) );
        }
        return listDTO;
    }

    public static MenuDTO copy( Menu source, Boolean copySubMenu )
    {
        if ( source == null )
            return null;

        MenuDTO target = new MenuDTO();

        target.setId( source.getId() );
        target.setDescription( source.getDescription() );
        target.setSequence( source.getSequence() );
        target.setTargetURL( source.getTargetURL() );
        target.setAutocheck( source.getAutocheck() );
        target.setChecked( source.getChecked() );
        target.setCheckmark( source.getCheckmark() );
        target.setDisabled( source.getDisabled() );
        target.setSeparatorBefore( source.getSeparatorBefore() );
        if ( source.getSubMenus() != null && ( source.getSubMenus().size() > 0 ) && copySubMenu ) {
            for ( Menu sm : source.getSubMenus() ) {
                target.addSubMenu( copy( sm, true ) );
            }
        }
        if ( source.getParentMenu() != null ) {
            target.setParent( copy( source.getParentMenu(), false ) );
            target.setParentId( source.getParentMenu().getId() );
        }
        return target;
    }

    public static Menu update( Menu target, MenuDTO source )
    {
        if ( source == null || target == null )
            return null;

        target.setDescription( source.getDescription() );
        target.setSequence( source.getSequence() );
        target.setTargetURL( source.getTargetURL() );
        target.setAutocheck( source.getAutocheck() );
        target.setChecked( source.getChecked() );
        target.setCheckmark( source.getCheckmark() );
        target.setDisabled( source.getDisabled() );
        target.setSeparatorBefore( source.getSeparatorBefore() );
        return target;
    }

    public static Menu createEntity( MenuDTO source )
    {
        if ( source == null )
            return null;
        Menu target = new Menu();

        target.setId( source.getId() );
        return update( target, source );
    }
}
