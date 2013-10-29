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
		this.context = new InitialContext( );
		this.cache = Collections.synchronizedMap( new HashMap<String, Object>( ) );
		this.logger.info( "Singleton Service Locator is created" );
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

		if ( this.cache != null && this.cache.containsKey( name ) ) {
			home = this.cache.get( name );
		}
		else {
			this.logger.info( "Cache miss for ejb : " + name );
			home = this.context.lookup( name );
			if ( this.cache != null ) {
				this.cache.put( name, home );
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

			contextName = "ejb:" + this.getAppName( ) + "/" + ( SysUtils.isEmpty( ejbProjectName ) ? this.getModuleName( ) : ejbProjectName )
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
			obj = this.getHome( this.makeEJBSessionNameLocator( cls, ejbProjectName ) );
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
		if ( SysUtils.isEmpty( this.appName ) ) {
			try {
				this.appName = (String) this.getHome( JNDI_APP_NAME );
				this.logger.info( "APP NAME FOUND: " + this.appName );
			}
			catch ( NamingException e ) {
				this.logger.warn( "Failed to get module name: " + JNDI_APP_NAME, e );
				this.appName = "System";
			}
		}
		return this.appName;
	}

	private String getModuleName( )
	{
		if ( SysUtils.isEmpty( this.moduleName ) ) {
			try {
				this.moduleName = (String) this.getHome( JNDI_MODULE_NAME );
				this.logger.info( "MODULE NAME FOUND: " + this.appName );
			}
			catch ( NamingException e ) {
				this.logger.warn( "Failed to get module name: " + JNDI_MODULE_NAME, e );
				this.moduleName = "SystemEJB";
			}
		}
		return this.moduleName;
	}
}
