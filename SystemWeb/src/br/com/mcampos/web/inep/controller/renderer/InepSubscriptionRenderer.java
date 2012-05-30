package br.com.mcampos.web.inep.controller.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.inep.subscription.InepSubscription;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class InepSubscriptionRenderer extends BaseListRenderer<InepSubscription>
{
	@Override
	public void render( Listitem item, InepSubscription data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getId( ).getId( ) );
	}

}
