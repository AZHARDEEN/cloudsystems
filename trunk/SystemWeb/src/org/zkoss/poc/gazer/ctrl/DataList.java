package org.zkoss.poc.gazer.ctrl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.zkoss.poc.gazer.data.Analytics;
import org.zkoss.poc.gazer.data.EventInfo;
import org.zkoss.poc.gazer.data.ReqDataCarrier;

public class DataList
{
	public static ArrayList<ReqDataCarrier> reqList;
	public static ArrayList<EventInfo> eventList;
	public static ArrayList<Analytics> analyticsList;
	public static ArrayList<EventInfo> uniqueEventList;
	public static HashMap<String, EventInfo> uniqueEventMap;

	public static ArrayList<ReqDataCarrier> getSortedReqList( String reqPath )
	{
		Map<String, ReqDataCarrier> map = PfmMonitor.getRecord( reqPath );
		reqList = new ArrayList<ReqDataCarrier>( map.values( ) );

		Collections.sort( reqList, new Comparator<ReqDataCarrier>( )
		{
			@Override
			public int compare( ReqDataCarrier req0, ReqDataCarrier req1 )
			{
				return req0.getReqId( ).compareTo( req1.getReqId( ) );
			}
		} );
		return reqList;
	}

	public static ArrayList<EventInfo> getEventList( String reqPath )
	{
		eventList = new ArrayList<EventInfo>( );
		Iterator<ReqDataCarrier> reqDataItr = getSortedReqList( reqPath ).iterator( );
		while ( reqDataItr.hasNext( ) ) {
			ReqDataCarrier dataCarrier = reqDataItr.next( );
			Iterator<EventInfo> eventItr = dataCarrier.getEventRecord( ).iterator( );
			while ( eventItr.hasNext( ) ) {
				EventInfo eventInfo = eventItr.next( );
				eventList.add( eventInfo );
			}
		}
		return eventList;
	}

	public static ArrayList<Analytics> getAnalyticsList( String reqPath )
	{
		analyticsList = new ArrayList<Analytics>( );
		uniqueEventList = getUniqueEventInfoList( reqPath );
		for ( EventInfo e : uniqueEventList ) {
			analyticsList.add( createAnalytics( e ) );
		}
		return analyticsList;
	}

	public static ArrayList<EventInfo> getUniqueEventInfoList( String reqPath )
	{
		eventList = getEventList( reqPath );
		uniqueEventMap = new HashMap<String, EventInfo>( );
		for ( EventInfo e : eventList ) {
			String key = e.getTargetCompName( ) + e.getEventName( );
			if ( !( uniqueEventMap.containsKey( key ) ) ) {
				long sum = e.getEventProcTime( );
				e.setEventTimeSum( sum );
				e.setCount( 1 );
				uniqueEventMap.put( e.getTargetCompName( ) + e.getEventName( ), e );
			}
			else {
				EventInfo event = uniqueEventMap.get( key );
				long sum = e.getEventProcTime( ) + event.getEventTimeSum( );
				event.setEventTimeSum( sum );
				event.setCount( event.getCount( ) + 1 );
			}
		}
		uniqueEventList = new ArrayList<EventInfo>( uniqueEventMap.values( ) );
		return uniqueEventList;
	}

	public static Analytics createAnalytics( EventInfo event )
	{
		Analytics anyl = new Analytics( );
		anyl.setTarget( event.getTargetCompName( ) );
		anyl.setEvent( event.getEventName( ) );
		anyl.setTimeSum( event.getEventTimeSum( ) );
		anyl.setCount( event.getCount( ) );
		return anyl;
	}

}
