package br.com.mcampos.controller.admin.security.task;

import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.util.system.IDropEvent;

public class TaskTreeRenderer implements TreeitemRenderer
{
	protected Boolean draggable;
	protected Boolean droppable;
	protected IDropEvent dropEvent;

	public TaskTreeRenderer( IDropEvent evt, Boolean bDraggable, Boolean bDroppable )
	{
		super( );
		draggable = bDraggable;
		droppable = bDroppable;
		dropEvent = evt;
	}

	public void render( Treeitem item, Object data ) throws Exception
	{
		item.setValue( data );
		TaskDTO dto = (TaskDTO) data;
		Treerow row;

		row = item.getTreerow( );
		if ( row == null )
		{
			row = new Treerow( );
			item.appendChild( row );
		}
		row.appendChild( new Treecell( dto.toString( ) ) );
		if ( draggable )
			row.setDraggable( "true" );
		if ( droppable ) {
			row.setDroppable( "true" );
			if ( dropEvent != null )
				row.addEventListener( Events.ON_DROP, new EventListener( )
				{
					@Override
					public void onEvent( Event event ) throws Exception
					{
						dropEvent.onDrop( (DropEvent) event );
					}
				} );
		}
	}

	@Override
	public void render( Treeitem arg0, Object arg1, int arg2 ) throws Exception
	{
		// TODO Auto-generated method stub

	}
}
