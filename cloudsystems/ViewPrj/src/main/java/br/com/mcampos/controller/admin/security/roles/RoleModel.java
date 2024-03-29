package br.com.mcampos.controller.admin.security.roles;

import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.event.TreeDataEvent;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.ejb.cloudsystem.security.role.RoleFacade;
import br.com.mcampos.sysutils.ServiceLocator;

public class RoleModel extends AbstractTreeModel
{
	RoleFacade session;
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

	@Override
	public boolean isLeaf( Object node )
	{
		return getChildCount( node ) == 0;
	}

	@Override
	public Object getChild( Object parent, int index )
	{
		Object child = null;
		if ( isRole( parent ) )
			child = ( (RoleDTO) parent ).getChildRoles( ).get( index );
		return child;
	}

	@Override
	public int getChildCount( Object parent )
	{
		if ( isRole( parent ) )
			return getChildCount( ( (RoleDTO) parent ) );
		return 0;
	}

	protected int getChildCount( RoleDTO role )
	{
		int childs = 0;

		if ( role == null )
			return childs;
		childs = ( role.getChildRoles( ) != null ) ? role.getChildRoles( ).size( ) : 0;
		return childs;
	}

	protected RoleFacade getSession( )
	{
		if ( session == null )
			session = (RoleFacade) getRemoteSession( RoleFacade.class );
		return session;
	}

	protected Object getRemoteSession( Class remoteClass )
	{
		try {
			return ServiceLocator.getInstance( ).getRemoteSession( remoteClass, ServiceLocator.EJB_NAME[ 1 ] );
		}
		catch ( Exception e ) {
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}

	protected AuthenticationDTO getCurrentUser( )
	{
		return currentUser;
	}

	public void add( RoleDTO dto )
	{
		RoleDTO parent = find( dto.getParent( ) );
		if ( parent != null ) {
			parent.add( dto );
			int nIndex = parent.getChildRoles( ).size( );
			nIndex--;
			if ( nIndex == 0 ) {
				RoleDTO p = parent.getParent( );
				fireEvent( p, 0, p.getChildRoles( ).size( ) - 1, TreeDataEvent.CONTENTS_CHANGED );
			}
			else
				fireEvent( parent, nIndex, nIndex, TreeDataEvent.INTERVAL_ADDED );
		}
	}

	public void update( RoleDTO dto )
	{
		RoleDTO parent = find( dto.getParent( ) );
		if ( parent != null ) {
			int nIndex = parent.getChildRoles( ).indexOf( dto );
			if ( nIndex >= 0 ) {
				parent.getChildRoles( ).set( nIndex, dto );
				fireEvent( parent, nIndex, nIndex, TreeDataEvent.CONTENTS_CHANGED );
			}
		}
	}

	public void delete( RoleDTO dto )
	{
		RoleDTO parent = find( dto.getParent( ) );
		if ( parent != null ) {
			int nIndex = parent.getChildRoles( ).indexOf( dto );
			if ( nIndex >= 0 )
				fireEvent( parent, nIndex, nIndex, TreeDataEvent.INTERVAL_REMOVED );
		}
	}

	protected RoleDTO find( RoleDTO node )
	{
		RoleDTO root = (RoleDTO) getRoot( );

		int nIndex = getIndexOfChild( root, node );
		if ( nIndex >= 0 )
			root = root.getChildRoles( ).get( nIndex );
		return root;
	}
}
