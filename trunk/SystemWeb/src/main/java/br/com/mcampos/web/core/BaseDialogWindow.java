package br.com.mcampos.web.core;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Window;

import br.com.mcampos.web.core.event.IDialogEvent;

public abstract class BaseDialogWindow extends Window
{
	private static final long serialVersionUID = 8192456109740103585L;

	private IDialogEvent callEvent;

	protected abstract boolean validate( );

	public IDialogEvent getCallEvent( )
	{
		return callEvent;
	}

	public void setCallEvent( IDialogEvent callEvent )
	{
		this.callEvent = callEvent;
	}

	public void onCancel( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		onClose( );
	}

	protected void pushSessionParameter( String name, Object value )
	{
		Sessions.getCurrent( ).setAttribute( name, value );
	}

	protected Object popSessionParameter( String name )
	{
		Object obj = ( Sessions.getCurrent( ) != null ) ? Sessions.getCurrent( ).getAttribute( name ) : null;
		Sessions.getCurrent( ).setAttribute( name, null );
		return obj;
	}

	public void onOK( Event evt )
	{
		if ( validate( ) ) {
			if ( getCallEvent( ) != null )
				getCallEvent( ).onOK( this );
			onCancel( evt );
		}
		if ( evt != null )
			evt.stopPropagation( );
	}
}
