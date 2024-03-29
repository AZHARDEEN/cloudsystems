package br.com.mcampos.web.renderer.inep;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.inep.InepOralTeamDTO;
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
