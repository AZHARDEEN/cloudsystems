package br.com.mcampos.web.core;

import java.util.List;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import br.com.mcampos.ejb.core.SimpleDTO;
import br.com.mcampos.sysutils.SysUtils;

public final class ComboboxUtils
{
	public static void load ( Combobox combo, List<SimpleDTO> list, boolean selectFirst )
	{
		if ( combo.getChildren() != null ) {
			combo.getChildren().clear();
		}
		if ( SysUtils.isEmpty( list ) == false ) {
			for ( SimpleDTO dto : list ) {
				Comboitem item = combo.appendItem( dto.getDescription( ));
				if ( item != null ) {
					item.setValue( dto );
				}
			}
			if ( selectFirst && combo.getChildren( ).size( ) > 0 ) {
				combo.setSelectedIndex( 0 );
			}
		}
	}
}
