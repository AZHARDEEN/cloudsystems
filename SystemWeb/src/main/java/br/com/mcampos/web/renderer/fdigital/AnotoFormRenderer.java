package br.com.mcampos.web.renderer.fdigital;

import org.zkoss.zul.Listitem;

import br.com.mcampos.entity.fdigital.AnotoForm;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class AnotoFormRenderer extends BaseListRenderer<AnotoForm> {

	@Override
	public void render(Listitem item, AnotoForm data, int index) throws Exception 
	{
		super.render(item, data, index);
		addCell(item, data.getId().toString() );
		addCell(item, data.getApplication() );
		addCell(item, data.getDescription() );
	}

}
