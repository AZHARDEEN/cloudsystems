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

	public static final String[ ] EJB_NAME = new String[ ] { "SystemEJB", "EjbPrj" };

	private String appName;
	private String moduleName;

	private static final String JNDI_MODULE_NAME = "java:app/ModuleName";
	private static final String JNDI_APP_NAME = "java:global/AppName";

	private final Logger logger = LoggerFactory.getLogger( ServiceLocator.class.getSimpleName( ) );

	private ServiceLocator( ) throws NamingException
	{
		context = new InitialContext( );
		cache = Collections.synchronizedMap( new HashMap<String, Object>( ) );
		logger.info( "Singleton Service Locator is created" );
	}

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

	/*
		For stateless beans:
			ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>
	
		For stateful beans:
			ejb:<app-name>/<module-name>/<distinct-name>/<bean-name>!<fully-qualified-classname-of-the-remote-interface>?stateful
	 */
	protected String makeEJBSessionNameLocator( Class<?> cls, String ejbProjectName )
	{
		if ( cls != null ) {
			final String distinctName = "";
			final String beanName = cls.getSimpleName( );
			final String viewClassName = cls.getName( );
			String contextName;

			contextName = "ejb:" + getAppName( ) + "/" + ( SysUtils.isEmpty( ejbProjectName ) ? getModuleName( ) : ejbProjectName )
					+ "/" + distinctName + beanName + "!" + viewClassName;
			return contextName;
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
