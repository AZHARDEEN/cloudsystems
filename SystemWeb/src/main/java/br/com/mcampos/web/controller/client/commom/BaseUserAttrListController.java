package br.com.mcampos.web.controller.client.commom;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.core.event.DialogEvent;

public abstract class BaseUserAttrListController<DATA> extends BaseController<Component>
{
	private static final long serialVersionUID = 993535382863182500L;
	private static final Logger logger = LoggerFactory.getLogger( BaseUserAttrListController.class );

	@Wire( "#cmdUpdate, #cmdDelete" )
	private Button[ ] itemButtons;

	@Wire( "#lstItem" )
	private Listbox listbox;

	protected abstract String getDialogPath( );

	@Listen( "onSelect = listbox" )
	public void onSelect( Event evt )
	{
		Listitem selectedItem;

		selectedItem = getListbox( ).getSelectedItem( );
		if( selectedItem != null && selectedItem.getValue( ) != null ) {
			DATA value = selectedItem.getValue( );
			enableItemButtons( true );
			if( value instanceof UserDocument ) {
				UserDocument d = (UserDocument) value;
				if( d.getType( ).getId( ).equals( UserDocument.CPF ) || d.getType( ).getId( ).equals( UserDocument.EMAIL ) ) {
					for( Button b : itemButtons ) {
						if( b.getId( ).equals( "removeDocument" ) ) {
							b.setDisabled( true );
						}
					}
				}
			}
		}
		if( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onAfterRender=listbox" )
	public void afterRender( Event evt )
	{
		if( evt != null ) {
			if( evt.getTarget( ) instanceof Listbox ) {
				Listbox l = (Listbox) evt.getTarget( );
				if( l.getItemCount( ) > 0 ) {
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
		if( item != null ) {
			return item.getValue( );
		}
		else {
			return null;
		}
	}

	protected ListModelList<DATA> getModel( )
	{
		Object objModel = (getListbox( ).getModel( ));
		@SuppressWarnings( "unchecked" )
		ListModelList<DATA> model = (ListModelList<DATA>) objModel;
		if( model == null ) {
			model = new ListModelList<DATA>( );
			getListbox( ).setModel( model );
		}
		return model;
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		subscribe( );
		enableItemButtons( false );
	}

	private void enableItemButtons( boolean bEnable )
	{
		if( itemButtons != null )
		{
			for( Button b : itemButtons )
			{
				b.setDisabled( !bEnable );
			}
		}
	}

	protected Listbox getListbox( )
	{
		return listbox;
	}

	private void subscribe( )
	{
		EventQueues.lookup( DialogEvent.eventName, EventQueues.DESKTOP, true ).subscribe( new EventListener<Event>( )
		{
			@SuppressWarnings( "unchecked" )
			@Override
			public void onEvent( Event evt )
			{
				if( evt instanceof DialogEvent ) {
					DialogEvent<Window> dlgEvent = (DialogEvent<Window>) evt;
					try {
						onDialog( dlgEvent );
					}
					catch( Exception e ) {
						logger.error( "Subscribe error", e );
					}
				}
			}
		} );
	}

	@SuppressWarnings( "unchecked" )
	private void onDialog( DialogEvent<Window> evt )
	{
		if( evt != null ) {
			if( evt.getTarget( ) == getMainWindow( ) ) {
				if( evt.isUpdate( ) ) {
					int index = evt.getListIndex( );
					if( index >= 0 ) {
						getModel( ).set( index, (DATA) evt.getData( ) );
					}
				}
				else {
					getModel( ).add( (DATA) evt.getData( ) );
				}
				evt.stopPropagation( );
			}
			enableItemButtons( getListbox( ).getItemCount( ) > 0 );
		}
	}

	@Listen( "onClick=#cmdDelete" )
	public void onDelete( MouseEvent evt )
	{
		Messagebox.show( "Deseja excluir o item selecionado", "Exclusão", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, 2, new EventListener<Event>( )
				{
					@Override
					public void onEvent( Event event ) throws Exception
					{
						getModel( ).remove( getSelected( ) );
						enableItemButtons( getListbox( ).getItemCount( ) > 0 );
					}
				} );
	}

	@Listen( "onClick=#cmdCreate" )
	public void onAddNew( MouseEvent evt )
	{
		showDialog( null, false );
		if( evt != null )
			evt.stopPropagation( );
	}

	@Listen( "onClick=#cmdUpdate;onDoubleClick=listbox" )
	public void onUpdate( MouseEvent evt )
	{
		showDialog( getSelected( ), true );
		if( evt != null )
			evt.stopPropagation( );
	}

	private void showDialog( DATA data, boolean bUpdate )
	{
		Map<String, Object> param = new HashMap<String, Object>( );
		Window main = getMainWindow( );

		param.put( "targetWindow", main );
		param.put( "entity", data );
		param.put( "isUpdate", new Boolean( bUpdate ) );
		param.put( "listIndex", new Integer( getListbox( ).getSelectedIndex( ) ) );
		Component c = createComponents( getDialogPath( ), main, param );
		if( c instanceof Window ) {
			Window w = (Window) c;
			w.doModal( );
		}
		else {
			c.detach( );
		}
	}

}
