package br.com.mcampos.controller.admin.tables.title;


import br.com.mcampos.controller.commom.AbstractLisrRenderer;
import br.com.mcampos.dto.user.attributes.TitleDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;


public class TitleListRenderer extends AbstractLisrRenderer
{
	public TitleListRenderer()
	{
		super();
	}

	public void render( Listitem item, Object data ) throws Exception
	{
		TitleDTO dto = ( TitleDTO )data;
		item.setValue( data );
		Listcell cellId, cellDescription, cellAbbrev;

		createCells( item );
		cellId = ( Listcell )item.getChildren().get( 0 );
		cellDescription = ( Listcell )item.getChildren().get( 1 );
		cellAbbrev = ( Listcell )item.getChildren().get( 2 );
		if ( cellId != null )
			cellId.setLabel( dto.getId().toString() );
		if ( cellDescription != null )
			cellDescription.setLabel( dto.getDescription() );
		if ( cellAbbrev != null )
			cellAbbrev.setLabel( dto.getAbbreviation() );
	}
}
