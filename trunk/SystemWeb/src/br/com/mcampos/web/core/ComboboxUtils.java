package br.com.mcampos.web.core;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.sysutils.SysUtils;

public final class ComboboxUtils
{
	public static void load( Combobox combo, List<SimpleDTO> list, boolean selectFirst )
	{
		clear( combo );
		if ( SysUtils.isEmpty( list ) == false ) {
			for ( SimpleDTO dto : list ) {
				Comboitem item = combo.appendItem( dto.getDescription( ) );
				if ( item != null ) {
					item.setValue( dto );
				}
			}
			if ( selectFirst && combo.getChildren( ).size( ) > 0 ) {
				combo.setSelectedIndex( 0 );
			}
		}
	}

	public static <DATA> DATA getValue( Combobox c )
	{
		try {
			if ( c != null ) {
				Comboitem item = c.getSelectedItem( );
				if ( item != null && item.getValue( ) != null )
				{
					@SuppressWarnings( "unchecked" )
					DATA value = (DATA) item.getValue( );
					return value;
				}
			}
			return null;
		}
		catch ( Exception e )
		{
			e = null;
			return null;
		}
	}

	public static <DATA> void load( Combobox c, List<DATA> list, DATA itemToSelect, boolean bSelectFirst )
	{
		clear( c );
		if ( SysUtils.isEmpty( list ) == false ) {
			Comboitem selectedItem = null;

			for ( DATA dto : list ) {
				Comboitem item = c.appendItem( dto.toString( ) );
				if ( item != null ) {
					item.setValue( dto );
					if ( itemToSelect != null && dto.equals( itemToSelect ) ) {
						selectedItem = item;
					}
				}

			}
			if ( selectedItem != null ) {
				select( c, selectedItem );
			}
			else {
				if ( bSelectFirst == true ) {
					select( c, 0 );
				}
			}
		}
	}

	public static void clear( Combobox c )
	{
		if ( c != null && SysUtils.isEmpty( c.getChildren( ) ) == false ) {
			c.getChildren( ).clear( );
		}
	}

	public static <DATA> void find( Combobox c, DATA item )
	{
		if ( c == null || c.getItemCount( ) <= 0 || item == null ) {
			return;
		}
		for ( Component child : c.getChildren( ) ) {
			if ( child instanceof Comboitem ) {
				Comboitem cbItem = (Comboitem) child;
				if ( cbItem.getValue( ).equals( item ) ) {
					select( c, cbItem );
					break;
				}
			}
		}
	}

	public static void select( Combobox c, int nIndex )
	{
		if ( c != null && c.getItemCount( ) > nIndex ) {
			c.setSelectedIndex( nIndex );
			Events.sendEvent( c, new Event( Events.ON_SELECT, c ) );
		}
	}

	public static void select( Combobox c, Comboitem item )
	{
		if ( c != null && c.getItemCount( ) > 0 && item != null ) {
			c.setSelectedItem( item );
			if ( c.getSelectedItem( ).equals( item ) ) {
				Events.sendEvent( c, new Event( Events.ON_SELECT, c ) );
			}
		}
	}
}
