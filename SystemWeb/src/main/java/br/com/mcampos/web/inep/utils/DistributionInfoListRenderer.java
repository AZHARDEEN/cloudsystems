package br.com.mcampos.web.inep.utils;

import org.zkoss.zul.Listitem;

import br.com.mcampos.entity.inep.InepDistribution;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class DistributionInfoListRenderer extends BaseListRenderer<InepDistribution>
{

	@Override
	public void render( Listitem item, InepDistribution data, int index ) throws Exception
	{
		super.render( item, data, index );
		if ( data.getStatus( ).getId( ).equals( 3 ) || data.getStatus( ).getId( ).equals( 4 ) ) {
			if ( data.getNota( ) != null ) {
				if ( data.getRevisor( ).isCoordenador( ).equals( false ) ) {
					item.setSclass( "varianceStyle" );
				}
				else {
					item.setSclass( "revisedStyle" );
				}
			}
		}
		addCell( item, data.getTest( ).getTask( ).getId( ).getId( ).toString( ) );
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
		addCell( item, SysUtils.formatDate( data.getInsertDate( ) ) );
		addCell( item, SysUtils.formatDate( data.getUpdateDate( ) ) );
	}
}
