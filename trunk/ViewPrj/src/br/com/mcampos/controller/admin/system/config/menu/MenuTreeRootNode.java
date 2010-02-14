package br.com.mcampos.controller.admin.system.config.menu;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.MenuLocator;
import br.com.mcampos.util.business.SystemLocator;
import br.com.mcampos.util.system.tree.SimpleTreeNode;

import java.io.Serializable;

public class MenuTreeRootNode extends SimpleTreeNode implements Serializable
{
    MenuLocator locator;
    AuthenticationDTO currentUser;

    public MenuTreeRootNode( AuthenticationDTO currentUser )
    {
        super();
        setCurrentUser( currentUser );
    }

    public MenuLocator getLocator()
    {
        if ( locator == null )
            locator = new MenuLocator();
        return locator;
    }

    public void setCurrentUser( AuthenticationDTO currentUser )
    {
        this.currentUser = currentUser;
    }

    public AuthenticationDTO getCurrentUser()
    {
        return currentUser;
    }

    public void readChildren() throws ApplicationException
    {
        children = getLocator().get( getCurrentUser() );
    }
}
