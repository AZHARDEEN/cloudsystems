package br.com.mcampos.web.controller.admin.security.renderer;

import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import br.com.mcampos.web.controller.admin.security.treenode.RoleNode;
import br.com.mcampos.web.core.event.IDropEvent;

public class RoleTreeItemRenderer extends SecurityBaseTreeRenderer<RoleNode>
{
	public RoleTreeItemRenderer( IDropEvent event )
	{
		super( event );
	}

	@Override
	public void render( Treeitem item, RoleNode data, int index ) throws Exception
	{
		Treerow row = getRow( item );
		Treecell cell = getCell( row );
		cell.setLabel( data.getData( ).getDescription( ) );
		item.setValue( data );
		setEventListener( row );

		RoleNode parent = (RoleNode) data.getParent( );
		if ( parent.getData( ).getId( ).equals( 1 ) ) {
			setContext( item, "treePopup" );
		}
		else {
			setContext( item, "treePopupMove" );
		}

	}
}
