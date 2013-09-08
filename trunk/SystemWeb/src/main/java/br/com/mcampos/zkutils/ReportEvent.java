package br.com.mcampos.zkutils;

import org.zkoss.zk.ui.event.Event;

import br.com.mcampos.web.core.report.ReportItem;

public class ReportEvent extends Event
{
	private static final long serialVersionUID = -132220681266466243L;
	public static final String eventName = "onReport";
	public static final String queueName = "reportEventQueue";
	private ReportItem item;

	public ReportEvent( ReportItem item )
	{
		super( eventName );
		setItem( item );
	}

	public ReportItem getItem( )
	{
		return item;
	}

	private void setItem( ReportItem item )
	{
		this.item = item;
	}

}
