package br.com.mcampos.web.inep.utils;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import br.com.mcampos.ejb.inep.revisor.InepRevisor;

public class RevisorComboItemRenderer implements ComboitemRenderer<InepRevisor>
{

	@Override
	public void render( Comboitem arg0, InepRevisor arg1, int arg2 ) throws Exception
	{
		arg0.setValue( arg1 );
		arg0.setLabel( arg1.getCollaborator( ).getPerson( ).getName( ) );
	}

}
