package br.com.mcampos.util.locator;


import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.ejb.EJBObject;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ServiceLocator
{
    private static ServiceLocator myServiceLocator;
    private InitialContext context = null;
    private Map<String, EJBObject> cache;


    private ServiceLocator() throws ServiceLocatorException
    {
        try {
            Hashtable env = new Hashtable();
            // WebLogic Server 10.x connection details
            env.put( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory" );
            env.put( Context.PROVIDER_URL, "t3://127.0.0.1:7101" );
            context = new InitialContext( env );
            this.cache = Collections.synchronizedMap( new HashMap<String, EJBObject>() );
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

    /*
    public static EJBObject getService( String id ) throws ServiceLocatorException
    {
        if ( id == null )
            return null;
        try {
            byte[] bytes = new String( id ).getBytes();
            InputStream io = new ByteArrayInputStream( bytes );
            ObjectInputStream os = new ObjectInputStream( io );


            javax.ejb.Handle handle = ( javax.ejb.Handle )os.readObject();
            return handle.getEJBObject();
        }
        catch ( Exception e ) {
            throw new ServiceLocatorException( e );
        }
    }
    */


    /*
    public static String getId( EJBObject resourceObject ) throws ServiceLocatorException
    {
        try {
            javax.ejb.Handle handle = resourceObject.getHandle();
            ByteArrayOutputStream fo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream( fo );

            so.writeObject( handle );
            so.flush();
            so.close();
            return new String( fo.toByteArray() );
        }
        catch ( RemoteException re ) {
            throw new ServiceLocatorException( re );
        }
        catch ( IOException ioe ) {
            throw new ServiceLocatorException( ioe );
        }
    }
    */

    public EJBObject getHome( String name ) throws ServiceLocatorException
    {
        EJBObject home = null;

        if ( cache.containsKey( name ) ) {
            home = cache.get( name );
        }
        else {
            try {
                home = ( EJBObject )context.lookup( name );
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
}
