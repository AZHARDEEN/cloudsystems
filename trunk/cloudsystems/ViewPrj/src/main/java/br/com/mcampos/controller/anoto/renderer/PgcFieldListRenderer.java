package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.anoto.PgcFieldDTO;

public class PgcFieldListRenderer implements ListitemRenderer
{
	public PgcFieldListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		item.setValue( data );
		PgcFieldDTO dto = (PgcFieldDTO) data;
		int nIndex = 0;
		String sTime;

		if ( item.getChildren( ).size( ) == 0 ) {
			item.appendChild( new Listcell( ) );
			item.appendChild( new Listcell( ) );
			item.appendChild( new Listcell( ) );
		}
		( (Listcell) item.getChildren( ).get( nIndex++ ) ).setLabel( dto.getName( ) );
		( (Listcell) item.getChildren( ).get( nIndex++ ) ).setLabel( dto.getValue( ) );
		if ( dto.getEndTime( ) != null && dto.getStartTime( ) != null ) {
			Long diff = dto.getEndTime( ) - dto.getStartTime( );
			Float diffSec = diff.floatValue( ) / 1000;
			sTime = diffSec.toString( );
		}
		else
			sTime = "";
		( (Listcell) item.getChildren( ).get( nIndex++ ) ).setLabel( sTime );
	}
}
