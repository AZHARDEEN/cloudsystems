package br.com.mcampos.controller.commom;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;

import br.com.mcampos.controller.core.BaseController;

public class SubmitCancelController extends BaseController
{
	private Button cmdCancel;
	private Button cmdSubmit;

	public SubmitCancelController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setLabel( cmdCancel );
		setLabel( cmdSubmit );
	}
}
