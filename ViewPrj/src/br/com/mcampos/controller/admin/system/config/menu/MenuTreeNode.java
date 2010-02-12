package br.com.mcampos.controller.admin.system.config.menu;

import br.com.mcampos.util.business.SystemLocator;
import br.com.mcampos.util.system.tree.SimpleTreeNode;

public class MenuTreeNode extends SimpleTreeNode
{
    SystemLocator locator;


    public MenuTreeNode()
    {
        super();
    }

    public void readChildren()
    {
    }

    public SystemLocator getLocator()
    {
        if ( locator == null )
            locator = new SystemLocator();
        return locator;
    }
}
