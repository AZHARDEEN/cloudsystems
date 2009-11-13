package br.com.mcampos.controller.core;

import br.com.mcampos.util.business.RegisterLocator;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;

public class BaseClientListController extends GenericForwardComposer
{
    protected RegisterLocator loaderLocator;
    
    public BaseClientListController( char c )
    {
        super( c );
    }

    public BaseClientListController()
    {
        super();
    }


    public void setLoaderLocator( RegisterLocator loaderLocator )
    {
        this.loaderLocator = loaderLocator;
    }

    public RegisterLocator getLoaderLocator()
    {
        if ( this.loaderLocator == null )
            this.loaderLocator = new RegisterLocator ();
        return loaderLocator;
    }

    
}
