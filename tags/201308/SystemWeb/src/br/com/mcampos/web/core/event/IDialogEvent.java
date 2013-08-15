package br.com.mcampos.web.core.event;

import java.io.Serializable;

import org.zkoss.zul.Window;

public interface IDialogEvent extends Serializable
{
	void onOK( Window wnd );
}
