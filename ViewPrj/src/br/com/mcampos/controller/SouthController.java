package br.com.mcampos.controller;


import br.com.mcampos.controller.core.BaseController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;

public class SouthController extends BaseController
{
	private Label labelCopyright;

	public SouthController()
	{
		super();
	}

	public SouthController( char c )
	{
		super( c );
	}

	@Override
	public void doAfterCompose( Component component ) throws Exception
	{
		super.doAfterCompose( component );
		setLabel( labelCopyright );
	}
}
