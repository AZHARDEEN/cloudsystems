package org.zkoss.poc.gazer.data;

import java.util.LinkedList;
import java.util.List;

public class ReqDataCarrier
{
	public static final String KEY_REQ_DATA_CARRIER = "KEY_REQ_DATA_CARRIER";
	String reqPath = null;
	String requestID = null;
	String execID = null;
	private long[ ] time = new long[ 5 ];

	public List<EventInfo> eventRecord = new LinkedList<EventInfo>( );

	public ReqDataCarrier( )
	{
	}

	public ReqDataCarrier( String requestId )
	{
		requestID = requestId;
	}

	public List<EventInfo> getEventRecord( )
	{
		return eventRecord;
	}

	public void addEventInfo( EventInfo info )
	{
		eventRecord.add( info );
	}

	public String getExecId( )
	{
		return execID;
	}

	public void setExecId( String execID )
	{
		this.execID = execID;
	}

	public String getReqId( )
	{
		return requestID;
	}

	public void setReqPath( String reqPath )
	{
		this.reqPath = reqPath;
	}

	public String getReqPath( )
	{
		return reqPath;
	}

	public long getTime( int i )
	{
		return time[ i ];
	}

	public void setTime( int i, long time )
	{
		this.time[ i ] = time;
	}

	public long getServerProcessDuration( )
	{
		if ( this.getTime( 2 ) == 0 ) {
			return 0;
		}
		return this.getTime( 2 ) - this.getTime( 1 );
	}

	public long getClientRenderingDuration( )
	{
		return this.getTime( 4 ) - this.getTime( 3 );
	}

	public long getNetworkLatency( )
	{
		long latency;
		for ( int i = 0; i < 4; i++ ) {
			if ( ( ( this.getTime( i ) ) == 0 ) ) {
				return 0;
			}
		}
		latency = this.getTime( 3 ) - this.getTime( 0 ) - this.getTime( 2 ) + this.getTime( 1 );
		return latency;
	}

}
