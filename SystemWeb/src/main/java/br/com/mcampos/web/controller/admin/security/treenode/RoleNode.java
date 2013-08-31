package br.com.mcampos.web.controller.admin.security.treenode;

import java.util.ArrayList;
import java.util.Collection;

import org.zkoss.zul.TreeNode;

import br.com.mcampos.entity.security.Role;

public class RoleNode extends BaseTreeNode<Role>
{
	private static final long serialVersionUID = 4211156700310182968L;

	public RoleNode( Role data )
	{
		super( data );
	}

	public RoleNode( Role data, Collection<? extends TreeNode<Role>> children )
	{
		super( data, children );
	}

	public static RoleNode createNode( Role root )
	{
		ArrayList<RoleNode> childs = new ArrayList<RoleNode>( root.getChilds( ).size( ) );
		for ( Role role : root.getChilds( ) ) {
			childs.add( createNode( role ) );
		}
		return new RoleNode( root, childs.size( ) == 0 ? null : childs );
	}
}
