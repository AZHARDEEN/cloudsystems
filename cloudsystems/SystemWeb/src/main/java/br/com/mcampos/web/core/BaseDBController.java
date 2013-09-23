package br.com.mcampos.web.core;

import java.util.Map;

import javax.naming.NamingException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.web.core.session.BeanSessonInterface;

public abstract class BaseDBController<BEAN extends BaseSessionInterface> extends BaseController<Window> implements BeanSessonInterface<BEAN>
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
			if ( this.session == null )
				this.session = (BEAN) ServiceLocator.getInstance( ).getRemoteSession( this.persistentClass, ServiceLocator.EJB_NAME[ 0 ] );
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

	@Override
	protected Component createComponents( String uri, Component parent, Map<?, ?> parameters )
	{
		return super.createComponents( uri, parent, parameters );
	}
}
