package br.com.mcampos.web.renderer.inep;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import br.com.mcampos.jpa.inep.InepEvent;

public class PackageComboItemRenderer implements ComboitemRenderer<InepEvent>
{

	@Override
	public void render( Comboitem arg0, InepEvent arg1, int arg2 ) throws Exception
	{
		arg0.setValue( arg1 );
		arg0.setLabel( arg1.getId( ).getId( ).toString( ) );
	}

}
