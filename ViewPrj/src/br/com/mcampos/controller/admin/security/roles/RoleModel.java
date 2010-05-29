package br.com.mcampos.controller.admin.security.roles;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.ejb.cloudsystem.security.facade.SecurityFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.event.TreeDataEvent;

public class RoleModel extends AbstractTreeModel
{
	SecurityFacade session;
	AuthenticationDTO currentUser;

	public RoleModel( AuthenticationDTO currentUser, Object root ) throws ApplicationException
	{
		super( root );
		this.currentUser = currentUser;
	}


	protected boolean isRole( Object node )
	{
		return ( node instanceof RoleDTO );
	}

	public boolean isLeaf( Object node )
	{
		return getChildCount( node ) == 0;
	}

	public Object getChild( Object parent, int index )
	{
		Object child = null;
		if ( isRole( parent ) )
			child = ( ( RoleDTO )parent ).getChildRoles().get( index );
		return child;
	}

	public int getChildCount( Object parent )
	{
		if ( isRole( parent ) )
			return getChildCount( ( ( RoleDTO )parent ) );
		return 0;
	}

	protected int getChildCount( RoleDTO role )
	{
		int childs = 0;

		if ( role == null )
			return childs;
		if ( role.getChildRoles() == null ) {
			role.setChildRoles( role.getChildRoles() );
		}
		childs = ( role.getChildRoles() != null ) ? role.getChildRoles().size() : 0;
		return childs;
	}

	protected SecurityFacade getSession()
	{
		if ( session == null )
			session = ( SecurityFacade )getRemoteSession( SecurityFacade.class );
		return session;
	}

	protected Object getRemoteSession( Class remoteClass )
	{
		try {
			return ServiceLocator.getInstance().getRemoteSession( remoteClass );
		}
		catch ( ServiceLocatorException e ) {
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}

	protected AuthenticationDTO getCurrentUser()
	{
		return currentUser;
	}

	public void add( RoleDTO dto )
	{
		RoleDTO parent = find( dto.getParent() );
		if ( parent != null ) {
			parent.add( dto );
			int nIndex = parent.getChildRoles().size();
			nIndex--;
			if ( nIndex == 0 ) {
				RoleDTO p = parent.getParent();
				fireEvent( p, 0, p.getChildRoles().size() - 1, TreeDataEvent.CONTENTS_CHANGED );
			}
			else {
				fireEvent( parent, nIndex, nIndex, TreeDataEvent.INTERVAL_ADDED );
			}
		}
	}

	public void update( RoleDTO dto )
	{
		RoleDTO parent = find( dto.getParent() );
		if ( parent != null ) {
			int nIndex = parent.getChildRoles().indexOf( dto );
			if ( nIndex >= 0 ) {
				parent.getChildRoles().set( nIndex, dto );
				fireEvent( parent, nIndex, nIndex, TreeDataEvent.CONTENTS_CHANGED );
			}
		}
	}

	public void delete( RoleDTO dto )
	{
		RoleDTO parent = find( dto.getParent() );
		if ( parent != null ) {
			int nIndex = parent.getChildRoles().indexOf( dto );
			if ( nIndex >= 0 ) {
				fireEvent( parent, nIndex, nIndex, TreeDataEvent.INTERVAL_REMOVED );
			}
		}
	}

	protected RoleDTO find( RoleDTO node )
	{
		RoleDTO root = ( RoleDTO )getRoot();

		int[] path = getPath( root, node );
		int nIndex = 0;
		while ( nIndex < path.length ) {
			root = root.getChildRoles().get( path[ nIndex ] );
			nIndex++;
		}
		return root;
	}
}
