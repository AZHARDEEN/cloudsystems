package br.com.mcampos.web.inep.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.InepOralTeamDTO;
import br.com.mcampos.ejb.inep.InepOralFacade;
import br.com.mcampos.entity.inep.InepOralTest;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.web.core.event.IDialogEvent;
import br.com.mcampos.web.inep.controller.dialog.DlgOralTeamChoice;
import br.com.mcampos.web.renderer.inep.InepOralTestListRenderer;

public class OralVarianceCoordinatorController extends BaseOralController implements IDialogEvent
{
	private static final long serialVersionUID = -2775088765815980017L;

	@Wire( "listbox#listTable" )
	private Listbox listbox;

	@Override
	protected Class<InepOralFacade> getSessionClass( )
	{
		return InepOralFacade.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getListbox( ).setItemRenderer( new InepOralTestListRenderer( ) );
	}

	@Override
	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		List<InepOralTest> list = Collections.emptyList( );
		InepEvent item = getCurrentEvent( );
		if ( item != null ) {
			resetRevisor( );
			if ( getRevisor( ) == null || getRevisor( ).isCoordenador( ) ) {
				list = getSession( ).getVarianceOralOnly( getPrincipal( ), item );
			}
		}
		setModel( list );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private Listbox getListbox( )
	{
		return listbox;
	}

	private void setModel( List<InepOralTest> list )
	{
		@SuppressWarnings( "rawtypes" )
		ListModelList model = new ListModelList<InepOralTest>( list );

		model.setMultiple( true );
		getListbox( ).setModel( model );
	}

	@Listen( "onClick = #assign" )
	public void onAssign( Event evt )
	{
		if ( evt != null )
			evt.stopPropagation( );
		@SuppressWarnings( "unchecked" )
		ListModelList<InepOralTest> model = (ListModelList<InepOralTest>) ( (Object) getListbox( ).getModel( ) );
		Set<InepOralTest> items = model.getSelection( );
		if ( items == null || items.size( ) == 0 ) {
			Messagebox.show( "Por favor, selecione uma ou mais inscrições em discrepância para distribuir aos corretores",
					"Distribuir Prova Oral em Discrepância",
					Messagebox.OK,
					Messagebox.INFORMATION );
			return;
		}
		Component c = createDialog( "/private/inep/oral/dlg_oral_team_choice.zul" );
		if ( c != null && c instanceof DlgOralTeamChoice ) {
			DlgOralTeamChoice dlg = (DlgOralTeamChoice) c;
			dlg.setCallEvent( this );
			dlg.loadList( getSession( ).getOralTeamToChoice( getCurrentEvent( ), getPrincipal( ) ) );
			dlg.doModal( );
		}
	}

	@Override
	public void onOK( Window wnd )
	{
		if ( !( wnd instanceof DlgOralTeamChoice ) )
			return;
		InepOralTeamDTO first, second;
		DlgOralTeamChoice dlg = (DlgOralTeamChoice) wnd;
		first = (InepOralTeamDTO) dlg.getFirst( ).getSelectedItem( ).getValue( );
		second = (InepOralTeamDTO) dlg.getSecond( ).getSelectedItem( ).getValue( );

		@SuppressWarnings( "unchecked" )
		ListModelList<InepOralTest> model = (ListModelList<InepOralTest>) ( (Object) getListbox( ).getModel( ) );
		Set<InepOralTest> items = model.getSelection( );

		getSession( ).distribute( getCurrentEvent( ), getPrincipal( ), first.getRevisor( ), second.getRevisor( ), items );
		model.removeAll( items );
	}
}
