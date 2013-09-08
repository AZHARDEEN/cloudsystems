package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.web.renderer.inep.DistributionExtractRowRenderer;

public class VarianceExtratController extends BaseExtractController
{
	private static final long serialVersionUID = 6069598689613106007L;

	@Override
	protected List<InepAnaliticoCorrecao> getList( InepEvent item )
	{
		return getSession( ).getAnaliticoDivergencia( item );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getDataGrid( ).setRowRenderer( new DistributionExtractRowRenderer( ) );
	}

}
