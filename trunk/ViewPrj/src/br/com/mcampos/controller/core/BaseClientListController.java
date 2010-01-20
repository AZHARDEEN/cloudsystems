package br.com.mcampos.controller.core;


import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;

public class BaseClientListController extends GenericForwardComposer
{
    
    public BaseClientListController( char c )
    {
        super( c );
    }

    public BaseClientListController()
    {
        super();
    }


    public Object getLoaderLocator()
    {
        /*
        if ( this.loaderLocator == null )
            this.loaderLocator = new RegisterLocator ();
        return loaderLocator;
        */
        return null;
    }

    
}
