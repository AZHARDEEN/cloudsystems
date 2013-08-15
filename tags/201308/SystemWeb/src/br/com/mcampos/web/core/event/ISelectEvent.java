package br.com.mcampos.web.core.event;

import org.zkoss.zk.ui.event.SelectEvent;

public interface ISelectEvent
{
	public void onSelect( @SuppressWarnings("rawtypes") SelectEvent evt );
}
