package br.com.mcampos.web.core.session;

import javax.naming.NamingException;

import br.com.mcampos.sysutils.ServiceLocator;

public abstract class BeanSession<BEAN> implements BeanSessonInterface<BEAN>
{
	private static final long serialVersionUID = 363255325590508435L;
	private Class<BEAN> persistentClass;
	private transient BEAN session = null;

	protected abstract Class<BEAN> getSessionClass( );

	public BeanSession( )
	{
		super( );
		setPersistentClass( );
	}

	private void setPersistentClass( )
	{
		this.persistentClass = getSessionClass( );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public BEAN getSession( )
	{
		try {
			if ( this.session == null ) {
				Object obj = ServiceLocator.getInstance( ).getRemoteSession( this.persistentClass, ServiceLocator.ejbName[ 0 ] );
				this.session = (BEAN) obj;
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
			this.session = null;
		}
		return this.session;
	}

}
