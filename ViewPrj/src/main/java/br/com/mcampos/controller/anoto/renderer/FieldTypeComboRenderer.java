package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class FieldTypeComboRenderer implements ComboitemRenderer
{
	public FieldTypeComboRenderer( )
	{
		super( );
	}

	@Override
	public void render( Comboitem item, Object data, int index ) throws Exception
	{
		item.setValue( data );
		item.setLabel( data.toString( ) );
	}
}
