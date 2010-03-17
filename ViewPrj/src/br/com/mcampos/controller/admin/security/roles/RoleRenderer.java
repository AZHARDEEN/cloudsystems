package br.com.mcampos.controller.admin.security.roles;


import br.com.mcampos.dto.security.RoleDTO;

import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

public class RoleRenderer implements TreeitemRenderer
{
    public RoleRenderer()
    {
        super();
    }

    public void render( Treeitem item, Object data ) throws Exception
    {
        item.setValue( data );
        if ( data == null )
            return;
        RoleDTO role = ( RoleDTO ) data;
        Treerow treeRow = item.getTreerow();
        Treecell cell;

        if ( treeRow == null ) {
            treeRow = new Treerow();
            item.appendChild( treeRow );
        }
        cell = (Treecell) treeRow.getFirstChild();
        if ( cell == null ) {
            cell = new Treecell( );
            treeRow.appendChild( cell );
        }
        cell.setLabel( data.toString() );
    }
}
