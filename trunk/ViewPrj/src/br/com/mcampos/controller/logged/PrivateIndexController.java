package br.com.mcampos.controller.logged;


import br.com.mcampos.controller.IndexController;
import br.com.mcampos.controller.core.LoggedBaseController;

import br.com.mcampos.sysutils.SysUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zkex.zul.Center;

public class PrivateIndexController extends LoggedBaseController
{
    Center mdiApplication;
    Component divMenu;

    public PrivateIndexController()
    {
        super();
    }

    public PrivateIndexController( char c )
    {
        super( c );
    }
    
    @Override
    public void doAfterCompose( Component comp ) throws Exception {
        super.doAfterCompose( comp );
    }
    
    public void onClick$mnuChangePasswod ()
    {
        gotoPage ( "/private/change_password.zul", mdiApplication );
    }

    public void onClick$mnuMyRecord ()
    {
        Map map = new LinkedHashMap ();
        map.put( "who", "myself" );
        gotoPage ( "/private/user/person.zul", mdiApplication, map );
    }

    public void onClick$mnuChangeBusinessEntity ()
    {
        gotoPage ( "/private/change_business_entity.zul", mdiApplication );
    }

    public void onClick$mnuBusinessEntity ()
    {
        gotoPage ( "/private/user/business_entity_list.zul", mdiApplication );
    }

    public void onClick$mnuClientList ()
    {
        gotoPage ( "/private/admin/clients/list_clients.zul", mdiApplication );
    }

    public void onClick$mnuLogout ()
    {
        redirect ( "/logout.zul" );
    }


    
    public void onBookmarkChanged(BookmarkEvent event)
    {
        String pID = null;
        String iID = event.getBookmark();
        if ( SysUtils.isEmpty( iID ) || iID.equals( "index" ) )
            mdiApplication.getChildren().clear();
    }

}
