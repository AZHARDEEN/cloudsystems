package br.com.mcampos.controller.typing;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.typing.iab.IabProblemsDTO;
import br.com.mcampos.sysutils.SysUtils;

public class IabProblemsListRenderer implements ListitemRenderer, Serializable
{
	public IabProblemsListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem listitem, Object object, int index ) throws Exception
	{
		listitem.setValue( object );
		IabProblemsDTO dto = (IabProblemsDTO) object;
		if ( listitem.getChildren( ) != null )
			listitem.getChildren( ).clear( );
		new Listcell( dto.getId( ).toString( ) ).setParent( listitem );
		new Listcell( ( dto.getUser_1( ) == null ) ? "" : dto.getUser_1( ).toString( ) ).setParent( listitem );
		new Listcell( ( dto.getUser_2( ) == null ) ? "" : dto.getUser_2( ).toString( ) ).setParent( listitem );

		String sProblem = "Não Catalogado";
		if ( SysUtils.isZero( dto.getStatus( ) ) == false ) {
			int val = dto.getStatus( );
			switch ( val ) {
			case 1:
				sProblem = "Sem Dupla Digitação";
				break;
			case 2:
				sProblem = "Dupla Dititação Não Confere";
				break;
			}
		}
		new Listcell( sProblem ).setParent( listitem );
	}
}
