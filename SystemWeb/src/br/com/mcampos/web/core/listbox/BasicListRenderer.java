package br.com.mcampos.web.core.listbox;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.ejb.core.BasicEntityRenderer;
import br.com.mcampos.sysutils.SysUtils;

public class BasicListRenderer implements ListitemRenderer<BasicEntityRenderer<?>>
{
	public BasicListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, BasicEntityRenderer<?> data, int Index )
	{
		if ( item == null || data == null ) {
			return;
		}
		List<Component> headers = getHeader( item );
		if ( item.getChildren( ) != null ) {
			item.getChildren( ).clear( );
		}
		if ( headers != null && headers.size( ) > 0 ) {
			/*
			 * for ( int nIndex = 0; nIndex < headers.size(); nIndex++ ) {
			 * item.appendChild( new Listcell( data.getField( nIndex ) ) ); }
			 */
			for ( Component comp : headers ) {
				Listheader header = (Listheader) comp;
				String fieldName = header.getValue( );
				if ( SysUtils.isEmpty( fieldName ) == false ) {
					fieldName = "get" + fieldName.substring( 0, 1 ).toUpperCase( ) + fieldName.substring( 1 );
					Object value;
					try {
						value = SysUtils.invokeMethod( data, fieldName, null );
					}
					catch ( Exception e ) {
						e = null;
						value = null;
					}
					addCell( item, value );
				}
			}
		}
		else {
			item.appendChild( new Listcell( data.toString( ) ) );
		}
		item.setValue( data );
	}

	private List<Component> getHeader( Listitem item )
	{
		Listbox listbox = item.getListbox( );

		return listbox.getListhead( ).getChildren( );
	}

	private void addCell( Listitem item, Object value )
	{
		String str = "";

		if ( value != null ) {
			if ( value instanceof String ) {
				str = (String) value;
			}
			else {
				str = value.toString( );
			}
		}
		item.appendChild( new Listcell( str ) );
	}

}
