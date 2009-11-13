package br.com.mcampos.controller.admin.clients;

import br.com.mcampos.dto.user.ListUserDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class BusinessEntityListRenderer implements ListitemRenderer
{
    public BusinessEntityListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        ListUserDTO usr = ( ListUserDTO ) data;
        
        item.setValue( usr );
        item.getChildren().add( new Listcell ( usr.getId ().toString() ) );
        item.getChildren().add( new Listcell ( usr.getDisplayName() ) );
    }
}
