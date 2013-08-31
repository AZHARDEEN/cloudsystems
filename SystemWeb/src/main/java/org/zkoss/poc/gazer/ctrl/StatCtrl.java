package org.zkoss.poc.gazer.ctrl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import org.zkoss.poc.gazer.data.EventInfo;
import org.zkoss.poc.gazer.ui.StatsRenderer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

public class StatCtrl extends GenericForwardComposer<Window>
{
	private static final long serialVersionUID = -5501323867604254783L;
	// private Panelchildren statsPanel;
	private Grid statsGrid;
	// private Grid anylGrid;
	// private Button sortByTarget;
	private ListModelList<EventInfo> statListModel;

	// private ListModelList<?> analyticsModel;
	// private ArrayList<Analytics> analyticsList;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );

	}

	public ListModelList<EventInfo> sortByEventTime( )
	{
		String reqPath = PfmMonitor.getMonitoredReqPath( );
		statListModel = new ListModelList<EventInfo>( DataList.getEventList( reqPath ) );
		statListModel.sort( new Comparator<EventInfo>( )
		{
			@Override
			public int compare( EventInfo event0, EventInfo event1 )
			{
				return ( (Long) event0.getEventProcTime( ) ).compareTo( ( event1.getEventProcTime( ) ) );
			}
		}, false );
		return statListModel;
	}

	public ListModelList<EventInfo> sortByReqID( )
	{
		String reqPath = PfmMonitor.getMonitoredReqPath( );
		statListModel = new ListModelList<EventInfo>( DataList.getEventList( reqPath ) );
		statListModel.sort( new Comparator<EventInfo>( )
		{
			@Override
			public int compare( EventInfo event0, EventInfo event1 )
			{
				return ( event0.getReqId( ) ).compareTo( ( event1.getReqId( ) ) );
			}
		}, true );
		return statListModel;
	}

	public ListModelList<EventInfo> sortByTarget( )
	{
		String reqPath = PfmMonitor.getMonitoredReqPath( );
		statListModel = new ListModelList<EventInfo>( DataList.getEventList( reqPath ) );
		statListModel.sort( new Comparator<EventInfo>( )
		{
			@Override
			public int compare( EventInfo event0, EventInfo event1 )
			{
				return ( event0.getTargetCompName( ) ).compareTo( ( event1.getTargetCompName( ) ) );
			}
		}, true );
		return statListModel;
	}

	public Grid getStatsGrid( )
	{
		Collection<Component> compSet = Executions.getCurrent( ).getDesktop( ).getComponents( );
		Iterator<Component> compItr = compSet.iterator( );
		while ( compItr.hasNext( ) ) {
			Component comp = compItr.next( );
			if ( comp.getId( ).endsWith( "statsGrid" ) ) {
				statsGrid = (Grid) comp;
				break;
			}
		}
		return statsGrid;
	}

	public void onClick$sortByEventTime( )
	{
		statsGrid = getStatsGrid( );
		statsGrid.setModel( sortByEventTime( ) );
		statsGrid.setRowRenderer( new StatsRenderer( ) );
	}

	public void onClick$sortByReqID( )
	{
		statsGrid = getStatsGrid( );
		statsGrid.setModel( sortByReqID( ) );
		statsGrid.setRowRenderer( new StatsRenderer( ) );
	}

	public void onClick$sortByTarget( )
	{
		statsGrid = getStatsGrid( );
		statsGrid.setModel( sortByTarget( ) );
		statsGrid.setRowRenderer( new StatsRenderer( ) );
	}

}
