package br.com.mcampos.web.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.jpa.user.CollaboratorType;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class CollaboratorTypeListRenderer extends BaseListRenderer<CollaboratorType>
{

	@Override
	public void render( Listitem item, CollaboratorType data, int index ) throws Exception
	{
		super.render( item, data, index );
		this.addCell( item, data.getId( ).getId( ).toString( ) );
		this.addCell( item, data.getDescription( ) );
	}

}
