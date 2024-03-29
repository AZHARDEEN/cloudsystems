package br.com.mcampos.web.core.tree;

import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

public class AdvancedTreeModel<E> extends DefaultTreeModel<E>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6054291680126270140L;

	public AdvancedTreeModel( TreeNode<E> root )
	{
		super( root );
	}

	/**
	 * remove the nodes which parent is <code>parent</code> with indexes
	 * <code>indexes</code>
	 * 
	 * @param parent
	 *            The parent of nodes are removed
	 * @param indexFrom
	 *            the lower index of the change range
	 * @param indexTo
	 *            the upper index of the change range
	 * @throws IndexOutOfBoundsException
	 *             - indexFrom < 0 or indexTo > number of parent's children
	 */
	public void remove( DefaultTreeNode<E> parent, int indexFrom, int indexTo ) throws IndexOutOfBoundsException
	{
		DefaultTreeNode<E> stn = parent;
		for ( int i = indexTo; i >= indexFrom; i-- ) {
			try {
				stn.getChildren( ).remove( i );
			}
			catch ( Exception exp ) {
				exp.printStackTrace( );
			}
		}
	}

	public void remove( DefaultTreeNode<E> target ) throws IndexOutOfBoundsException
	{
		int index = 0;
		DefaultTreeNode<E> parent = null;
		// find the parent and index of target
		parent = dfSearchParent( (DefaultTreeNode<E>) getRoot( ), target );
		for ( index = 0; index < parent.getChildCount( ); index++ ) {
			if ( parent.getChildAt( index ).equals( target ) ) {
				break;
			}
		}
		remove( parent, index, index );
	}

	/**
	 * insert new nodes which parent is <code>parent</code> with indexes
	 * <code>indexes</code> by new nodes <code>newNodes</code>
	 * 
	 * @param parent
	 *            The parent of nodes are inserted
	 * @param indexFrom
	 *            the lower index of the change range
	 * @param indexTo
	 *            the upper index of the change range
	 * @param newNodes
	 *            New nodes which are inserted
	 * @throws IndexOutOfBoundsException
	 *             - indexFrom < 0 or indexTo > number of parent's children
	 */
	public void insert( DefaultTreeNode<E> parent, int indexFrom, int indexTo, DefaultTreeNode<E>[ ] newNodes )
			throws IndexOutOfBoundsException
	{
		DefaultTreeNode<E> stn = parent;
		for ( int i = indexFrom; i <= indexTo; i++ ) {
			try {
				stn.getChildren( ).add( i, newNodes[ i - indexFrom ] );
			}
			catch ( Exception exp ) {
				throw new IndexOutOfBoundsException( "Out of bound: " + i + " while size=" + stn.getChildren( ).size( ) );
			}
		}
	}

	public void add( DefaultTreeNode<E> parent, DefaultTreeNode<E>[ ] newNodes )
	{
		for ( DefaultTreeNode<E> node : newNodes ) {
			add( parent, node );
		}
	}

	public void add( DefaultTreeNode<E> parent, DefaultTreeNode<E> newNode )
	{
		parent.getChildren( ).add( newNode );
	}

	private DefaultTreeNode<E> dfSearchParent( DefaultTreeNode<E> node, DefaultTreeNode<E> target )
	{
		if ( node.getChildren( ) != null && node.getChildren( ).contains( target ) ) {
			return node;
		}
		else {
			int size = getChildCount( node );
			for ( int i = 0; i < size; i++ ) {
				DefaultTreeNode<E> parent = dfSearchParent( (DefaultTreeNode<E>) getChild( node, i ), target );
				if ( parent != null ) {
					return parent;
				}
			}
		}
		return null;
	}
}
