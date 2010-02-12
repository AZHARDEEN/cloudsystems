package br.com.mcampos.controller.admin.system.config.menu;

import br.com.mcampos.controller.logged.PrivateIndexController;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

public class MenuTreeRender implements TreeitemRenderer
{
    MenuController parent;


    public MenuTreeRender( MenuController parent )
    {
        super();
        setParent( parent );
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
            tr.addEventListener( Events.ON_DROP, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        getParent().onDrop( event );
                    }
                } );
        }
        else {
            tr = item.getTreerow();
            tr.getChildren().clear();
        }
        // Attach treecells to treerow
        tcNamn.setParent( tr );
        item.setOpen( false );
    }

    public void setParent( MenuController parent )
    {
        this.parent = parent;
    }

    public MenuController getParent()
    {
        return parent;
    }
}
