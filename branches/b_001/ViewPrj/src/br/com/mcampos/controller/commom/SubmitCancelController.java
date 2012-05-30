package br.com.mcampos.controller.commom;


import br.com.mcampos.controller.core.BaseController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;

public class SubmitCancelController extends BaseController
{
	private Button cmdCancel;
	private Button cmdSubmit;

	public SubmitCancelController( char c )
	{
		super( c );
	}

	public SubmitCancelController()
	{
		super();
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setLabel( cmdCancel );
		setLabel( cmdSubmit );
	}
}
