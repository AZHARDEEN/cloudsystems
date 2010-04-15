package br.com.mcampos.controller.admin.security.roles;


import br.com.mcampos.dto.security.RoleDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class RoleListRenderer implements ListitemRenderer
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
            if ( dto.getParent() != null )
                item.getChildren().add( new Listcell( dto.getParent().toString() ) );
        }
    }
}
