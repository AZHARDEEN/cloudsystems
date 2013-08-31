package org.zkoss.poc.gazer.demo;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

@SuppressWarnings( { "rawtypes", "unchecked" } )
public class LifeCtrl extends GenericForwardComposer<Window>
{
	private static final long serialVersionUID = 304060492970301897L;
	private Grid lifeGrid;
	private ListModelList<Object> lifeListModel;
	LifeDAO lifeDAO = new LifeDAO( );

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );

		lifeGrid.setRowRenderer( new LifeRenderer( ) );
		lifeGrid.setModel( lifeListModel = new ListModelList<Object>( ) );

		lifeGrid.addEventListener( "onReadData", new EventListener( )
		{
			@Override
			public void onEvent( Event event ) throws Exception
			{
				List list = lifeDAO.readAll( );
				Events.postEvent( new Event( "onRenderGrid", lifeGrid, list ) );
			}
		} );
		lifeGrid.addEventListener( "onRenderGrid", new EventListener( )
		{
			@Override
			public void onEvent( Event event ) throws Exception
			{
				lifeListModel.clear( );
				lifeListModel.addAll( (List) event.getData( ) );
			}
		} );
	}

	public void onClick$display( )
	{
		Events.postEvent( new Event( "onReadData", lifeGrid ) );
	}

}
