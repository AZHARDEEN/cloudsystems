package br.com.mcampos.controller.admin.security.menu;

import java.io.Serializable;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.security.menu.MenuFacade;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.util.system.tree.SimpleTreeNode;

public class MenuTreeRootNode extends SimpleTreeNode implements Serializable
{
	MenuFacade locator;
	AuthenticationDTO currentUser;

	public MenuTreeRootNode( AuthenticationDTO currentUser )
	{
		super( );
		setCurrentUser( currentUser );
	}

	public MenuFacade getLocator( )
	{
		if ( locator == null )
			locator = getRemoteSession( );
		return locator;
	}

	public void setCurrentUser( AuthenticationDTO currentUser )
	{
		this.currentUser = currentUser;
	}

	public AuthenticationDTO getCurrentUser( )
	{
		return currentUser;
	}

	@Override
	public void readChildren( ) throws ApplicationException
	{
		children = getLocator( ).getParentMenus( getCurrentUser( ) );
	}

	private MenuFacade getRemoteSession( )
	{
		try {
			return (MenuFacade) ServiceLocator.getInstance( ).getRemoteSession( MenuFacade.class, ServiceLocator.EJB_NAME[ 1 ] );
		}
		catch ( Exception e ) {
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}
}
