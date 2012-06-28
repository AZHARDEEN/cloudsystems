package br.com.mcampos.web.core.listbox;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public abstract class BaseListRenderer<T> implements ListitemRenderer<T>
{

	protected void addCell( Listitem item, Object value )
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

	protected void clear( Listitem item )
	{
		if ( item != null && item.getChildren( ) != null ) {
			item.getChildren( ).clear( );
		}
	}

	@Override
	public void render( Listitem item, T data, int index ) throws Exception
	{
		if ( item == null || data == null ) {
			return;
		}
		clear( item );
		item.setValue( data );
	}

}
