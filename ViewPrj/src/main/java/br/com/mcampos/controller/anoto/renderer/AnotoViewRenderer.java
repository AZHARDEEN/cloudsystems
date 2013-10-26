package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.dto.anoto.PgcPenPageDTO;

public class AnotoViewRenderer implements TreeitemRenderer
{
	public AnotoViewRenderer( )
	{
		super( );
	}

	@Override
	public void render( Treeitem item, Object data, int index ) throws Exception
	{
		Treerow treeRow = item.getTreerow( );
		Treecell cell;

		if ( treeRow == null ) {
			treeRow = new Treerow( );
			item.appendChild( treeRow );
		}
		if ( data instanceof AnotoPenPageDTO )
			cell = new Treecell( ( (AnotoPenPageDTO) data ).getPen( ).getId( ) );
		else if ( data instanceof PgcPenPageDTO )
			cell = new Treecell( ( (PgcPenPageDTO) data ).getPgc( ).toString( ) );
		else
			cell = new Treecell( data.toString( ) );
		treeRow.appendChild( cell );
		item.setValue( data );
	}

}
