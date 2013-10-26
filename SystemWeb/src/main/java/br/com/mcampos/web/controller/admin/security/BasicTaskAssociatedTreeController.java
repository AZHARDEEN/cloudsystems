package br.com.mcampos.web.controller.admin.security;

import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.jpa.security.Task;
import br.com.mcampos.web.controller.admin.security.treenode.BaseTreeNode;
import br.com.mcampos.web.controller.admin.security.treenode.TaskNode;
import br.com.mcampos.web.core.tree.AdvancedTreeModel;
import br.com.mcampos.web.core.tree.BaseTreeController;
import br.com.mcampos.web.renderer.TaskListItemRenderer;
import br.com.mcampos.web.renderer.TaskTreeItemRenderer;

public abstract class BasicTaskAssociatedTreeController<SESSION extends BaseSessionInterface, ENTITY> extends BaseTreeController<SESSION, ENTITY>
{
	private static final long serialVersionUID = 3217455613838662440L;
	private static final String taskPopupId = "listPopupTaskRole";
	@Wire
	private Tree treeTasks;

	@Wire
	private Listbox listTasks;

	protected abstract TaskNode createRootTaskNode( );

	protected abstract void deleteTask( SESSION session, ENTITY entity, Task task );

	protected abstract void addTask( SESSION session, ENTITY entity, Task task );

	protected Tree getTreeTasks( )
	{
		return this.treeTasks;
	}

	protected Listbox getListTasks( )
	{
		return this.listTasks;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getListTasks( ).setItemRenderer( new TaskListItemRenderer( taskPopupId ) );
		getTreeTasks( ).setItemRenderer( new TaskTreeItemRenderer( this ) );
		getTreeTasks( ).setModel( new AdvancedTreeModel<Task>( createRootTaskNode( ) ) );
	}

	protected void onDeleteTask( Event evt )
	{
		if ( evt != null && evt.getName( ).equalsIgnoreCase( "onYes" ) )
		{
			Task task = (Task) getListTasks( ).getSelectedItem( ).getValue( );
			@SuppressWarnings( "unchecked" )
			ListModelList<Task> model = (ListModelList<Task>) (Object) getListTasks( ).getModel( );
			try {
				deleteTask( getSession( ), getSelectedNode( ).getData( ), task );
			}
			catch ( Exception e )
			{
				showErrorMessage( "Erro ao remover tarefa", "Remover Tarefa" );
			}
			model.remove( task );
			evt.stopPropagation( );
		}
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public void onDrop( DropEvent evt )
	{
		if ( evt != null )
		{
			Treerow source = (Treerow) evt.getDragged( );
			Treerow target = (Treerow) evt.getTarget( );
			Object vSource = ( (Treeitem) source.getParent( ) ).getValue( );
			BaseTreeNode<ENTITY> vTarget = ( (Treeitem) target.getParent( ) ).getValue( );
			if ( vSource instanceof TaskNode )
			{
				TaskNode t = (TaskNode) vSource;
				if ( t != null ) {
					try {
						addTask( getSession( ), vTarget.getData( ), t.getData( ) );
						ListModelList<Task> model = (ListModelList<Task>) (Object) getListTasks( ).getModel( );
						if ( model.contains( t.getData( ) ) == false ) {
							model.add( t.getData( ) );
						}
					}
					catch ( Exception e )
					{
						showErrorMessage( "Erro ao adicinar tarefa", "Adicionar Tarefa" );
					}

				}
			}
			else {
				super.onDrop( evt );
			}
		}
	}

	@Listen( "onClick = #removeTaskItem" )
	public void onRemoveLinkedTask( Event evt )
	{
		Listitem item = getListTasks( ).getSelectedItem( );
		if ( item == null )
		{
			Messagebox.show( "Por favor, selecione uma tarefa associada antes de excluí-la", "Exclusão de Tarafa Associada",
					Messagebox.OK,
					Messagebox.INFORMATION );
			return;
		}
		Task task = (Task) item.getValue( );
		String msg = String.format( "Deseja excluir tarefa %s associada item selecionado", task.getDescription( ) );
		Messagebox.show( msg, "Exclusão de Tarefa Associada", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, 2, new EventListener<Event>( )
				{
					@Override
					public void onEvent( Event event ) throws Exception
					{
						onDeleteTask( event );
					}
				} );
		evt.stopPropagation( );
	}

	@Listen( "onDoubleClick = listbox" )
	public void onDoubleClick( MouseEvent evt )
	{
		gotoPage( "/private/admin/security/task.zul", true );
	}
}
