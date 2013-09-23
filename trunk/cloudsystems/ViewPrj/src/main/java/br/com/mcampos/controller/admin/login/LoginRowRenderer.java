package br.com.mcampos.controller.admin.login;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import br.com.mcampos.dto.user.login.ListLoginDTO;

public class LoginRowRenderer implements RowRenderer
{
	public LoginRowRenderer( )
	{
		super( );
	}

	@Override
	public void render( Row row, Object data, int index ) throws Exception
	{
		row.setValue( data );
		if ( data == null )
			return;
		ListLoginDTO dto = (ListLoginDTO) data;
		row.getChildren( ).clear( );
		new Label( dto.getId( ).toString( ) ).setParent( row );
		new Label( dto.getName( ) ).setParent( row );
		new Label( dto.getUserStatus( ).toString( ) ).setParent( row );
	}
}
