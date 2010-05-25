package br.com.mcampos.controller.admin.tables.title;


import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class TitleListRenderer implements ListitemRenderer, Serializable
{
    public TitleListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        TitleDTO dto = ( TitleDTO )data;
        item.setValue( data );
        Listcell cellId, cellDescription, cellAbbrev;

        createCells( item );
        cellId = ( Listcell )item.getChildren().get( 0 );
        cellDescription = ( Listcell )item.getChildren().get( 1 );
        cellAbbrev = ( Listcell )item.getChildren().get( 2 );
        if ( cellId != null )
            cellId.setLabel( dto.getId().toString() );
        if ( cellDescription != null )
            cellDescription.setLabel( dto.getDescription() );
        if ( cellAbbrev != null )
            cellAbbrev.setLabel( dto.getAbbreviation() );
    }


    private void createCells( Listitem item )
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
