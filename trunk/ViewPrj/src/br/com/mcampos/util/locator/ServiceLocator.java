package br.com.mcampos.util.locator;


import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ServiceLocator
{
    private static ServiceLocator myServiceLocator;
    private InitialContext context = null;
    private Map<String, Object> cache;
    protected static final String ejbPrefix = "CloudSystems-EjbPrj-";


    private ServiceLocator() throws ServiceLocatorException
    {
        try {
            Hashtable env = new Hashtable();
            // WebLogic Server 10.x connection details
            env.put( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory" );
            env.put( Context.PROVIDER_URL, "t3://127.0.0.1:7101" );
            context = new InitialContext( env );
            this.cache = Collections.synchronizedMap( new HashMap<String, Object>() );
        }
        catch ( NamingException ne ) {
            throw new ServiceLocatorException( ne );
        }
    }

    public static ServiceLocator getInstance() throws ServiceLocatorException
    {
        if ( myServiceLocator == null )
            myServiceLocator = new ServiceLocator();
        return myServiceLocator;
    }


    public Object getHome( String name ) throws ServiceLocatorException
    {
        Object home = null;

        if ( cache.containsKey( name ) ) {
            home = cache.get( name );
        }
        else {
            try {
                home = context.lookup( name );
                cache.put( name, home );
            }
            catch ( NamingException ex ) {
                throw new ServiceLocatorException( ex );
            }
        }
        return home;
    }

    public String getServiceLocatorInfo()
    {
        return "CloudSystemsServiceLocator";
    }

    protected String makeEJBSessionNameLocator( Class cls )
    {
        /*get class name without package name, if any!!*/
        return ejbPrefix + cls.getSimpleName() + "#" + cls.getName();
    }

    public Object getRemoteSession( Class cls ) throws ServiceLocatorException
    {
        return getHome( makeEJBSessionNameLocator( cls ) );
    }

}
