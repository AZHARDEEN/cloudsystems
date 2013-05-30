package br.com.mcampos.web.inep.controller.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.dto.inep.InepOralTeamDTO;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class InepOralTeamDTOListRenderer extends BaseListRenderer<InepOralTeamDTO>
{
	@Override
	public void render( Listitem item, InepOralTeamDTO data, int index ) throws Exception
	{
		super.render( item, data, index );
		if ( data.getRevisor( ) != null ) {
			addCell( item, data.getRevisor( ).getCollaborator( ).getPerson( ).getName( ) );
			addCell( item, data.getTests( ).toString( ) );
		}
	}

}
