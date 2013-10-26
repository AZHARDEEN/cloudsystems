package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.anoto.AnotoPageDTO;

public class AnotoPageListRenderer implements ListitemRenderer
{
	public AnotoPageListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		AnotoPageDTO dto = (AnotoPageDTO) data;

		item.setValue( dto );
		item.getChildren( ).add( new Listcell( dto.getPageAddress( ) ) );
	}
}
