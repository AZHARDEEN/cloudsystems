package br.com.mcampos.controller.logged;


import br.com.mcampos.controller.core.LoggedBaseController;

import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.sysutils.SysUtils;

import br.com.mcampos.util.business.UsersLocator;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.api.Center;

public class PrivateIndexController extends LoggedBaseController
{
    protected Center mdiApplication;
    protected Menubar mainMenu;

    protected UsersLocator userLocator;

    public PrivateIndexController()
    {
        super();
    }

    public PrivateIndexController( char c )
    {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        List<MenuDTO> menus = getUserLocator().getRoles( getLoggedInUser() );
        for ( MenuDTO item : menus ) {
            addMenu( item, mainMenu );
        }
    }

    protected Component addMenu( MenuDTO item, Component parent )
    {

        if ( item.getSubMenu().size() > 0 ) {
            Menu menuItem;
            Menupopup menuBar;

            menuItem = new Menu( item.getDescription() );
            menuBar = new Menupopup();
            menuBar.setParent( menuItem );
            menuItem.appendChild( menuBar );
            menuItem.setParent( parent );
            parent.appendChild( menuItem );
            for ( MenuDTO submenu : item.getSubMenu() ) {
                addMenu( submenu, menuBar );
            }
            return menuBar;
        }
        else {
            Menuitem menuItem;

            menuItem = new Menuitem( item.getDescription() );
            menuItem.setId( "mnu" + item.getId() );
            menuItem.setAttribute( "menu_target_url", item.getTargetURL() );
            if ( item.getSeparatorBefore() )
                parent.appendChild( new Menuseparator() );
            parent.appendChild( menuItem );
            menuItem.setParent( parent );
            menuItem.setAutocheck( item.getAutocheck() );
            menuItem.setChecked( item.getChecked() );
            menuItem.setCheckmark( item.getCheckmark() );
            menuItem.setDisabled( item.getDisabled() );
            menuItem.addEventListener( Events.ON_CLICK, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        PrivateIndexController.this.onMenuClick( event );
                    }
                } );
            return parent;
        }
    }


    public void onBookmarkChanged( BookmarkEvent event )
    {
        String iID = event.getBookmark();
        if ( SysUtils.isEmpty( iID ) || iID.equals( "index" ) )
            mdiApplication.getChildren().clear();
    }

    public UsersLocator getUserLocator()
    {
        if ( userLocator == null )
            userLocator = new UsersLocator();
        return userLocator;
    }

    public void onMenuClick( Event evt ) throws Exception
    {
        Menuitem item;

        if ( evt.getTarget() instanceof Menuitem ) {
            item = ( Menuitem )evt.getTarget();
            String url = ( String )item.getAttribute( "menu_target_url" );

            if ( SysUtils.isEmpty( url ) == false )
                gotoPage( url, mdiApplication );
            evt.stopPropagation();
        }
    }
}
