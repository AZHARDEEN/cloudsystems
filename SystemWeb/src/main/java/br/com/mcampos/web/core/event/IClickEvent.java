package br.com.mcampos.web.core.event;

import java.io.Serializable;

import org.zkoss.zk.ui.event.MouseEvent;

public interface IClickEvent extends Serializable
{
	void onClick( MouseEvent evt );
}
