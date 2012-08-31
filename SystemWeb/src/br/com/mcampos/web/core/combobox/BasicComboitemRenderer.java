package br.com.mcampos.web.core.combobox;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import br.com.mcampos.ejb.core.SimpleEntity;

public class BasicComboitemRenderer implements ComboitemRenderer<SimpleEntity<?>>
{

	@Override
	public void render( Comboitem item, SimpleEntity<?> entity, int index ) throws Exception
	{
		item.setValue( entity );
		item.setLabel( entity.getCode( ) );
	}

}
