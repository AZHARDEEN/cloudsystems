package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PgcFieldListRenderer implements ListitemRenderer
{
    public PgcFieldListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        PgcFieldDTO dto = ( PgcFieldDTO )data;
        int nIndex = 0;
        String sTime;

        if ( item.getChildren().size() == 0 ) {
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
        }
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getName() );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( SysUtils.isEmpty( dto.getRevisedText() ) ? dto.getIrcText() :
                                                                     dto.getRevisedText() );
        if ( dto.getEndTime() != null && dto.getStartTime() != null ) {
            Long diff = dto.getEndTime() - dto.getStartTime();
            Float diffSec = diff.floatValue() / 1000;
            sTime = diffSec.toString();
        }
        else {
            sTime = "";
        }
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( sTime );
    }
}
