package br.com.mcampos.web.inep.utils;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import br.com.mcampos.ejb.inep.packs.InepPackage;

public class PackageComboItemRenderer implements ComboitemRenderer<InepPackage>
{

	@Override
	public void render( Comboitem arg0, InepPackage arg1, int arg2 ) throws Exception
	{
		arg0.setValue( arg1 );
		arg0.setLabel( arg1.getId( ).getId( ).toString( ) );
	}

}
