package br.com.mcampos.web.inep.controller.dialog;

import java.util.List;

import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import br.com.mcampos.ejb.inep.InepOralTeamDTO;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDialogWindow;
import br.com.mcampos.web.renderer.inep.InepOralTeamDTOListRenderer;

public class DlgOralTeamChoice extends BaseDialogWindow
{
	private static final long serialVersionUID = -2088897400989043774L;

	private Listbox first;
	private Listbox second;

	@Override
	protected boolean validate( )
	{
		if ( getFirst( ).getSelectedItem( ) == null ) {
			Messagebox.show( "Por favor, selecione um corretor na primeira coluna" );
			return false;
		}
		if ( getSecond( ).getSelectedItem( ) == null ) {
			Messagebox.show( "Por favor, selecione um corretor na segunda coluna" );
			return false;
		}
		if ( getFirst( ).getSelectedItem( ).getValue( ).equals( getSecond( ).getSelectedItem( ).getValue( ) ) ) {
			Messagebox.show( "Nao 'e permitido escolher o mesmo corretor para as provas" );
			return false;
		}
		return true;
	}

	public Listbox getFirst( )
	{
		if ( first == null ) {
			first = (Listbox) getFellow( "first" );
			if ( first != null )
				first.setItemRenderer( new InepOralTeamDTOListRenderer( ) );
		}
		return first;
	}

	public Listbox getSecond( )
	{
		if ( second == null ) {
			second = (Listbox) getFellow( "second" );
			if ( second != null )
				second.setItemRenderer( new InepOralTeamDTOListRenderer( ) );
		}
		return second;
	}

	public void onSelect( )
	{
	}

	public void loadList( List<InepOralTeamDTO> team )
	{
		if ( SysUtils.isEmpty( team ) )
			return;
		getFirst( ).setModel( new ListModelList<InepOralTeamDTO>( team ) );
		getSecond( ).setModel( new ListModelList<InepOralTeamDTO>( team ) );
	}

}
