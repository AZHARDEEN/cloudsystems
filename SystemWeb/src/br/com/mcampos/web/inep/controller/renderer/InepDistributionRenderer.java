package br.com.mcampos.web.inep.controller.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class InepDistributionRenderer extends BaseListRenderer<InepDistribution>
{
	@Override
	public void render( Listitem item, InepDistribution data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getTest( ).getSubscription( ).getId( ).getId( ) );
		addCell( item, data.getTest( ).getTask( ).getId( ).getId( ).toString( ) );
	}

}
