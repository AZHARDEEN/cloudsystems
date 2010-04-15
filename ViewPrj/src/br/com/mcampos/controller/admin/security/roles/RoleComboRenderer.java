package br.com.mcampos.controller.admin.security.roles;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class RoleComboRenderer implements ComboitemRenderer
{
    public RoleComboRenderer()
    {
        super();
    }

    public void render( Comboitem item, Object data )
    {
        item.setValue( data );
        if ( data != null ) {
            item.setLabel( data.toString() );
        }
    }
}
