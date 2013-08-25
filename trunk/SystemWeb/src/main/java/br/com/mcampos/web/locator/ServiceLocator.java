package br.com.mcampos.web.locator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceLocator
{
	private static ServiceLocator myServiceLocator;
	private InitialContext context = null;
	private final Map<String, Object> cache;

	private static final Logger logger = LoggerFactory.getLogger( ServiceLocator.class.getSimpleName( ) );

	private ServiceLocator( ) throws NamingException
	{
		Hashtable<String, Object> env = new Hashtable<String, Object>( );
		// WebLogic Server 10.x connection details
		// env.put( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory" );
		// env.put( Context.PROVIDER_URL, "t3://127.0.0.1:7101" );
		// env.put( Context.INITIAL_CONTEXT_FACTORY,
		// "org.jboss.ejb.client.naming" );
		// env.put( Context.PROVIDER_URL, "127.0.0.1:4999" );
		// env.put( Context.URL_PKG_PREFIXES,
		// "org.jboss.ejb.client.naming:org.jboss.naming:org.jnp.interfaces" );

		env.put( Context.INITIAL_CONTEXT_FACTORY, "org.jboss.as.naming.InitialContextFactory" );
		env.put( Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming:org.jboss.naming:org.jnp.interfaces" );
		env.put( Context.PROVIDER_URL, "127.0.0.1:4447" );
		env.put( "jboss.naming.client.ejb.context", true );

		context = new InitialContext( env );
		cache = Collections.synchronizedMap( new HashMap<String, Object>( ) );
	}

	public static ServiceLocator getInstance( ) throws NamingException
	{
		if ( myServiceLocator == null ) {
			myServiceLocator = new ServiceLocator( );
		}
		return myServiceLocator;
	}

	public Object getHome( String name ) throws NamingException
	{
		Object home = null;

		if ( cache.containsKey( name ) ) {
			home = cache.get( name );
		}
		else {
			logger.info( "Looking up for ejb: " + name );
			home = context.lookup( name );
			cache.put( name, home );
		}
		return home;
	}

	public String getServiceLocatorInfo( )
	{
		return "CloudSystemsServiceLocator";
	}

	protected String makeEJBSessionNameLocator( Class<?> cls )
	{
		/* get class name without package name, if any!! */
		/*
		 * Mask to lookup -
		 * ejb:<app-name>/<module-name>/<distinct-name>/<bean-name
		 * >!<fully-qualified-classname-of-the-remote-interface>
		 * java:module/MenuFacade!br.com.mcampos.ejb.security.menu.MenuFacade
		 */
		if ( cls != null ) {
			// String contextName = "java:app/SystemEJB/" + cls.getSimpleName( )
			// + "!" + cls.getCanonicalName( );

			// The app name is the application name of the deployed EJBs. This
			// is typically the ear name
			// without the .ear suffix. However, the application name could be
			// overridden in the application.xml of the
			// EJB deployment on the server.
			// Since we haven't deployed the application as a .ear, the app name
			// for us will be an empty string
			final String appName = "System";
			// This is the module name of the deployed EJBs on the server. This
			// is typically the jar name of the
			// EJB deployment, without the .jar suffix, but can be overridden
			// via the ejb-jar.xml
			// In this example, we have deployed the EJBs in a
			// jboss-as-ejb-remote-app.jar, so the module name is
			// jboss-as-ejb-remote-app
			final String moduleName = "SystemEJB";
			// AS7 allows each deployment to have an (optional) distinct name.
			// We haven't specified a distinct name for
			// our EJB deployment, so this is an empty string
			final String distinctName = "";
			// The EJB name which by default is the simple class name of the
			// bean implementation class
			final String beanName = cls.getSimpleName( );
			// the remote view fully qualified class name
			final String viewClassName = cls.getName( );
			// let's do the lookup

			String contextName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + beanName + "!" + viewClassName;
			return contextName;
		}
		else {
			return null;
		}
	}

	public Object getRemoteSession( Class<?> cls ) throws NamingException
	{
		if ( cls != null ) {
			return getHome( makeEJBSessionNameLocator( cls ) );
		}
		else {
			return null;
		}
	}

}
