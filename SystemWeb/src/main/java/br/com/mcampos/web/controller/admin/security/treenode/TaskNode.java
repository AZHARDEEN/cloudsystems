package br.com.mcampos.web.controller.admin.security.treenode;

import java.util.ArrayList;
import java.util.Collection;

import org.zkoss.zul.TreeNode;

import br.com.mcampos.jpa.security.Task;

public class TaskNode extends BaseTreeNode<Task>
{
	private static final long serialVersionUID = -2454314903869520913L;

	public TaskNode( Task data )
	{
		super( data );
	}

	public TaskNode( Task data, Collection<? extends TreeNode<Task>> children )
	{
		super( data, children );
	}

	public static TaskNode createNode( Task root )
	{
		ArrayList<TaskNode> childs = new ArrayList<TaskNode>( root.getChilds( ).size( ) );
		for ( Task item : root.getChilds( ) ) {
			childs.add( createNode( item ) );
		}
		return new TaskNode( root, childs.size( ) == 0 ? null : childs );
	}
}
