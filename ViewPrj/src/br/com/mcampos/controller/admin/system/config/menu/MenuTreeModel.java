package br.com.mcampos.controller.admin.system.config.menu;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.util.business.SystemLocator;

import br.com.mcampos.util.system.tree.SimpleTreeNode;

import java.util.AbstractList;
import java.util.ArrayList;

import java.util.List;

import org.zkoss.zul.AbstractTreeModel;

public class MenuTreeModel extends AbstractTreeModel
{


    public MenuTreeModel( Object root )
    {
        super( root );
    }

    public boolean isLeaf( Object node )
    {
        if ( node == null )
            return true;
        if ( node instanceof SimpleTreeNode )
            return ( ( SimpleTreeNode )node ).isLeaf();
        else if ( node instanceof MenuDTO )
            return ( ( MenuDTO )node ).getSubMenu().size() == 0;
        else
            return true;
    }

    public Object getChild( Object node, int index )
    {
        if ( node instanceof SimpleTreeNode )
            return ( ( SimpleTreeNode )node ).getChild( index );
        else if ( node instanceof MenuDTO )
            return ( ( MenuDTO )node ).getSubMenu().get( index );
        else if ( node instanceof AbstractList )
            return ( ( AbstractList )node ).get( index );
        else
            return null;
    }

    public int getChildCount( Object parent )
    {
        if ( parent == null )
            return 0;

        if ( parent instanceof SimpleTreeNode )
            return ( ( SimpleTreeNode )parent ).getChildCount();
        else if ( parent instanceof MenuDTO )
            return ( ( MenuDTO )parent ).getSubMenu().size();
        else if ( parent instanceof List )
            return ( ( List )parent ).size();
        else
            return 0;
    }
}
