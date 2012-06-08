package br.com.mcampos.web.core.combobox;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.locator.ServiceLocator;

public abstract class ComboboxExt<BEAN, DATA> extends Combobox
{
	private static final Logger logger = LoggerFactory.getLogger( ComboboxExt.class );
	private static final long serialVersionUID = 5803878398952556887L;
	private Class<BEAN> persistentClass;
	private transient BEAN session = null;

	private List<DetailInterface> details;

	protected abstract Class<BEAN> getSessionClass( );

	protected abstract void load( );

	public ComboboxExt( )
	{
		super( );
		setPersistentClass( );
	}

	public ComboboxExt( String value ) throws WrongValueException
	{
		super( value );
		setPersistentClass( );
	}

	@Override
	public void setSelectedItem( Comboitem item )
	{
		logger.info( "setSelectedItem: " + item.getValue( ).toString( ) );
		super.setSelectedItem( item );
		processOnSelect( );
	}

	private void processOnSelect( )
	{
		logger.info( "processOnSelect: " + this.getClass( ).getSimpleName( ) );
		Events.sendEvent( this, new Event( Events.ON_SELECT, this ) );
	}

	public DATA getSelectedValue( )
	{
		Comboitem item = getSelectedItem( );

		if ( item != null && item.getValue( ) != null ) {
			return item.getValue( );
		}
		else {
			return null;
		}
	}

	public void load( List<DATA> list, DATA itemToSelect, boolean bSelectFirst )
	{
		getChildren( ).clear( );
		if ( SysUtils.isEmpty( list ) == false ) {
			Comboitem selectedItem = null;

			for ( DATA dto : list ) {
				Comboitem item = appendItem( dto.toString( ) );
				if ( item != null ) {
					item.setValue( dto );
					if ( itemToSelect != null && item.equals( itemToSelect ) ) {
						selectedItem = item;
					}
				}
			}
			if ( selectedItem != null ) {
				setSelectedItem( selectedItem );
			}
			else {
				if ( bSelectFirst ) {
					setSelectedIndex( 0 );
				}
			}
		}
	}

	protected void setPersistentClass( )
	{
		this.persistentClass = getSessionClass( );
	}

	@SuppressWarnings( "unchecked" )
	public BEAN getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = ( (BEAN) ServiceLocator.getInstance( ).getRemoteSession( this.persistentClass ) );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

	public void find( DATA item )
	{
		if ( getItemCount( ) <= 0 || item == null ) {
			return;
		}
		for ( Component child : getChildren( ) ) {
			if ( child instanceof Comboitem ) {
				Comboitem cbItem = (Comboitem) child;
				if ( cbItem.getValue( ).equals( item ) ) {
					setSelectedItem( cbItem );
					break;
				}
			}
		}
	}

	private List<DetailInterface> getDetails( )
	{
		if ( this.details == null ) {
			this.details = new ArrayList<DetailInterface>( );
		}
		return this.details;
	}

	public void addDetail( DetailInterface newDetail )
	{
		getDetails( ).add( newDetail );
	}

	public void onSelect( Event evt )
	{
		logger.info( "onSelect: " + evt.getTarget( ).getId( ) );
		for ( DetailInterface item : getDetails( ) ) {
			item.onChangeMaster( getSelectedValue( ) );
		}
		evt.stopPropagation( );
	}

	public void onCreate( Event evt )
	{
		if ( ( this instanceof DetailInterface ) == false )
		{
			logger.info( "onCreate: " + evt.getTarget( ).getId( ) );
			load( );
		}
	}
}
