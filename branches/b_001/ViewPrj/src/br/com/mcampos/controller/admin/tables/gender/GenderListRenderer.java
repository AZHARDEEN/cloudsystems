package br.com.mcampos.controller.admin.tables.gender;


import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class GenderListRenderer implements ListitemRenderer, Serializable
{

    public GenderListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        GenderDTO dto = ( GenderDTO )data;
        item.setValue( data );
        Listcell cellId, cellDescription;

        if ( SysUtils.isEmpty( item.getChildren() ) ) {
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
        }
        cellId = ( Listcell )item.getChildren().get( 0 );
        cellDescription = ( Listcell )item.getChildren().get( 1 );
        if ( cellId != null )
            cellId.setLabel( dto.getId().toString() );
        if ( cellDescription != null )
            cellDescription.setLabel( dto.getDescription() );
    }
}
