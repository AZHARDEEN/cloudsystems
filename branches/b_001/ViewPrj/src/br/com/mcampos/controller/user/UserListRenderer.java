package br.com.mcampos.controller.user;


import br.com.mcampos.controller.commom.AbstractLisrRenderer;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class UserListRenderer extends AbstractLisrRenderer
{
    public UserListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        if ( data == null )
            return;
        ListUserDTO u = ( ListUserDTO )data;
        Listcell cellId, cellDescription;

        String name = SysUtils.isEmpty( u.getNickName() ) ? u.getName() : u.getNickName();
        createCells( item );
        cellId = ( Listcell )item.getChildren().get( 0 );
        cellDescription = ( Listcell )item.getChildren().get( 1 );
        if ( cellId != null )
            cellId.setLabel( u.getId().toString() );
        if ( cellDescription != null )
            cellDescription.setLabel( name );
    }
}
