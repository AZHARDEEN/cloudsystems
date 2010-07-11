package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.PenUserDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;


public class PenUserRowRenderer implements RowRenderer, Serializable
{
    public PenUserRowRenderer()
    {
        super();
    }

    public void render( Row row, Object data ) throws Exception
    {
        if ( row.getChildren() != null && row.getChildren().size() > 0 )
            row.getChildren().clear();
        row.setValue( data );
        if ( data == null )
            return;
        PenUserDTO dto = ( PenUserDTO )data;
        new Label( dto.getPenId() ).setParent( row );
        if ( dto.getUser() != null ) {
            new Label( dto.getUser().getName() ).setParent( row );
            new Label( "-" ).setParent( row );
        }
        else {
            new Label( "-" ).setParent( row );
            new Label( "-" ).setParent( row );
        }
        new Label( dto.getFromDate() != null ? SysUtils.formatDate( dto.getFromDate() ) : "-" ).setParent( row );
    }

}
