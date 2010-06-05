package br.com.mcampos.controller.commom;


import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public abstract class AbstractLisrRenderer implements ListitemRenderer, Serializable
{
	public AbstractLisrRenderer()
	{
		super();
	}

	protected void createCells( Listitem item )
	{
		if ( SysUtils.isEmpty( item.getChildren() ) ) {
			Listhead head = item.getListbox().getListhead();
			if ( head != null && head.getChildren() != null ) {
				for ( int cells = 0; cells < head.getChildren().size(); cells++ )
					item.appendChild( new Listcell() );
			}
		}
	}

}
