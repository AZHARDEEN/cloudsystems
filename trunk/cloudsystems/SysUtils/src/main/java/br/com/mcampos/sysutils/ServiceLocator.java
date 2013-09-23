package br.com.mcampos.sysutils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ServiceLocator
{
	private static ServiceLocator myServiceLocator;
	private InitialContext context = null;
	private final Map<String, Object> cache;

	public static final String[ ] ejbName = new String[ ] { "SystemEJB", "EjbPrj" };

	// The app name is the application name of the deployed EJBs. This
	// is typically the ear name
	// without the .ear suffix. However, the application name could be
	// overridden in the application.xml of the
	// EJB deployment on the server.
	// Since we haven't deployed the application as a .ear, the app name
	// for us will be an empty string
	private String appName;
	// This is the module name of the deployed EJBs on the server. This
	// is typically the jar name of the
	// EJB deployment, without the .jar suffix, but can be overridden
	// via the ejb-jar.xml
	// In this example, we have deployed the EJBs in a
	// jboss-as-ejb-remote-app.jar, so the module name is
	// jboss-as-ejb-remote-app
	private String moduleName;

	private final String JNDI_MODULE_NAME = "java:app/ModuleName";
	private final String JNDI_APP_NAME = "java:global/AppName";

	private static final Logger logger = LoggerFactory.getLogger( ServiceLocator.class.getSimpleName( ) );

	private ServiceLocator( ) throws NamingException
	{
		// Properties props = new Properties( );
		// Hashtable<String, Object> props = new Hashtable<String, Object>( );
		// WebLogic Server 10.x connection details
		// env.put( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory" );
		// env.put( Context.PROVIDER_URL, "t3://127.0.0.1:7101" );
		// env.put( Context.INITIAL_CONTEXT_FACTORY,
		// "org.jboss.ejb.client.naming" );
		// env.put( Context.PROVIDER_URL, "127.0.0.1:4999" );
		// env.put( Context.URL_PKG_PREFIXES,
		// "org.jboss.ejb.client.naming:org.jboss.naming:org.jnp.interfaces" );

		// props.setProperty( Context.INITIAL_CONTEXT_FACTORY, "org.jboss.as.naming.InitialContextFactory" );
		// props.setProperty( Context.URL_PKG_PREFIXES, "org.jnp.interfaces:org.jboss.naming:org.jboss.ejb.client.naming" );
		// props.setProperty( Context.PROVIDER_URL, "127.0.0.1:4447" );
		// props.setProperty( "jboss.naming.client.ejb.context", "true" );
		// props.setProperty( Context.INITIAL_CONTEXT_FACTORY, "org.jboss.as.naming.InitialContextFactory" );
		// props.setProperty( Context.URL_PKG_PREFIXES, "org.jboss.as.naming.interfaces" );
		// props.setProperty( Context.PROVIDER_URL, "jnp://localhost:4447" );
		// setCredential( props );

		// context = new InitialContext( props );
		context = new InitialContext( );
		cache = Collections.synchronizedMap( new HashMap<String, Object>( ) );
		logger.info( "Singleton Service Locator is created" );
	}

	/*
	private void setCredential( Properties jndiProps )
	{
		// username
		jndiProps.setProperty( Context.SECURITY_PRINCIPAL, "user" );
		// password
		jndiProps.setProperty( Context.SECURITY_CREDENTIALS, "password" );
	}
	*/

	public static synchronized ServiceLocator getInstance( ) throws NamingException
	{
		if ( myServiceLocator == null ) {
			myServiceLocator = new ServiceLocator( );
		}
		return myServiceLocator;
	}

	private Object getHome( String name ) throws NamingException
	{
		Object home = null;

		if ( cache != null && cache.containsKey( name ) ) {
			home = cache.get( name );
		}
		else {
			logger.info( "Cache miss for ejb : " + name );
			home = context.lookup( name );
			if ( cache != null ) {
				cache.put( name, home );
			}
		}
		return home;
	}

	public String getServiceLocatorInfo( )
	{
		return "CloudSystemsServiceLocator";
	}

	protected String makeEJBSessionNameLocator( Class<?> cls, String ejbProjectName )
	{
		/* get class name without package name, if any!! */
		/*
		 * Mask to lookup -
		 * ejb:<app-name>/<module-name>/<distinct-name>/<bean-name
		 * >!<fully-qualified-classname-of-the-remote-interface>
		 * java:module/MenuFacade!br.com.mcampos.ejb.security.menu.MenuFacade
		 */
		if ( cls != null ) {
			// WEBLOGIC
			// String contextName = "java:app/SystemEJB/" + cls.getSimpleName( )
			// + "!" + cls.getCanonicalName( );

			// AS7 allows each deployment to have an (optional) distinct name.
			// We haven't specified a distinct name for
			// our EJB deployment, so this is an empty string
			final String distinctName = "";
			// The EJB name which by default is the simple class name of the
			// bean implementation class
			final String beanName = cls.getSimpleName( );
			// the remote view fully qualified class name
			final String viewClassName = cls.getName( );
			// let's build the remote ejb name to lookup
			String contextName;

			contextName = "ejb:" + getAppName( ) + "/" + ( SysUtils.isEmpty( ejbProjectName ) ? getModuleName( ) : ejbProjectName )
					+ "/" + distinctName + beanName + "!" + viewClassName;
			return contextName;

			/*
				For stateless beans:
					ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>
			
				For stateful beans:
					ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>?stateful
			 */
		}
		else {
			return null;
		}
	}

	public Object getRemoteSession( Class<?> cls, String ejbProjectName ) throws NamingException
	{
		Object obj = null;
		if ( cls != null ) {
			obj = getHome( makeEJBSessionNameLocator( cls, ejbProjectName ) );
			if ( obj != null ) {
				obj = PortableRemoteObject.narrow( obj, cls );
			}
			return obj;
		}
		else {
			return null;
		}
	}

	private String getAppName( )
	{
		if ( SysUtils.isEmpty( appName ) ) {
			try {
				appName = (String) getHome( JNDI_APP_NAME );
				logger.info( "APP NAME FOUND: " + appName );
			}
			catch ( NamingException e ) {
				logger.warn( "Failed to get module name: " + JNDI_APP_NAME, e );
				appName = "System";
			}
		}
		return appName;
	}

	private String getModuleName( )
	{
		if ( SysUtils.isEmpty( moduleName ) ) {
			try {
				moduleName = (String) getHome( JNDI_MODULE_NAME );
				logger.info( "MODULE NAME FOUND: " + appName );
			}
			catch ( NamingException e ) {
				logger.warn( "Failed to get module name: " + JNDI_MODULE_NAME, e );
				moduleName = "SystemEJB";
			}
		}
		return moduleName;
	}
}
