package br.com.mcampos.web.controller.admin.security;

import java.util.Collections;
import java.util.List;

import org.omg.CORBA.portable.ApplicationException;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.TreeitemRenderer;

import br.com.mcampos.ejb.security.menu.Menu;
import br.com.mcampos.ejb.security.menu.MenuFacade;
import br.com.mcampos.ejb.security.task.Task;
import br.com.mcampos.web.controller.admin.security.renderer.MenuTreeItemRenderer;
import br.com.mcampos.web.controller.admin.security.treenode.BaseTreeNode;
import br.com.mcampos.web.controller.admin.security.treenode.MenuNode;
import br.com.mcampos.web.controller.admin.security.treenode.TaskNode;
import br.com.mcampos.web.core.event.IDropEvent;

public class MenuController extends BasicTaskAssociatedTreeController<MenuFacade, Menu> implements IDropEvent
{
	private static final long serialVersionUID = 1749790390493626044L;

	@Wire
	Label recordId;
	@Wire
	Label recordDescription;
	@Wire
	Label recordParent;
	@Wire
	Label recordURL;
	@Wire
	Label recordSequence;
	@Wire
	Label recordImagePath;
	@Wire
	Checkbox recordSeparator;
	@Wire
	Checkbox recordAutocheck;
	@Wire
	Checkbox recordChecked;
	@Wire
	Checkbox recordCheckmark;
	@Wire
	Checkbox recordDisabled;

	@Wire
	Intbox editId;
	@Wire
	Textbox editDescription;
	@Wire
	Textbox editURL;
	@Wire
	Intbox editSequence;
	@Wire
	Textbox editImagePath;
	@Wire
	Checkbox editSeparator;
	@Wire
	Checkbox editAutocheck;
	@Wire
	Checkbox editChecked;
	@Wire
	Checkbox editCheckmark;
	@Wire
	Checkbox editDisabled;

	@Override
	protected Class<MenuFacade> getSessionClass( )
	{
		return MenuFacade.class;
	}

	@Override
	protected TreeitemRenderer<?> getTreeRenderer( )
	{
		return new MenuTreeItemRenderer( this );
	}

	@Override
	protected BaseTreeNode<Menu> getRootNode( )
	{
		Menu rootMenu = new Menu( );
		try {
			rootMenu.setChilds( getSession( ).getTopContextMenu( ) );
		}
		catch ( ApplicationException e ) {
			e.printStackTrace( );
		}
		return MenuNode.createNode( rootMenu );
	}

	@Override
	protected void showRecord( Menu node )
	{
		List<Task> tasks;
		if ( node == null )
		{
			this.recordId.setValue( "" );
			this.recordDescription.setValue( "" );
			this.recordURL.setValue( "" );
			this.recordSequence.setValue( "" );
			this.recordSeparator.setChecked( false );
			this.recordAutocheck.setChecked( false );
			this.recordChecked.setChecked( false );
			this.recordCheckmark.setChecked( false );
			this.recordDisabled.setChecked( false );
			this.recordImagePath.setValue( "" );
			tasks = Collections.emptyList( );
		}
		else
		{
			this.recordId.setValue( node.getId( ).toString( ) );
			this.recordDescription.setValue( node.getDescription( ) );
			this.recordURL.setValue( node.getUrl( ) );
			this.recordSequence.setValue( node.getSequence( ).toString( ) );
			this.recordSeparator.setChecked( node.getSeparatorBefore( ) );
			this.recordAutocheck.setChecked( node.getAutocheck( ) );
			this.recordChecked.setChecked( node.getChecked( ) );
			this.recordCheckmark.setChecked( node.getCheckmark( ) );
			this.recordDisabled.setChecked( node.getDisabled( ) );
			this.recordImagePath.setValue( node.getImagePath( ) );
			tasks = getSession( ).getTaks( node );
		}
		getListTasks( ).setModel( new ListModelList<Task>( tasks ) );
	}

	@Override
	protected void prepareForInsert( )
	{
		this.editId.setReadonly( false );
		this.editId.setValue( getSession( ).getNextId( ) );
		this.editDescription.setValue( "" );
		this.editDescription.setFocus( true );
		this.editURL.setValue( "" );
		this.editImagePath.setValue( "" );
		this.editSequence.setValue( 0 );
		this.editSeparator.setChecked( false );
		this.editAutocheck.setChecked( false );
		this.editChecked.setChecked( false );
		this.editCheckmark.setChecked( false );
		this.editDisabled.setChecked( false );
	}

	@Override
	protected void prepareForUpdate( Menu data )
	{
		this.editId.setReadonly( true );
		this.editId.setValue( data.getId( ) );
		this.editDescription.setValue( data.getDescription( ) );
		this.editDescription.setFocus( true );
		this.editURL.setValue( data.getUrl( ) );
		this.editSequence.setValue( data.getSequence( ) );
		this.editSeparator.setChecked( data.getSeparatorBefore( ) );
		this.editAutocheck.setChecked( data.getAutocheck( ) );
		this.editChecked.setChecked( data.getChecked( ) );
		this.editCheckmark.setChecked( data.getCheckmark( ) );
		this.editDisabled.setChecked( data.getDisabled( ) );
		this.editImagePath.setValue( data.getImagePath( ) );
	}

	@Override
	protected boolean validateRecord( )
	{
		return true;
	}

	@Override
	protected MenuNode insert( )
	{
		Menu menu = new Menu( );

		menu.setId( this.editId.getValue( ) );
		update( getSession( ), menu );
		return MenuNode.createNode( menu );
	}

	@Override
	protected void update( MenuFacade session, Menu menu )
	{
		menu.setDescription( this.editDescription.getValue( ) );
		menu.setUrl( this.editURL.getValue( ) );
		menu.setSequence( this.editSequence.getValue( ) );
		menu.setSeparatorBefore( this.editSeparator.isChecked( ) );
		menu.setAutocheck( this.editAutocheck.isChecked( ) );
		menu.setChecked( this.editChecked.isChecked( ) );
		menu.setCheckmark( this.editCheckmark.isChecked( ) );
		menu.setDisabled( this.editDisabled.isChecked( ) );
		menu.setImagePath( this.editImagePath.getValue( ) );

		session.merge( menu );
	}

	@Override
	protected TaskNode createRootTaskNode( )
	{
		Task rootTask = new Task( );
		rootTask.setChilds( getSession( ).getRootTask( ) );
		return ( TaskNode.createNode( rootTask ) );
	}

	@Override
	protected void remove( MenuFacade session, Menu data )
	{
		getSession( ).remove( data );
	}

	@Override
	protected void deleteTask( MenuFacade session, Menu entity, Task task )
	{
		session.remove( entity, task );
	}

	@Override
	protected void changeParent( MenuFacade session, Menu source, Menu target )
	{
		session.changeParent( source, target );
	}

	@Override
	protected void addTask( MenuFacade session, Menu entity, Task task )
	{
		session.add( entity, task );
	}
}
