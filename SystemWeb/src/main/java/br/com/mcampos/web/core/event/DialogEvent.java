package br.com.mcampos.web.core.event;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;

public class DialogEvent<OBJECT> extends Event
{

	private static final long serialVersionUID = 2780866550320019744L;
	public static final String eventName = "onCloseDialog";
	public static final String eventQueueName = "Dialog Queue";

	public DialogEvent( Component target, OBJECT data )
	{
		super( eventName, target, data );
	}
}
