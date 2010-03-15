package br.com.mcampos.controller.admin.security.roles;

import org.zkoss.zul.AbstractTreeModel;

public class RoleModel extends AbstractTreeModel
{
    public RoleModel( Object object )
    {
        super( object );
    }

    public boolean isLeaf( Object node )
    {
        return false;
    }

    public Object getChild( Object parent, int index )
    {
        return null;
    }

    public int getChildCount( Object parent )
    {
        return 0;
    }
}
