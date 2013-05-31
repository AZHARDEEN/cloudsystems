package br.com.mcampos.web.inep.controller.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class RevisorListRenderer extends BaseListRenderer<InepRevisor>
{

	@Override
	public void render( Listitem item, InepRevisor data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getCollaborator( ).getId( ).getSequence( ) );
		addCell( item, data.getCollaborator( ).getPerson( ).getName( ) );
		addCell( item, data.getTask( ) != null ? data.getTask( ).getDescription( ) : "Oral" );
		addCell( item, data.isCoordenador( ) ? "SIM" : "" );
	}
}
