package br.com.mcampos.util.locator;

public class ServiceLocatorException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4448210270552184875L;

	public ServiceLocatorException( Exception e )
	{
		super( e.getMessage( ) );
	}
}
