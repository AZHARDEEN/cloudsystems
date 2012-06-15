package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.web.inep.controller.renderer.DistributionExtractRowRenderer;

public class VarianceExtratController extends BaseExtractController
{
	private static final long serialVersionUID = 6069598689613106007L;

	@Override
	protected List<InepAnaliticoCorrecao> getList( InepPackage item )
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
