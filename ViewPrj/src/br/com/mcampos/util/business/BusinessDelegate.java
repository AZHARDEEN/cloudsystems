package br.com.mcampos.util.business;


import br.com.mcampos.util.locator.ServiceLocator;

import java.io.Serializable;

public class BusinessDelegate implements Serializable
{
    protected static final String ejbPrefix = "CloudSystems-EjbPrj-";

    public BusinessDelegate()
    {
    }

    public Object getEJB( String sName )
    {
        try {
            ServiceLocator svc = ServiceLocator.getInstance();
            return svc.getHome( sName );
        }
        catch ( Exception e ) {
            return null;
        }
    }

    public Object getEJBSession( Class cls )
    {
        return getEJB( makeEJBSessionNameLocator( cls ) );
    }

    public String makeEJBSessionNameLocator( Class cls )
    {
        String name;
        int firstChar;

        /*get class name without package name, if any!!*/
        name = cls.getName();
        firstChar = name.lastIndexOf( '.' ) + 1;
        if ( firstChar > 0 )
            name = name.substring( firstChar );
        name = ejbPrefix + name + "#" + cls.getName();
        return name;
    }

}
