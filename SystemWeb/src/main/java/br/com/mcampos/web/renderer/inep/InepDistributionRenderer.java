package br.com.mcampos.web.renderer.inep;

import org.zkoss.zul.Listitem;

import br.com.mcampos.entity.inep.InepDistribution;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class InepDistributionRenderer extends BaseListRenderer<InepDistribution>
{
	@Override
	public void render( Listitem item, InepDistribution data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getTest( ).getSubscription( ).getId( ).getId( ) );
		addCell( item, data.getTest( ).getTask( ).getId( ).getId( ).toString( ) );
		String descNota = "";
		if ( data.getNota( ) != null )
		{
			switch ( data.getNota( ) )
			{
			case 6:
				descNota = "Anulado";
				break;
			case 7:
				descNota = "Em Branco";
				break;
			default:
				descNota = data.getNota( ).toString( );
				break;
			}
		}
		else {
			addCell( item, "" );
		}
		addCell( item, descNota );
		addCell( item, SysUtils.isEmpty( data.getObs( ) ) ? "" : "SIM" );
	}

}
