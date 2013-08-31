package br.com.mcampos.web.renderer;

import java.util.Date;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import br.com.mcampos.entity.security.Login;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class LoginItemRenderer extends BaseListRenderer<Login>
{

	@Override
	public void render( Listitem item, Login data, int index ) throws Exception
	{
		super.render( item, data, index );

		addCell( item, data.getId( ) );
		addCell( item, data.getPerson( ).getName( ) );
		Listcell cell = addCell( item, data.getStatus( ).getDescription( ) );
		if ( data.getStatus( ).getId( ).equals( 1 ) == false ) {
			cell.setStyle( "color:red" );
		}
		cell = addCell( item, SysUtils.formatDate( data.getExpDate( ), "dd/MM/yyyy" ) );
		if ( data.getExpDate( ).compareTo( new Date( ) ) < 0 ) {
			cell.setStyle( "color:red" );
		}
		cell = addCell( item, data.getTryCount( ).toString( ) );
		if ( data.getTryCount( ).compareTo( 5 ) > 0 ) {
			cell.setStyle( "color:red" );
		}
		addCell( item, data.getPerson( ).getDocument( ) );
		addCell( item, data.getPerson( ).getEmail( ) );

		item.setContext( "popupOptions" );
	}

}
