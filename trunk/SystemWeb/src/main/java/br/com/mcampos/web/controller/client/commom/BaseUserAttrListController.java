package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.web.core.BaseController;

public abstract class BaseUserAttrListController<DATA> extends BaseController<Component>
{
	private static final long serialVersionUID = 993535382863182500L;

	@Wire( "#removeDocument, #removeContact, #removeAddress, #updateDocument, #updateContact, #updateAddress, #cmdDeleteAddress, #cmdUpdateAddress" )
	private Button[ ] itemButtons;

	@Wire( "#divOperationsAddress, #divInfoAddress" )
	private Div[ ] divOperations;

	@Wire( "#divConfirmOperationsAddress, #divEditAddress" )
	private Div[ ] divConfirmations;

	protected abstract Listbox getListbox( );

	protected abstract void showRecord( DATA data );

	protected abstract DATA createNew( );

	protected abstract void update( DATA data );

	protected abstract boolean validate( DATA data );

	private boolean bUpdate = false;

	@Listen( "onSelect = #documentList, #contactList, #addressList" )
	public void onSelect( Event evt )
	{
		Listitem selectedItem;

		selectedItem = getListbox( ).getSelectedItem( );
		if ( selectedItem != null && selectedItem.getValue( ) != null ) {
			DATA value = selectedItem.getValue( );
			showRecord( value );
			enableItemButtons( true );
			if ( value instanceof UserDocument ) {
				UserDocument d = (UserDocument) value;
				if ( d.getType( ).getId( ).equals( UserDocument.typeCPF ) || d.getType( ).getId( ).equals( UserDocument.typeEmail ) ) {
					for ( Button b : this.itemButtons ) {
						if ( b.getId( ).equals( "removeDocument" ) ) {
							b.setDisabled( true );
						}
					}
				}
			}
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onAfterRender=listbox" )
	public void afterRender( Event evt )
	{
		if ( evt != null ) {
			if ( evt.getTarget( ) instanceof Listbox ) {
				Listbox l = (Listbox) evt.getTarget( );
				if ( l.getItemCount( ) > 0 ) {
					l.setSelectedIndex( 0 );
					onSelect( null );
				}
			}
			evt.stopPropagation( );
		}
	}

	protected DATA getSelected( )
	{
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item != null ) {
			return item.getValue( );
		}
		else {
			return null;
		}
	}

	@Listen( "onClick = #removeDocument, #removeContact, #removeAddress" )
	public void onRemove( Event evt )
	{
		DATA item = getSelected( );
		if ( item != null ) {
			ListModelList<DATA> model = getModel( );
			model.remove( item );
			setSelectedIndex( 0 );
		}
		else {
			showRecord( null );
			enableItemButtons( false );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onClick = #addDocument, #addContact, #addAddress" )
	public void onNew( Event evt )
	{
		DATA newItem = createNew( );
		if ( newItem != null ) {
			getModel( ).add( newItem );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onClick = #updateDocument, #updateContact, #updateAddress" )
	public void onUpdate( Event evt )
	{
		DATA newItem = getSelected( );
		if ( newItem != null ) {
			update( newItem );
			getModel( ).set( getListbox( ).getSelectedIndex( ), newItem );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private ListModelList<DATA> getModel( )
	{
		Object objModel = ( getListbox( ).getModel( ) );
		@SuppressWarnings( "unchecked" )
		ListModelList<DATA> model = (ListModelList<DATA>) objModel;
		if ( model == null ) {
			model = new ListModelList<DATA>( );
			getListbox( ).setModel( model );
		}
		return model;
	}

	private void setSelectedIndex( int nIndex )
	{
		if ( getListbox( ).getItemCount( ) > nIndex ) {
			getListbox( ).setSelectedIndex( nIndex );
			Events.sendEvent( getListbox( ), new Event( Events.ON_SELECT, getListbox( ) ) );
		}
		else {
			getListbox( ).clearSelection( );
			showRecord( null );
			enableItemButtons( false );
		}
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		enableItemButtons( false );
	}

	private void enableItemButtons( boolean bEnable )
	{
		if ( this.itemButtons != null )
		{
			for ( Button b : this.itemButtons )
			{
				b.setDisabled( !bEnable );
			}
		}
	}

	@Listen( "onClick=#cmdCancelAddress" )
	public void onCancel( MouseEvent evt )
	{
		enableOperations( true );
		showRecord( getSelected( ) );
		if ( evt != null )
			evt.stopPropagation( );
	}

	@Listen( "onClick=#cmdSaveAddress" )
	public void onSave( MouseEvent evt )
	{
		DATA newItem;

		if ( !bUpdate ) {
			newItem = createNew( );
		}
		else {
			newItem = getSelected( );
			update( newItem );
		}
		if ( newItem == null )
			return;
		if ( validate( newItem ) ) {
			enableOperations( true );
			if ( !bUpdate ) {
				getModel( ).add( newItem );
			}
			else {
				getModel( ).set( getListbox( ).getSelectedIndex( ), newItem );
			}
			showRecord( newItem );
		}
		if ( evt != null )
			evt.stopPropagation( );
	}

	private void enableOperations( boolean bEnable )
	{
		if ( divOperations != null ) {
			for ( Div item : divOperations ) {
				item.setVisible( bEnable );
			}
		}
		if ( divConfirmations != null ) {
			for ( Div item : divConfirmations ) {
				item.setVisible( !bEnable );
			}
		}
	}
}
