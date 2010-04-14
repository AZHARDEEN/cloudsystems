package br.com.mcampos.controller.admin.system.config.task;


import br.com.mcampos.dto.security.TaskDTO;

import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

public class TaskTreeRenderer implements TreeitemRenderer
{
    protected Boolean draggable;
    protected Boolean droppable;

    public TaskTreeRenderer( Boolean bDraggable, Boolean bDroppable )
    {
        super();
        draggable = bDraggable;
        droppable = bDroppable;
    }

    public void render( Treeitem item, Object data ) throws Exception
    {
        item.setValue( data );
        TaskDTO dto = (TaskDTO ) data;
        Treerow row;

        row = item.getTreerow();
        if ( row == null )
        {
            row = new Treerow ();
            item.appendChild( row );
        }
        row.appendChild( new Treecell( dto.toString() ) );
        if ( draggable )
            row.setDraggable( "true" );
        if ( droppable )
            row.setDroppable( "true" );
    }
}
