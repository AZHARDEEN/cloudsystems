package br.com.mcampos.web.core.event;

import java.io.Serializable;

import br.com.mcampos.web.core.BaseController;

public interface IDialogEvent extends Serializable
{
	void onOK( BaseController wndController );
}
