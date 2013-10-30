package br.com.mcampos.sysutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SysProperties {
	private static SysProperties instance;
	private final Properties properties;
	private static final Logger LOGGER = LoggerFactory.getLogger( SysProperties.class.getSimpleName( ) );


	private static final String PROPERTY_FILENAME = "system.properties";



	private SysProperties (  String propertyFilename )
	{
		InputStream is = null;

		properties = new Properties ();
		is = getClass().getClassLoader().getResourceAsStream(propertyFilename);
		if ( is != null ) {
			try {
				properties.load(is);
				LOGGER.info( "Properties loaded");
			} catch (IOException e) {
				LOGGER.error( "Error loading system properties");
			}
		}
	}


	public static SysProperties getInstance ()
	{
		if ( instance == null ) {
			instance = new SysProperties(PROPERTY_FILENAME);
		}
		return instance;
	}

	public String getProperty ( String name )
	{
		LOGGER.info( "Looking for property " + name );
		return properties.getProperty( name );
	}

	public String getProperty( String name, String defaultValue )
	{
		LOGGER.info( "Looking for property " + name + " with default value " + defaultValue );
		return properties.getProperty( name, defaultValue );
	}

}
