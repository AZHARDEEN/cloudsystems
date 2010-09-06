package br.com.mcampos.controller.admin.tables.core;


import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class SimpleTableListRenderer implements ListitemRenderer, Serializable
{
    public SimpleTableListRenderer()
    {
        super();
    }

    public void render( Listitem listitem, Object object ) throws Exception
    {
        SimpleTableDTO dto = ( SimpleTableDTO )object;

        if ( dto != null ) {
            listitem.getChildren().clear();
            listitem.getChildren().add( new Listcell( dto.getId() != null ? dto.getId().toString() : "N/D" ) );
            listitem.getChildren().add( new Listcell( SysUtils.isEmpty( dto.getDescription() ) == false ? dto.getDescription() :
                                                      "" ) );
        }
    }
}
