package br.com.mcampos.web.controller.admin.security.treenode;

import java.util.ArrayList;
import java.util.Collection;

import org.zkoss.zul.TreeNode;

import br.com.mcampos.entity.security.Menu;

public class MenuNode extends BaseTreeNode<Menu>
{
	private static final long serialVersionUID = 1050459979911488141L;

	public MenuNode( Menu data )
	{
		super( data );
	}

	public MenuNode( Menu data, Collection<? extends TreeNode<Menu>> children )
	{
		super( data, children );
	}

	public static MenuNode createNode( Menu root )
	{

		if ( root == null ) {
			return null;
		}
		ArrayList<MenuNode> childs = new ArrayList<MenuNode>( root.getChilds( ).size( ) );
		for ( Menu item : root.getChilds( ) ) {
			childs.add( createNode( item ) );
		}
		return new MenuNode( root, childs.size( ) == 0 ? null : childs );
	}

}
