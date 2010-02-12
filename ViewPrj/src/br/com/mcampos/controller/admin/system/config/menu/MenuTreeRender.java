package br.com.mcampos.controller.admin.system.config.menu;

import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

public class MenuTreeRender implements TreeitemRenderer
{
    public MenuTreeRender()
    {
        super();
    }

    public void render( Treeitem item, Object data ) throws Exception
    {
        item.setValue( data );

        // Construct treecells
        Treecell tcNamn = new Treecell( data.toString() );
        Treerow tr = null;

        if ( item.getTreerow() == null ) {
            tr = new Treerow();
            tr.setDraggable( "true" );
            tr.setDroppable( "true" );
            tr.setParent( item );
        }
        else {
            tr = item.getTreerow();
            tr.setDraggable( "true" );
            tr.setDroppable( "true" );
            tr.getChildren().clear();
        }
        // Attach treecells to treerow
        tcNamn.setParent( tr );
        item.setOpen( false );
    }
}
