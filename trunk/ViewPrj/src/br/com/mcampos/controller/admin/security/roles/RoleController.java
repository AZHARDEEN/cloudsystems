package br.com.mcampos.controller.admin.security.roles;


import br.com.mcampos.controller.admin.security.SecutityBaseController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tree;

public class RoleController extends SecutityBaseController
{
    Tree tree;

    public RoleController( char c )
    {
        super( c );
    }

    public RoleController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        configureTree();
    }


    protected void configureTree ()
    {
        if ( tree == null )
            return;
        tree.setTreeitemRenderer( new RoleRenderer() );
    }
}
