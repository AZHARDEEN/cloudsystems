package br.com.mcampos.controller.admin.system.config.menu;

import br.com.mcampos.controller.core.LoggedBaseController;

import br.com.mcampos.util.business.SystemLocator;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tree;

public class MenuController extends LoggedBaseController
{

    protected Tree treeList;
    protected SystemLocator locator;


    public MenuController( char c )
    {
        super( c );
    }

    public MenuController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        getTreeList().setTreeitemRenderer( new MenuTreeRender() );
        getTreeList().setModel( new MenuTreeModel( getLocator().getMenuList( getLoggedInUser() ) ) );
    }

    protected Tree getTreeList()
    {
        return treeList;
    }

    protected SystemLocator getLocator()
    {
        if ( locator == null )
            locator = new SystemLocator();
        return locator;
    }
}
