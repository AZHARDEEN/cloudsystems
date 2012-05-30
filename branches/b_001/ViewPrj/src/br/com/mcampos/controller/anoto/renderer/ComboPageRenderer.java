package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class ComboPageRenderer implements ComboitemRenderer
{
    public ComboPageRenderer()
    {
        super();
    }

    public void render( Comboitem item, Object data ) throws Exception
    {
        item.setValue( data );
        AnotoPageDTO dto = ( AnotoPageDTO )data;
        if ( SysUtils.isEmpty( dto.getDescription() ) )
            item.setLabel( dto.getPageAddress() );
        else
            item.setLabel( dto.getDescription() );
    }
}
