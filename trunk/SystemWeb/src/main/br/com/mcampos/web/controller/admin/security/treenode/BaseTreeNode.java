package br.com.mcampos.web.controller.admin.security.treenode;

import java.util.Collection;

import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeNode;

public abstract class BaseTreeNode<ENTITY> extends DefaultTreeNode<ENTITY>
{
	private static final long serialVersionUID = -3477427471108573757L;

	public BaseTreeNode( ENTITY data )
	{
		super( data );
	}

	public BaseTreeNode( ENTITY data, Collection<? extends TreeNode<ENTITY>> children )
	{
		super( data, children );
	}

}
