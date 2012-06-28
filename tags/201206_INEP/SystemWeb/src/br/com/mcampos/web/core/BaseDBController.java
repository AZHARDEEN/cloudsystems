package br.com.mcampos.web.core;

import javax.naming.NamingException;

import org.zkoss.zul.Window;

import br.com.mcampos.web.core.session.BeanSessonInterface;
import br.com.mcampos.web.locator.ServiceLocator;

public abstract class BaseDBController<BEAN> extends BaseController<Window> implements BeanSessonInterface<BEAN>
{
	private static final long serialVersionUID = -2456900330944095085L;
	private Class<BEAN> persistentClass;
	private transient BEAN session = null;

	protected abstract Class<BEAN> getSessionClass( );

	public BaseDBController( )
	{
		super( );
		setPersistentClass( );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public BEAN getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = (BEAN) ServiceLocator.getInstance( ).getRemoteSession( this.persistentClass );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

	protected void setPersistentClass( )
	{
		this.persistentClass = getSessionClass( );
	}
}
