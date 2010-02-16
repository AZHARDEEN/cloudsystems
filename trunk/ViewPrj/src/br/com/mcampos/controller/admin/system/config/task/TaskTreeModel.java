package br.com.mcampos.controller.admin.system.config.task;

import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.system.tree.SimpleTreeNode;

import java.util.AbstractList;

import java.util.List;

import org.zkoss.zul.AbstractTreeModel;

public class TaskTreeModel extends AbstractTreeModel
{
    public TaskTreeModel( Object object )
    {
        super( object );
    }

    public boolean isLeaf( Object node )
    {
        try {
            if ( node == null )
                return true;
            if ( node instanceof SimpleTreeNode )
                return ( ( SimpleTreeNode )node ).isLeaf();
            else if ( node instanceof TaskDTO )
                return ( ( TaskDTO )node ).getSubtasks().size() == 0;
            else
                return true;
        }
        catch ( ApplicationException e ) {
            e = null;
            return true;
        }
    }

    public Object getChild( Object parent, int index )
    {
        try {
            if ( parent instanceof SimpleTreeNode )
                return ( ( SimpleTreeNode )parent ).getChild( index );
            else if ( parent instanceof TaskDTO )
                return ( ( TaskDTO )parent ).getSubtasks().get( index );
            else if ( parent instanceof AbstractList )
                return ( ( AbstractList )parent ).get( index );
            else
                return null;
        }
        catch ( ApplicationException e ) {
            e = null;
            return null;
        }
    }

    public int getChildCount( Object parent )
    {
        if ( parent == null )
            return 0;

        try {
            if ( parent instanceof SimpleTreeNode )
                return ( ( SimpleTreeNode )parent ).getChildCount();
            else if ( parent instanceof TaskDTO )
                return ( ( TaskDTO )parent ).getSubtasks().size();
            else if ( parent instanceof List )
                return ( ( List )parent ).size();
            else
                return 0;
        }
        catch ( ApplicationException e ) {
            e = null;
            return 0;
        }
    }
}
