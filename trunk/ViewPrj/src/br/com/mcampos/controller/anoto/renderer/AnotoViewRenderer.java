package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.AnotoPenPageDTO;

import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

public class AnotoViewRenderer implements TreeitemRenderer
{
    public AnotoViewRenderer()
    {
        super();
    }

    public void render( Treeitem item, Object data ) throws Exception
    {
        Treerow treeRow = item.getTreerow();
        Treecell cell;

        if ( treeRow == null ) {
            treeRow = new Treerow();
            item.appendChild( treeRow );
        }
        if ( data instanceof AnotoPenPageDTO ) {
            cell = new Treecell( ( ( AnotoPenPageDTO )data ).getPenId() );
        }
        else {
            cell = new Treecell( data.toString() );
        }
        treeRow.appendChild( cell );
    }

}
