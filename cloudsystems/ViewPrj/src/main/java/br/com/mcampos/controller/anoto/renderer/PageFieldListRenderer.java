package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;

public class PageFieldListRenderer implements ListitemRenderer
{
	public PageFieldListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		item.setValue( data );
		AnotoPageFieldDTO dto = (AnotoPageFieldDTO) data;
		int nIndex = 0;
		if ( item.getChildren( ).size( ) == 0 ) {
			item.appendChild( new Listcell( ) );
			item.appendChild( new Listcell( ) );
			item.appendChild( new Listcell( ) );
			item.appendChild( new Listcell( ) );
		}
		( (Listcell) item.getChildren( ).get( nIndex++ ) ).setLabel( "" + ( item.getListbox( ).getIndexOfItem( item ) + 1 ) );
		( (Listcell) item.getChildren( ).get( nIndex++ ) ).setLabel( dto.getName( ) );
		( (Listcell) item.getChildren( ).get( nIndex++ ) ).setLabel( dto.getType( ).getDescription( ) );
		( (Listcell) item.getChildren( ).get( nIndex++ ) ).setLabel( dto.getIcr( ) ? "SIM" : "NÃO" );
	}
}
