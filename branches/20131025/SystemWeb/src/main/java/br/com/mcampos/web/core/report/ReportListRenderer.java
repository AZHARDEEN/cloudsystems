package br.com.mcampos.web.core.report;

import org.zkoss.zul.Listitem;

import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class ReportListRenderer extends BaseListRenderer<ReportItem>
{

	@Override
	public void render( Listitem item, ReportItem data, int index ) throws Exception
	{
		super.render( item, data, index );

		addCell( item, data.getName( ) );
	}

}
