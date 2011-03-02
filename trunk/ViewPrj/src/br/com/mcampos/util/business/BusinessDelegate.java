package br.com.mcampos.util.business;


import br.com.mcampos.util.locator.ServiceLocator;

import java.io.Serializable;

public class BusinessDelegate implements Serializable
{
    protected static final String ejbPrefix = "CloudSystems-EjbPrj-";
    @SuppressWarnings( "compatibility:-5214889860329379697" )
    private static final long serialVersionUID = 6118738841638308021L;

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
        return ejbPrefix + cls.getSimpleName() + "#" + cls.getName();
    }

}
