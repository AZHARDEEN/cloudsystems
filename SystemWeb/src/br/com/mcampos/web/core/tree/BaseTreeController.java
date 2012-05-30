package br.com.mcampos.web.core.tree;

import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import br.com.mcampos.web.controller.admin.security.treenode.BaseTreeNode;
import br.com.mcampos.web.core.BaseDBController;
import br.com.mcampos.web.core.event.IDropEvent;

public abstract class BaseTreeController<SESSION, ENTITY> extends BaseDBController<SESSION> implements IDropEvent
{
	@Wire( "#tree" )
	private Tree tree;

	@Wire( "#cmdUpdate" )
	private Button btnUpdate;

	@Wire( "#cmdDelete" )
	private Button btnDelete;

	@Wire( "#recordEdit" )
	private Div divEdit;

	@Wire( "#recordView" )
	private Div divView;

	private Boolean updating;

	private BaseTreeNode<ENTITY> root;

	private static final long serialVersionUID = -3667218525594327898L;

	protected abstract TreeitemRenderer<?> getTreeRenderer( );

	protected abstract BaseTreeNode<ENTITY> getRootNode( );

	protected abstract void showRecord( ENTITY data );

	protected abstract void prepareForInsert( );

	protected abstract void prepareForUpdate( ENTITY data );

	protected abstract boolean validateRecord( );

	protected abstract BaseTreeNode<ENTITY> insert( );

	protected abstract void update( SESSION session, ENTITY data );

	protected abstract void remove( SESSION session, ENTITY data );

	protected abstract void changeParent( SESSION session, ENTITY source, ENTITY target );

	@SuppressWarnings( "unchecked" )
	protected AdvancedTreeModel<ENTITY> getModel( )
	{
		Object obj;

		obj = ( getTree( ).getModel( ) );
		if ( obj == null ) {
			obj = new AdvancedTreeModel<ENTITY>( getRoot( ) );
		}
		return (AdvancedTreeModel<ENTITY>) obj;
	}

	protected void loadTree( )
	{
		getTree( ).setModel( getModel( ) );
	}

	protected Tree getTree( )
	{
		return this.tree;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getTree( ).setItemRenderer( getTreeRenderer( ) );
		loadTree( );
	}

	protected Button getBtnUpdate( )
	{
		return this.btnUpdate;
	}

	protected Button getBtnDelete( )
	{
		return this.btnDelete;
	}

	protected Div getDivEdit( )
	{
		return this.divEdit;
	}

	protected Div getDivView( )
	{
		return this.divView;
	}

	@Listen( "onSelect = #tree" )
	public void onSelect( Event evt )
	{
		BaseTreeNode<ENTITY> baseNode = getSelectedNode( );
		if ( baseNode == null ) {
			getBtnDelete( ).setDisabled( true );
			getBtnUpdate( ).setDisabled( true );
			showRecord( null );
		}
		else {
			getBtnDelete( ).setDisabled( false );
			getBtnUpdate( ).setDisabled( false );
			showRecord( baseNode.getData( ) );
		}
		evt.stopPropagation( );
	}

	protected BaseTreeNode<ENTITY> getSelectedNode( )
	{
		return getTree( ).getSelectedItem( ).getValue( );
	}

	@Listen( "onClick = #cmdRefresh" )
	public void onRefresh( Event evt )
	{
		getTree( ).setModel( null );
		loadTree( );
		getBtnDelete( ).setDisabled( true );
		getBtnUpdate( ).setDisabled( true );
		showRecord( null );
		evt.stopPropagation( );
	}

	@Listen( "onClick = #cmdCreate" )
	public void onCreate( Event evt )
	{
		setUpdating( false );
		enableEdit( true );
		prepareForInsert( );
		evt.stopPropagation( );
	}

	@Listen( "onClick = #cmdUpdate" )
	public void onUpdate( Event evt )
	{
		setUpdating( true );
		enableEdit( true );
		prepareForUpdate( getSelectedNode( ).getData( ) );
		evt.stopPropagation( );
	}

	private Boolean isUpdating( )
	{
		if ( this.updating == null ) {
			this.updating = false;
		}
		return this.updating;
	}

	private void setUpdating( Boolean bSet )
	{
		this.updating = bSet;
	}

	private void enableEdit( boolean enable )
	{
		getDivView( ).setVisible( !enable );
		getDivEdit( ).setVisible( enable );
	}

	@Listen( "onClick = #cmdSave, #cmdCancel; onOK = textbox" )
	public void onSave( Event evt )
	{
		if ( ( evt.getTarget( ).getId( ).equalsIgnoreCase( "cmdSave" ) || evt.getName( ).equalsIgnoreCase( "onOK" ) ) ) {
			if ( validateRecord( ) == false ) {
				return;
			}
			BaseTreeNode<ENTITY> node;
			try {
				if ( isUpdating( ) )
				{
					node = getSelectedNode( );
					update( getSession( ), node.getData( ) );
					node.setData( node.getData( ) );
				}
				else {
					node = insert( );
					getModel( ).add( getRoot( ), node );
				}
			}
			catch ( Exception e )
			{
				if ( isUpdating( ) ) {
					showErrorMessage( "Erro ao tentar atualizar o item selecionado", "Atualização" );
				}
				else {
					showErrorMessage( "Erro ao tentar incluir novo item", "Inclusão" );
				}
			}
		}
		enableEdit( false );
		evt.stopPropagation( );
	}

	private BaseTreeNode<ENTITY> getRoot( )
	{
		if ( this.root == null ) {
			this.root = getRootNode( );
		}
		return this.root;
	}

	protected void onDeleteEntity( Event evt )
	{
		if ( evt != null && evt.getName( ).equalsIgnoreCase( "onYes" ) )
		{
			BaseTreeNode<ENTITY> nodeToDelete = getSelectedNode( );
			getModel( ).remove( nodeToDelete );
			try {
				remove( getSession( ), nodeToDelete.getData( ) );
			}
			catch ( Exception e )
			{
				showErrorMessage( "Erro ao excluir item", "Exclusao" );
			}
			showRecord( null );
			evt.stopPropagation( );
		}
	}

	@Listen( "onClick = #cmdDelete, #removeTreeitem, #removeTreeitemMove" )
	public void onDelete( Event evt )
	{
		Treeitem item = getTree( ).getSelectedItem( );
		if ( item == null )
		{
			Messagebox.show( "Por favor, selecione um item antes de excluí-lo", "Exclusão", Messagebox.OK,
					Messagebox.INFORMATION );
			return;
		}
		else {
			Messagebox.show( "Deseja excluir o item selecionado e todas os seus descendentes", "Exclusão", Messagebox.YES
					| Messagebox.NO,
					Messagebox.QUESTION, 2, new EventListener<Event>( )
					{
				@Override
				public void onEvent( Event event ) throws Exception
				{
					onDeleteEntity( event );
				}
					} );
		}
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public void onDrop( DropEvent evt )
	{
		Treerow source = (Treerow) evt.getDragged( );
		Treerow target = (Treerow) evt.getTarget( );
		Object vSource = ( (Treeitem) source.getParent( ) ).getValue( );
		BaseTreeNode<ENTITY> vTarget = ( (Treeitem) target.getParent( ) ).getValue( );
		if ( vSource instanceof BaseTreeNode ) {
			BaseTreeNode<ENTITY> r = (BaseTreeNode<ENTITY>) vSource;

			try {
				changeParent( getSession( ), r.getData( ), vTarget.getData( ) );
				getModel( ).remove( r );
				getModel( ).add( vTarget, r );
			}
			catch ( Exception e )
			{
				showErrorMessage( "Erro ao mover item", "Mover" );
			}

		}
	}
}
