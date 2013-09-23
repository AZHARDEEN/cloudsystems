/**
 * 
 */
package org.zkoss.poc.gazer.ctrl;

import java.text.DecimalFormat;
import java.util.Iterator;

import org.zkoss.poc.gazer.data.Analytics;
import org.zkoss.poc.gazer.ui.AnylRenderer;
import org.zkoss.poc.gazer.ui.EventDataRenderer;
import org.zkoss.poc.gazer.ui.ReqDataRenderer;
import org.zkoss.poc.gazer.ui.StatsRenderer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class DataCtrl extends GenericForwardComposer<Component>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7681950173804358991L;
	private long current = System.currentTimeMillis( );
	public String currentReqPath;
	private Grid reqDataGrid;
	private Grid eventDataGrid;
	private Timer reqUpdateTimer;
	private Textbox urlBox;
	// private Panelchildren browserParent;
	public Iframe browser;
	private Treechildren treeStem;
	private Grid statsGrid;
	private Grid anylGrid;

	private Flashchart eventPie;

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );

		refresh( );
	}

	public void clearModel( )
	{
		ListModelList<Object> voidModel = new ListModelList<Object>( );
		eventDataGrid.setModel( voidModel );
		reqDataGrid.setModel( voidModel );
		statsGrid.setModel( voidModel );
		anylGrid.setModel( voidModel );
		eventPie.setModel( new SimplePieModel( ) );
	}

	public void onClick$reset( )
	{
		PfmMonitor.getMasterRecord( ).clear( );
		clearModel( );
	}

	public void onClick$reload( )
	{
		browser.invalidate( );
	}

	public void onClick$entry( )
	{
		String browser_url = urlBox.getText( );
		browser.setSrc( browser_url );
		reqUpdateTimer.start( );
		clearModel( );

	}

	public void onOK$urlBox( )
	{
		onClick$entry( );
	}

	public void onTimer$reqUpdateTimer( )
	{
		reqUpdateTimer.setDelay( 500 );
		reqUpdateTimer.setRepeats( true );
		refresh( );

	}

	public void refresh( )
	{
		if ( PfmMonitor.getLastTimeStamp( ) == current )
			return;

		current = PfmMonitor.getLastTimeStamp( );

		if ( !( PfmMonitor.MonitoredReqPath == null ) ) {

			String reqPath = PfmMonitor.getMonitoredReqPath( );
			showStats( reqPath );

			if ( ( treeStem.getAttribute( reqPath ) == null ) ) {

				class TreeEventListener implements EventListener
				{
					private String reqPath = null;
					private DataCtrl ctrl;

					public TreeEventListener( String reqPath, DataCtrl ctrl )
					{
						this.reqPath = reqPath;
						this.ctrl = ctrl;
					}

					@Override
					public void onEvent( Event event ) throws Exception
					{
						ctrl.showStats( reqPath );
						browser.setSrc( reqPath );
					}
				}

				Treeitem item = new Treeitem( );
				item.setParent( treeStem );
				Treerow row = new Treerow( );
				row.setParent( item );

				Treecell pathCell = new Treecell( reqPath );
				pathCell.setParent( row );
				treeStem.setAttribute( reqPath, item );
				row.addEventListener( "onClick", new TreeEventListener( reqPath, this ) );
			}

		}

	}

	public void showStats( String reqPath )
	{

		ListModelList dataList = new ListModelList( DataList.getSortedReqList( reqPath ) );
		reqDataGrid.setModel( dataList );
		reqDataGrid.setRowRenderer( new ReqDataRenderer( ) );

		ListModelList eventDataList = new ListModelList( DataList.getEventList( reqPath ) );
		eventDataGrid.setModel( eventDataList );
		eventDataGrid.setRowRenderer( new EventDataRenderer( ) );
		statsGrid.setModel( eventDataList );
		statsGrid.setRowRenderer( new StatsRenderer( ) );

		ListModelList analyticsModel = new ListModelList( DataList.getAnalyticsList( reqPath ) );
		anylGrid.setModel( analyticsModel );
		anylGrid.setRowRenderer( new AnylRenderer( ) );

		PieModel pieModel = new SimplePieModel( );
		for ( Iterator<Analytics> aItr = analyticsModel.iterator( ); aItr
				.hasNext( ); ) {
			Analytics ana = aItr.next( );
			DecimalFormat df = new DecimalFormat( "0.00" );
			pieModel.setValue( ana.getTarget( ) + ana.getEvent( ), new Double( df
					.format( ( (double) ana.getTimeSum( ) ) / ( ana.getCount( ) ) ) )
					.doubleValue( ) );
		}
		eventPie.setModel( pieModel );
	}

}
