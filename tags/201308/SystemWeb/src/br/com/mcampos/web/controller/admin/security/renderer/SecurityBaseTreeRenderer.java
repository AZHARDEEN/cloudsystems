package br.com.mcampos.web.controller.admin.security.renderer;

import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import br.com.mcampos.web.core.event.IDropEvent;

public abstract class SecurityBaseTreeRenderer<NODE> implements TreeitemRenderer<NODE>
{
	private IDropEvent dropEvent;

	public SecurityBaseTreeRenderer( IDropEvent event )
	{
		setDropEvent( event );
	}

	protected IDropEvent getDropEvent( )
	{
		return this.dropEvent;
	}

	private void setDropEvent( IDropEvent dropEvent )
	{
		this.dropEvent = dropEvent;
	}

	protected void setEventListener( Treerow row )
	{
		row.setDraggable( "true" );
		row.setDroppable( "true" );
		row.addEventListener( Events.ON_DROP, new EventListener<DropEvent>( )
		{
			@Override
			public void onEvent( DropEvent event ) throws Exception
			{
				getDropEvent( ).onDrop( event );
			}
		} );
	}

	protected Treerow getRow( Treeitem item )
	{
		Treerow row;

		row = item.getTreerow( );
		if ( row == null ) {
			row = new Treerow( );
			item.appendChild( row );
		}
		return row;
	}

	protected Treecell getCell( Treerow row )
	{
		Treecell cell = (Treecell) row.getFirstChild( );
		if ( cell == null ) {
			cell = new Treecell( );
			row.appendChild( cell );
		}
		return cell;
	}

	protected Treecell createCell( Treerow row )
	{
		Treecell cell = new Treecell( );
		row.appendChild( cell );
		return cell;
	}

	protected void setContext( Treeitem item, String context )
	{
		item.setContext( context );
	}

}
