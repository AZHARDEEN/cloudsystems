package br.com.mcampos.controller;

import br.com.mcampos.controller.core.BaseController;

import br.com.mcampos.sysutils.SysUtils;

import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zul.Menu;

public class IndexController extends BaseController 
{
    public static final String mdiApplicationParamName = "mdiApplication";
    
    Center mdiApplication;
    
    public IndexController() {
        super();
    }

    public IndexController( char c ) {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception {
        super.doAfterCompose( comp );
        
    }
    
    public void onClick$loginMenuItem (  )
    {
        gotoPage ( "/login.zul", mdiApplication ); 
    }

    public void onClick$registerMenuItem ()
    {
        gotoPage ( "/register.zul", mdiApplication );
    }

    public void onClick$resentEmailMenuItem ()
    {
        gotoPage ( "/re_send_email.zul", mdiApplication );
    }

    public void onClick$validateEmailMenuItem ()
    {
        gotoPage ( "/validate_email.zul", mdiApplication );
    }
}
