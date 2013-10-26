package br.com.mcampos.controller.admin.tables.core;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.sysutils.SysUtils;

public class SimpleTableListRenderer implements ListitemRenderer, Serializable
{
	public SimpleTableListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem listitem, Object object, int index ) throws Exception
	{
		SimpleTableDTO dto = (SimpleTableDTO) object;

		if ( dto != null ) {
			listitem.getChildren( ).clear( );
			listitem.getChildren( ).add( new Listcell( dto.getId( ) != null ? dto.getId( ).toString( ) : "N/D" ) );
			listitem.getChildren( ).add( new Listcell( SysUtils.isEmpty( dto.getDescription( ) ) == false ? dto.getDescription( ) :
					"" ) );
		}
	}
}
