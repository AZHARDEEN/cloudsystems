package br.com.mcampos.controller.logged;


import br.com.mcampos.controller.core.BookmarkHelper;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.controller.core.PageBrowseHistory;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.security.menu.UserMenuFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

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
    protected static final String attrMenu = "dto";

    protected UserMenuFacade userLocator;

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
        AddBookmarkEventListener();
    }


    private void clear()
    {
        String initPage;
        try {
            initPage = getUserLocator().getInitialPage( getLoggedInUser() );
            if ( !SysUtils.isEmpty( initPage ) ) {
                gotoPage( initPage, mdiApplication );
            }
            else
                mdiApplication.getChildren().clear();
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }


    protected void AddBookmarkEventListener()
    {
        page.addEventListener( Events.ON_BOOKMARK_CHANGE, new EventListener()
            {
                public void onEvent( Event event ) throws Exception
                {
                    if ( event != null && event instanceof BookmarkEvent )
                        PrivateIndexController.this.onBookmarkChange( ( BookmarkEvent )event );
                }
            } );
    }

    protected void onBookmarkChange( BookmarkEvent evt )
    {
        BookmarkHelper history;
        String bookmark;
        PageBrowseHistory target = null;


        bookmark = evt.getBookmark().replaceAll( bookmarkId, "" );
        if ( SysUtils.isEmpty( bookmark ) )
            return;
        try {
            int index = Integer.parseInt( bookmark );
            history = getHistory();
            if ( index < 0 || index >= history.get().size() )
                return;
            target = history.get( index );
        }
        catch ( NumberFormatException e ) {
            e = null;
            return;
        }
        gotoPage( target );
        evt.stopPropagation();
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
            //menuItem.setId( "mnu" + item.getId() );
            if ( item.getSeparatorBefore() )
                parent.appendChild( new Menuseparator() );
            parent.appendChild( menuItem );
            menuItem.setParent( parent );
            menuItem.setAutocheck( item.getAutocheck() );
            menuItem.setChecked( item.getChecked() );
            menuItem.setCheckmark( item.getCheckmark() );
            menuItem.setDisabled( item.getDisabled() );
            menuItem.setAttribute( attrMenu, item );
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
            clear();
    }

    public UserMenuFacade getUserLocator()
    {
        if ( userLocator == null )
            userLocator = ( UserMenuFacade )getRemoteSession( UserMenuFacade.class );
        return userLocator;
    }

    public void onMenuClick( Event evt ) throws Exception
    {
        Menuitem item;

        if ( evt.getTarget() instanceof Menuitem ) {
            item = ( Menuitem )evt.getTarget();
            Object obj = item.getAttribute( attrMenu );

            if ( obj != null && obj instanceof MenuDTO ) {
                MenuDTO dto = ( MenuDTO )obj;
                if ( SysUtils.isEmpty( dto.getTargetURL() ) == false )
                    gotoPage( dto.getTargetURL(), mdiApplication );
                evt.stopPropagation();
            }
        }
    }

    public void onNotify( Event evt ) throws ApplicationException
    {
        if ( evt.getData() instanceof CompanyDTO ) {
            if ( mdiApplication != null && mdiApplication.getChildren() != null )
                clear();
            if ( mainMenu == null )
                return;
            if ( mainMenu.getChildren() != null )
                mainMenu.getChildren().clear();
            List<MenuDTO> menus = getUserLocator().getMenus( getLoggedInUser() );
            for ( MenuDTO item : menus ) {
                addMenu( item, mainMenu );
            }
            //mainMenu.invalidate();
        }
    }
}
