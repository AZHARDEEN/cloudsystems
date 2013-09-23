package br.com.mcampos.controller.admin.security.roles;

import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.util.system.IDropEvent;

public class RoleRenderer implements TreeitemRenderer
{
	protected Boolean draggable;
	protected Boolean droppable;
	protected IDropEvent dropEvent;

	public RoleRenderer( IDropEvent evt, Boolean bDraggable, Boolean bDroppable )
	{
		super( );
		draggable = bDraggable;
		droppable = bDroppable;
		dropEvent = evt;
	}

	@Override
	public void render( Treeitem item, Object data, int index ) throws Exception
	{
		item.setValue( data );
		if ( data == null )
			return;
		RoleDTO role = (RoleDTO) data;
		Treerow treeRow = item.getTreerow( );
		Treecell cell;

		if ( treeRow == null ) {
			treeRow = new Treerow( );
			item.appendChild( treeRow );
		}
		cell = (Treecell) treeRow.getFirstChild( );
		if ( cell == null ) {
			cell = new Treecell( );
			treeRow.appendChild( cell );
		}
		cell.setLabel( data.toString( ) );
		if ( draggable )
			treeRow.setDraggable( "true" );
		if ( droppable ) {
			treeRow.setDroppable( "true" );
			if ( dropEvent != null )
				treeRow.addEventListener( Events.ON_DROP, new EventListener( )
				{
					@Override
					public void onEvent( Event event ) throws Exception
					{
						dropEvent.onDrop( (DropEvent) event );
					}
				} );
		}
	}
}
