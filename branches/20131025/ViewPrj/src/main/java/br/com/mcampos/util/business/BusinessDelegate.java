package br.com.mcampos.util.business;

import java.io.Serializable;

import javax.naming.NamingException;

import br.com.mcampos.sysutils.ServiceLocator;

public class BusinessDelegate implements Serializable
{
	protected static final String ejbPrefix = "";
	@SuppressWarnings( "compatibility:-5214889860329379697" )
	private static final long serialVersionUID = 6118738841638308021L;

	public BusinessDelegate( )
	{
	}

	public Object getEJBSession( Class<?> cls )
	{
		try {
			return ServiceLocator.getInstance( ).getRemoteSession( cls, ServiceLocator.EJB_NAME[ 1 ] );
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
			return null;
		}
	}
}
