package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.web.renderer.inep.DistributionExtractRowRenderer;

public class DistributionExtractController extends BaseExtractController
{
	private static final long serialVersionUID = 4222122749896979237L;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.getDataGrid( ).setRowRenderer( new DistributionExtractRowRenderer( ) );
	}

	@Override
	protected List<InepAnaliticoCorrecao> getList( InepEvent item )
	{
		if ( this.getRevisor( ) == null || this.getRevisor( ).isCoordenador( ) ) {
			return this.getSession( ).getAnaliticoCorrecao( item );
		}
		else {
			return this.getSession( ).getAnaliticoCorrecao( this.getRevisor( ).getTask( ) );
		}
	}
}
