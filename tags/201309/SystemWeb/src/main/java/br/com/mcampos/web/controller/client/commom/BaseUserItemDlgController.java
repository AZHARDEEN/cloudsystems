package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Window;

import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.core.event.DialogEvent;

public abstract class BaseUserItemDlgController<ENTITY> extends BaseController<Window>
{
	private static final long serialVersionUID = 4182165108640866996L;

	private ENTITY entity;

	private Window targetWindow;
	private Integer nIndex;
	private Boolean isUpdate;

	protected abstract boolean validate( ENTITY data );

	protected abstract void update( ENTITY data );

	protected abstract ENTITY createEntity( );

	protected abstract void showRecord( ENTITY data );

	@Listen( "onClick=#cmdCancel" )
	public void onCancel( MouseEvent evt )
	{
		getMainWindow( ).detach( );
		if( evt != null )
			evt.stopPropagation( );
	}

	@Listen( "onClick=#cmdSave" )
	public void onOk( MouseEvent evt )
	{
		update( getEntity( ) );
		if( validate( getEntity( ) ) ) {
			EventQueues.lookup( DialogEvent.eventName, EventQueues.DESKTOP, true ).publish(
					new DialogEvent<ENTITY>( targetWindow, getEntity( ), nIndex, isUpdate )
					);
			onCancel( evt );
		}
		if( evt != null )
			evt.stopPropagation( );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		loadParams( );
	}

	private final void loadParams( )
	{
		targetWindow = (Window) getParameter( "targetWindow" );
		nIndex = (int) getParameter( "listIndex" );
		isUpdate = (boolean) getParameter( "isUpdate" );
		getEntity( );

	}

	@SuppressWarnings( "unchecked" )
	protected final ENTITY getEntity( )
	{
		if( entity == null ) {
			entity = (ENTITY) getParameter( "entity" );
			if( entity == null )
				entity = createEntity( );
		}
		return entity;
	}
}
