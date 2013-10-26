package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class ComboPenRenderer implements ComboitemRenderer
{
	public ComboPenRenderer( )
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
