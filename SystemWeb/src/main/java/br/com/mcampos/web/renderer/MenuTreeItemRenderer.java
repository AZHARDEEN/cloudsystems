package br.com.mcampos.web.renderer;

import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import br.com.mcampos.web.controller.admin.security.treenode.MenuNode;
import br.com.mcampos.web.core.event.IDropEvent;

public class MenuTreeItemRenderer extends SecurityBaseTreeRenderer<MenuNode>
{
	public MenuTreeItemRenderer( IDropEvent event )
	{
		super( event );
	}

	@Override
	public void render( Treeitem item, MenuNode data, int index ) throws Exception
	{
		Treerow row = getRow( item );
		Treecell cell = getCell( row );
		cell.setLabel( data.getData( ).getDescription( ) );
		cell = createCell( row );
		cell.setLabel( data.getData( ).getUrl( ) );
		item.setValue( data );
		setEventListener( row );
		MenuNode parent = (MenuNode) data.getParent( );
		if ( parent.getData( ).getId( ) == null ) {
			setContext( item, "treePopup" );
		}
		else {
			setContext( item, "treePopupMove" );
		}
	}
}
