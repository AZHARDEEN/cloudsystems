package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.commom.AbstractLisrRenderer;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class ClientListRenderer extends AbstractLisrRenderer
{
    public ClientListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        if ( data == null )
            return;
        ClientDTO u = ( ClientDTO )data;
        Listcell cellId, cellDescription;

        String name = SysUtils.isEmpty( u.getClient().getNickName() ) ? u.getClient().getName() : u.getClient().getNickName();
        createCells( item );
        cellId = ( Listcell )item.getChildren().get( 0 );
        cellDescription = ( Listcell )item.getChildren().get( 1 );
        if ( cellId != null )
            cellId.setLabel( u.getClient().getId().toString() );
        if ( cellDescription != null )
            cellDescription.setLabel( name );
    }
}
