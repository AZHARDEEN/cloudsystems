package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.web.renderer.inep.DistributionExtractRowRenderer;

public class DistributionExtractController extends BaseExtractController
{
	private static final long serialVersionUID = 4222122749896979237L;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getDataGrid( ).setRowRenderer( new DistributionExtractRowRenderer( ) );
	}

	@Override
	protected List<InepAnaliticoCorrecao> getList( InepPackage item )
	{
		if ( getRevisor( ) == null || getRevisor( ).isCoordenador( ) ) {
			return getSession( ).getAnaliticoCorrecao( item );
		}
		else {
			return getSession( ).getAnaliticoCorrecao( getRevisor( ).getTask( ) );
		}
	}
}
