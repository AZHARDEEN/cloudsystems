package br.com.mcampos.web.controller.admin.security;

import java.util.Collections;
import java.util.List;

import org.omg.CORBA.portable.ApplicationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.security.menu.MenuFacade;
import br.com.mcampos.entity.security.Menu;
import br.com.mcampos.entity.security.Task;
import br.com.mcampos.web.controller.admin.security.treenode.BaseTreeNode;
import br.com.mcampos.web.controller.admin.security.treenode.MenuNode;
import br.com.mcampos.web.controller.admin.security.treenode.TaskNode;
import br.com.mcampos.web.core.event.IDropEvent;
import br.com.mcampos.web.renderer.MenuTreeItemRenderer;

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
		catch( ApplicationException e ) {
			e.printStackTrace( );
		}
		return MenuNode.createNode( rootMenu );
	}

	@Override
	protected void showRecord( Menu node )
	{
		List<Task> tasks;
		if( node == null )
		{
			recordId.setValue( "" );
			recordDescription.setValue( "" );
			recordURL.setValue( "" );
			recordSequence.setValue( "" );
			recordSeparator.setChecked( false );
			recordAutocheck.setChecked( false );
			recordChecked.setChecked( false );
			recordCheckmark.setChecked( false );
			recordDisabled.setChecked( false );
			recordImagePath.setValue( "" );
			tasks = Collections.emptyList( );
		}
		else
		{
			recordId.setValue( node.getId( ).toString( ) );
			recordDescription.setValue( node.getDescription( ) );
			recordURL.setValue( node.getUrl( ) );
			recordSequence.setValue( node.getSequence( ).toString( ) );
			recordSeparator.setChecked( node.getSeparatorBefore( ) );
			recordAutocheck.setChecked( node.getAutocheck( ) );
			recordChecked.setChecked( node.getChecked( ) );
			recordCheckmark.setChecked( node.getCheckmark( ) );
			recordDisabled.setChecked( node.getDisabled( ) );
			recordImagePath.setValue( node.getImagePath( ) );
			tasks = getSession( ).getTaks( node );
		}
		getListTasks( ).setModel( new ListModelList<Task>( tasks ) );
	}

	@Override
	protected void prepareForInsert( )
	{
		editId.setReadonly( false );
		editId.setValue( getSession( ).getNextId( ) );
		editDescription.setValue( "" );
		editDescription.setFocus( true );
		editURL.setValue( "" );
		editImagePath.setValue( "" );
		editSequence.setValue( 0 );
		editSeparator.setChecked( false );
		editAutocheck.setChecked( false );
		editChecked.setChecked( false );
		editCheckmark.setChecked( false );
		editDisabled.setChecked( false );
	}

	@Override
	protected void prepareForUpdate( Menu data )
	{
		editId.setReadonly( true );
		editId.setValue( data.getId( ) );
		editDescription.setValue( data.getDescription( ) );
		editDescription.setFocus( true );
		editURL.setValue( data.getUrl( ) );
		editSequence.setValue( data.getSequence( ) );
		editSeparator.setChecked( data.getSeparatorBefore( ) );
		editAutocheck.setChecked( data.getAutocheck( ) );
		editChecked.setChecked( data.getChecked( ) );
		editCheckmark.setChecked( data.getCheckmark( ) );
		editDisabled.setChecked( data.getDisabled( ) );
		editImagePath.setValue( data.getImagePath( ) );
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

		menu.setId( editId.getValue( ) );
		update( getSession( ), menu );
		return MenuNode.createNode( menu );
	}

	@Override
	protected void update( MenuFacade session, Menu menu )
	{
		menu.setDescription( editDescription.getValue( ) );
		menu.setUrl( editURL.getValue( ) );
		menu.setSequence( editSequence.getValue( ) );
		menu.setSeparatorBefore( editSeparator.isChecked( ) );
		menu.setAutocheck( editAutocheck.isChecked( ) );
		menu.setChecked( editChecked.isChecked( ) );
		menu.setCheckmark( editCheckmark.isChecked( ) );
		menu.setDisabled( editDisabled.isChecked( ) );
		menu.setImagePath( editImagePath.getValue( ) );

		session.merge( menu );
	}

	@Override
	protected TaskNode createRootTaskNode( )
	{
		return(TaskNode.createNode( getSession( ).getRootTask( ) ));
	}

	@Override
	protected void remove( MenuFacade session, Menu data )
	{
		getSession( ).remove( getPrincipal( ), data );
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

	@Listen( "onClick=#btnFilePath,#btnPath" )
	public void onClickFilePath( Event evt )
	{
		Component c = createComponents( "/templates/file_path_dialog.zul", getMainWindow( ), null );
		if( c != null && c instanceof Window ) {
			((Window) c).doModal( );
		}
		if( evt != null )
			evt.stopPropagation( );
	}
}
