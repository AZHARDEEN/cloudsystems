package br.com.mcampos.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;

import br.com.mcampos.controller.core.BaseController;

public class SouthController extends BaseController
{
	private Label labelCopyright;

	public SouthController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Component component ) throws Exception
	{
		super.doAfterCompose( component );
		setLabel( labelCopyright );
	}
}
