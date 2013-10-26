package br.com.mcampos.controller.admin.security.menu;

import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.util.system.IDropEvent;

public class MenuTreeRenderer implements TreeitemRenderer
{
	protected Boolean draggable;
	protected Boolean droppable;
	protected IDropEvent dropEvent;

	public MenuTreeRenderer( IDropEvent evt, Boolean bDraggable, Boolean bDroppable )
	{
		super( );
		draggable = bDraggable;
		droppable = bDroppable;
		dropEvent = evt;
	}

	@Override
	public void render( Treeitem item, Object data, int index ) throws Exception
	{
		Treerow tr = null;

		item.setValue( data );
		if ( item.getTreerow( ) == null ) {
			tr = new Treerow( );
			tr.setParent( item );
		}
		else {
			tr = item.getTreerow( );
			tr.getChildren( ).clear( );
		}
		configureTreeitem( item );
	}

	protected void configureTreeitem( Treeitem item )
	{
		MenuDTO data = (MenuDTO) item.getValue( );
		Treerow row;

		row = item.getTreerow( );
		row.appendChild( new Treecell( data.toString( ) ) );
		row.appendChild( new Treecell( data.getSequence( ).toString( ) ) );

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
}
