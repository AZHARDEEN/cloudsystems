package org.zkoss.poc.gazer.ctrl;

import org.zkoss.poc.gazer.data.EventInfo;
import org.zkoss.poc.gazer.data.ReqDataCarrier;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.EventInterceptor;

public class AuInfoInterceptor implements EventInterceptor
{

	public ReqDataCarrier getDataCarrier( )
	{
		ReqDataCarrier dataCarrier =
				(ReqDataCarrier) Executions.getCurrent( ).getAttribute( ReqDataCarrier.KEY_REQ_DATA_CARRIER );
		return dataCarrier;
	}

	@Override
	public Event beforeProcessEvent( Event event )
	{
		if ( PfmMonitor.isIndex( Executions.getCurrent( ).getDesktop( ).getRequestPath( ) ) ) {
			EventInfo eventInfo = createEventInfoInst( event );
			eventInfo.setStartProcTime( System.currentTimeMillis( ) );
			eventInfo.setReqPath( Executions.getCurrent( ).getDesktop( ).getRequestPath( ) );
		}

		return event;

	}

	private static final String KEY_EVENT_INFO = "KEY_EVENT_INFO";

	public EventInfo createEventInfoInst( Event event )
	{
		EventInfo eventInfo = new EventInfo( getDataCarrier( ).getExecId( ) );
		eventInfo.setEventName( event.getName( ) );
		eventInfo.setTargetCompName( event.getTarget( ).toString( ) );
		Executions.getCurrent( ).setAttribute( KEY_EVENT_INFO, eventInfo );
		return eventInfo;
	}

	@Override
	public void afterProcessEvent( Event event )
	{
		if ( PfmMonitor.isIndex( Executions.getCurrent( ).getDesktop( ).getRequestPath( ) ) ) {
			EventInfo eventInfo = getEventInfoInst( );
			eventInfo.setReqId( getDataCarrier( ).getReqId( ) );
			eventInfo.setAfterProcTime( System.currentTimeMillis( ) );
			getDataCarrier( ).addEventInfo( eventInfo );
		}

	}

	private EventInfo getEventInfoInst( )
	{
		return (EventInfo) Executions.getCurrent( ).getAttribute( KEY_EVENT_INFO );
	}

	@Override
	public Event beforeSendEvent( Event event )
	{
		return event;
	}

	@Override
	public Event beforePostEvent( Event event )
	{
		return event;
	}
}
