package br.com.mcampos.web.renderer.inep;

import org.zkoss.zul.Listitem;

import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class SubscriptionItemRenderer extends BaseListRenderer<InepSubscription>
{

	@Override
	public void render( Listitem item, InepSubscription data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getId( ).getId( ) );
	}

}
