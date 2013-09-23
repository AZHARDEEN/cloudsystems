package org.zkoss.poc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeFormatter
{
	public TimeFormatter( )
	{
	}

	public static String timeConverter( long time )
	{
		DateFormat formatter = new SimpleDateFormat( "hh:mm:ss.SSS" );
		// DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS");
		Calendar calendar = Calendar.getInstance( );
		calendar.setTimeInMillis( time );
		return formatter.format( calendar.getTime( ) );
	}
}