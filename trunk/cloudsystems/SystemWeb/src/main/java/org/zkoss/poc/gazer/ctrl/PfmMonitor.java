package org.zkoss.poc.gazer.ctrl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.poc.gazer.data.ReqDataCarrier;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.PerformanceMeter;

public class PfmMonitor implements PerformanceMeter
{

	public static String MonitoredReqPath = null;

	public static void setMonitoredReqPath( String reqPath )
	{
		MonitoredReqPath = reqPath;
	}

	public static String getMonitoredReqPath( )
	{
		return MonitoredReqPath;
	}

	public String getCurrentReqPath( )
	{
		String currentReqPath = Executions.getCurrent( ).getDesktop( ).getRequestPath( );
		return currentReqPath;
	}

	private static volatile long lastTimeStamp = System.currentTimeMillis( );

	private static Map<String, Map<String, ReqDataCarrier>> masterRecord =
			Collections.synchronizedMap( new HashMap<String, Map<String, ReqDataCarrier>>( ) );

	public static Map<String, Map<String, ReqDataCarrier>> getMasterRecord( )
	{
		return masterRecord;
	}

	public static Map<String, ReqDataCarrier> getRecord( String reqPath )
	{
		Map<String, ReqDataCarrier> record = masterRecord.get( reqPath );
		if ( record == null ) {
			record = Collections.synchronizedMap( new HashMap<String, ReqDataCarrier>( ) );
			masterRecord.put( reqPath, record );
		}
		return record;
	}

	public static ReqDataCarrier getReqDataInst( Map<String, ReqDataCarrier> record, String reqId )
	{
		ReqDataCarrier carrier = record.get( reqId );
		if ( carrier == null ) {
			carrier = new ReqDataCarrier( reqId );
			record.put( reqId, carrier );
		}
		return carrier;
	}

	@Override
	public void requestStartAtClient( String requestId, Execution exec, long time )
	{
		if ( !isIndex( exec.getDesktop( ).getRequestPath( ) ) )
			return;
		getReqDataInst( getRecord( exec.getDesktop( ).getRequestPath( ) ), requestId ).setTime( 0, time );

		// verification
		// System.out.println("T1");
	}

	@Override
	public void requestStartAtServer( String requestId, Execution exec, long time )
	{
		if ( !isIndex( exec.getDesktop( ).getRequestPath( ) ) )
			return;

		ReqDataCarrier carrier = getReqDataInst( getRecord( getCurrentReqPath( ) ), requestId );
		carrier.setTime( 1, time );
		carrier.setExecId( exec.toString( ) );
		carrier.setReqPath( getCurrentReqPath( ) );
		PfmMonitor.setMonitoredReqPath( getCurrentReqPath( ) );
		masterRecord.put( getCurrentReqPath( ), getRecord( getCurrentReqPath( ) ) );
		exec.setAttribute( ReqDataCarrier.KEY_REQ_DATA_CARRIER, carrier );

		// verification
		// System.out.println("T2");
	}

	@Override
	public void requestCompleteAtServer( String requestId, Execution exec,
			long time )
	{
		if ( !isIndex( exec.getDesktop( ).getRequestPath( ) ) )
			return;
		getReqDataInst( getRecord( exec.getDesktop( ).getRequestPath( ) ), requestId ).setTime( 2, time );

		// verification
		// System.out.println("T3");

	}

	@Override
	public void requestReceiveAtClient( String requestId, Execution exec,
			long time )
	{
		if ( !isIndex( exec.getDesktop( ).getRequestPath( ) ) )
			return;
		getReqDataInst( getRecord( exec.getDesktop( ).getRequestPath( ) ), requestId ).setTime( 3, time );

		// verification
		// System.out.println("T4");

	}

	@Override
	public void requestCompleteAtClient( String requestId, Execution exec,
			long time )
	{

		if ( !isIndex( exec.getDesktop( ).getRequestPath( ) ) )
			return;

		getReqDataInst( getRecord( exec.getDesktop( ).getRequestPath( ) ), requestId ).setTime( 4, time );
		lastTimeStamp = System.currentTimeMillis( );

		// verification
		// System.out.println("T5");

	}

	public static long getLastTimeStamp( )
	{
		return lastTimeStamp;
	}

	public final static String FILTER = "index.zul";

	public static boolean isIndex( String path )
	{
		return !path.endsWith( FILTER );
	}

}
