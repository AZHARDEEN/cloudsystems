package br.com.mcampos.web.renderer;

import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import br.com.mcampos.web.controller.admin.security.treenode.TaskNode;
import br.com.mcampos.web.core.event.IDropEvent;

public class TaskTreeItemRenderer extends SecurityBaseTreeRenderer<TaskNode>
{

	public TaskTreeItemRenderer( IDropEvent event )
	{
		super( event );
	}

	@Override
	public void render( Treeitem item, TaskNode data, int index ) throws Exception
	{
		Treerow row = getRow( item );
		Treecell cell = getCell( row );
		cell.setLabel( data.getData( ).getDescription( ) );
		item.setValue( data );
		setEventListener( row );
		TaskNode parent = (TaskNode) data.getParent( );
		if ( parent.getData( ).getId( ) == null ) {
			setContext( item, "treePopup" );
		}
		else {
			setContext( item, "treePopupMove" );
		}
	}
}
