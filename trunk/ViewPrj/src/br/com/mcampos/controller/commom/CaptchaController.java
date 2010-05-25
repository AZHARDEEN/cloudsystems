package br.com.mcampos.controller.commom;


import br.com.mcampos.controller.core.BaseController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;

public class CaptchaController extends BaseController
{
	private Label captchaLabelMessage;
	private Label captchLabelValidation;
	private Button captchaBtnRegenerate;

	public CaptchaController()
	{
		super();
	}

	public CaptchaController( char c )
	{
		super( c );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setLabel( captchaLabelMessage );
		setLabel( captchLabelValidation );
		setLabel( captchaBtnRegenerate );
	}
}
