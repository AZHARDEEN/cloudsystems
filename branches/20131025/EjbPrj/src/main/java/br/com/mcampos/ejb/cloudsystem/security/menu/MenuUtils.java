package br.com.mcampos.ejb.cloudsystem.security.menu;


import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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


    public static List<Menu> getParents( List<Menu> menus )
    {
        List<Menu> allTasks = new ArrayList<Menu>();
        for ( Menu item : menus ) {
            getParents( item.getParentMenu(), allTasks );
        }
        menus.addAll( allTasks );
        return menus;
    }

    public static List<Menu> getParents( Menu parentMenu, List<Menu> allMenus )
    {
        if ( parentMenu == null )
            return allMenus;
        Menu parent = parentMenu.getParentMenu();
        if ( parent != null )
            getParents( parent, allMenus );
        if ( allMenus.contains( parentMenu ) == false )
            allMenus.add( parentMenu );
        return allMenus;
    }


    public static List<MenuDTO> organizeAndCopy( List<Menu> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<MenuDTO> dtoList = new LinkedList<MenuDTO>();
        for ( Menu m : list ) {
            addMenu( dtoList, m );
        }
        return dtoList;
    }

    private static MenuDTO addMenu( List<MenuDTO> menuList, Menu newMenu )
    {
        MenuDTO dto, parentDTO = null;
        int nIndex;

        if ( newMenu.getParentMenu() != null ) {
            //this menu has a parent menu.
            parentDTO = copy( newMenu.getParentMenu(), false );
            nIndex = menuList.indexOf( parentDTO );
            if ( nIndex == -1 ) {
                //parent is not in list. Must Add.
                parentDTO = addMenu( menuList, newMenu.getParentMenu() );
            }
            else {
                parentDTO = ( MenuDTO )menuList.get( nIndex );
            }
            dto = copy( newMenu, false );
            return addMenu( parentDTO.getSubMenu(), dto );
        }
        else {
            dto = copy( newMenu, false );
            return addMenu( menuList, dto );
        }
    }

    private static MenuDTO addMenu( List<MenuDTO> menuList, MenuDTO dto )
    {
        int nIndex;

        nIndex = menuList.indexOf( dto );
        if ( nIndex == -1 ) {
            for ( MenuDTO sibling : ( List<MenuDTO> )menuList ) {
                if ( dto.getSequence() > sibling.getSequence() )
                    continue;
                nIndex = menuList.indexOf( sibling );
                break;
            }
            if ( nIndex == -1 )
                menuList.add( dto );
            else
                menuList.add( nIndex, dto );
            return dto;
        }
        else
            return ( MenuDTO )menuList.get( nIndex );
    }

}
