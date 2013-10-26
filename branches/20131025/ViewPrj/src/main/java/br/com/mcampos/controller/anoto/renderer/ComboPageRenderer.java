package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import br.com.mcampos.dto.anoto.AnotoPageDTO;
import br.com.mcampos.sysutils.SysUtils;

public class ComboPageRenderer implements ComboitemRenderer
{
	public ComboPageRenderer( )
	{
		super( );
	}

	@Override
	public void render( Comboitem item, Object data, int index ) throws Exception
	{
		item.setValue( data );
		AnotoPageDTO dto = (AnotoPageDTO) data;
		if ( SysUtils.isEmpty( dto.getDescription( ) ) )
			item.setLabel( dto.getPageAddress( ) );
		else
			item.setLabel( dto.getDescription( ) );
	}
}
