package br.com.mcampos.web.core.event;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;

public class DialogEvent<OBJECT> extends Event
{

	private static final long serialVersionUID = 2780866550320019744L;
	public static final String eventName = "onCloseDialog";
	public static final String eventQueueName = "Dialog Queue";

	private int listIndex;

	private boolean isUpdate;

	public DialogEvent( Component target, OBJECT data, int listIndex, boolean isUpdate )
	{
		super( eventName, target, data );
		setListIndex( listIndex );
		setUpdate( isUpdate );

	}

	public int getListIndex( )
	{
		return listIndex;
	}

	private void setListIndex( int listIndex )
	{
		this.listIndex = listIndex;
	}

	public boolean isUpdate( )
	{
		return isUpdate;
	}

	private void setUpdate( boolean isUpdate )
	{
		this.isUpdate = isUpdate;
	}
}
