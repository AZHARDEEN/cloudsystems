package br.com.mcampos.web.inep.utils;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class InepStatusOralDistributionsListRenderer extends BaseListRenderer<InepOralDistribution>
{

	@Override
	public void render( Listitem item, InepOralDistribution data, int index ) throws Exception
	{
		super.render( item, data, index );
		String name = data.getRevisor( ).getCollaborator( ).getPerson( ).getName( );
		if ( data.getRevisor( ).isCoordenador( ).equals( true ) ) {
			name = name + "(C)";
		}
		addCell( item, name );
		addCell( item, data.getRevisor( ).getCollaborator( ).getId( ).getSequence( ).toString( ) );
		addCell( item, data.getStatus( ).getDescription( ) );
		Integer nota = data.getNota( );
		if ( nota == null ) {
			addCell( item, "" );
		}
		else {
			switch ( nota ) {
			case 6:
				addCell( item, "A" );
				break;
			case 7:
				addCell( item, "Br" );
				break;
			default:
				addCell( item, nota.toString( ) );
			}
		}
	}

}
