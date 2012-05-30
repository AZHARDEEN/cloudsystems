package br.com.mcampos.controller.admin.security.roles;


import br.com.mcampos.controller.commom.AbstractLisrRenderer;
import br.com.mcampos.dto.security.RoleDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class RoleListRenderer extends AbstractLisrRenderer
{
    public RoleListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        RoleDTO dto = ( RoleDTO )data;
        if ( dto != null ) {
            item.getChildren().add( new Listcell( dto.getId().toString() ) );
            item.getChildren().add( new Listcell( dto.getDescription() ) );
            item.getChildren().add( new Listcell( ( dto.getParent() != null ) ? dto.getParent().toString() : " " ) );
        }
    }
}
