package br.com.mcampos.util.system.tree;

import br.com.mcampos.dto.core.SimpleTableDTO;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleTreeNode implements Serializable
{
    protected List children = null;

    public abstract void readChildren();

    public List getChildren()
    {
        return children;
    }

    public Object getChild( int arg1 )
    {
        Object child = null;

        if ( children == null )
            readChildren();

        if ( children != null && ( arg1 > -1 && arg1 < children.size() ) )
            child = children.get( arg1 );

        return child;
    }

    public int getChildCount()
    {
        if ( children == null )
            readChildren();

        if ( children != null )
            return children.size();

        return 0;
    }

    public boolean isLeaf()
    {
        return ( getChildCount() == 0 );
    }
}
