package br.com.mcampos.controller.core;


import br.com.mcampos.util.business.SimpleTableLoaderLocator;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;

public class BaseClientListController extends GenericForwardComposer
{
    private SimpleTableLoaderLocator simpleTableLoaderLocator = null;
    
    
    public BaseClientListController( char c )
    {
        super( c );
    }

    public BaseClientListController()
    {
        super();
    }


    public SimpleTableLoaderLocator getLocator()
    {
        if ( this.simpleTableLoaderLocator == null )
            this.simpleTableLoaderLocator = new SimpleTableLoaderLocator ();
        return simpleTableLoaderLocator;
    }

    
}
