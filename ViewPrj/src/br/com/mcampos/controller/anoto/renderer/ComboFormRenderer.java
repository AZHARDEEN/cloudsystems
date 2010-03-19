package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class ComboFormRenderer implements ComboitemRenderer
{
    public ComboFormRenderer()
    {
        super();
    }

    public void render( Comboitem item, Object data ) throws Exception
    {
        item.setValue( data );
        item.setLabel( data.toString() );
    }
}