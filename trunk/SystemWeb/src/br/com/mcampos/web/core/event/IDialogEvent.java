package br.com.mcampos.web.core.event;

import java.io.Serializable;

import org.zkoss.zk.ui.Component;

import br.com.mcampos.web.core.BaseController;

public interface IDialogEvent extends Serializable
{
	void onOK( BaseController<? extends Component> wndController );
}
