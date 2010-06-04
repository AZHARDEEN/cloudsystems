package br.com.mcampos.controller.admin.security.menu;


import br.com.mcampos.dto.system.MenuDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class MenuListRenderer implements ListitemRenderer
{
    public MenuListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        MenuDTO dto = ( MenuDTO )data;
        if ( dto != null ) {
            item.getChildren().add( new Listcell( dto.getId().toString() ) );
            item.getChildren().add( new Listcell( dto.getDescription() ) );
            item.getChildren().add( new Listcell( dto.getTargetURL() ) );
            if ( dto.getParent() != null )
                item.getChildren().add( new Listcell( dto.getParent().toString() ) );
        }
    }
}
