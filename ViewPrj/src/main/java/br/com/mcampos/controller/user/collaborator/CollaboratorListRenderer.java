package br.com.mcampos.controller.user.collaborator;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import br.com.mcampos.controller.commom.AbstractLisrRenderer;
import br.com.mcampos.dto.user.collaborator.CollaboratorDTO;

public class CollaboratorListRenderer extends AbstractLisrRenderer
{
	public CollaboratorListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		item.setValue( data );
		if ( data == null )
			return;
		CollaboratorDTO u = (CollaboratorDTO) data;
		Listcell cellId, cellDescription;

		String name = u.getCollaborator( ).getName( );
		createCells( item );
		cellId = (Listcell) item.getChildren( ).get( 0 );
		cellDescription = (Listcell) item.getChildren( ).get( 1 );
		if ( cellId != null )
			cellId.setLabel( u.getSequence( ).toString( ) );
		if ( cellDescription != null )
			cellDescription.setLabel( name );
	}
}
