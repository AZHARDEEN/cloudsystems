package br.com.mcampos.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Center;

import br.com.mcampos.controller.core.BaseController;

public class IndexController extends BaseController
{
	public static final String mdiApplicationParamName = "mdiApplication";

	Center mdiApplication;

	public IndexController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		onClick$loginMenuItem( );
	}

	public void onClick$loginMenuItem( )
	{
		gotoPage( "/login.zul", mdiApplication );
	}

	public void onClick$registerMenuItem( )
	{
		gotoPage( "/register.zul", mdiApplication );
	}

	public void onClick$resentEmailMenuItem( )
	{
		gotoPage( "/re_send_email.zul", mdiApplication );
	}

	public void onClick$validateEmailMenuItem( )
	{
		gotoPage( "/validate_email.zul", mdiApplication );
	}
}
