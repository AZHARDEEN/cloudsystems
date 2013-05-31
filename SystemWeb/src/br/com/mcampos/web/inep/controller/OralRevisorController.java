package br.com.mcampos.web.inep.controller;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.InepOralFacade;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.inep.controller.renderer.InepOralDistributionListRenderer;

public class OralRevisorController extends BaseOralController
{
	private static final long serialVersionUID = -4168237404522277162L;

	@Wire
	Radio rdVoid;
	@Wire
	Radio rdBlank;
	@Wire( "listbox#listTable" )
	private Listbox listbox;

	@Wire( "radiogroup#sv6" )
	private Radiogroup notas;

	@Override
	protected Class<InepOralFacade> getSessionClass( )
	{
		return InepOralFacade.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setRadio( );
		getListbox( ).setItemRenderer( new InepOralDistributionListRenderer( ) );
	}

	private void setRadio( )
	{
		if ( rdVoid != null )
			rdVoid.setVisible( false );
		if ( rdBlank != null )
			rdBlank.setVisible( false );
	}

	@Override
	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		List<InepOralDistribution> list = Collections.emptyList( );
		InepPackage item = getCurrentEvent( );
		if ( item != null ) {
			resetRevisor( );
			list = getSession( ).getOralTests( getRevisor( ) );
		}
		setModel( list );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private void setModel( List<InepOralDistribution> list )
	{
		@SuppressWarnings( "rawtypes" )
		ListModelList model = new ListModelList<InepOralDistribution>( list );

		model.setMultiple( true );
		getListbox( ).setModel( model );
	}

	@SuppressWarnings( "unchecked" )
	private ListModelList<InepOralDistribution> getModel( )
	{
		return (ListModelList<InepOralDistribution>) ( (Object) getListbox( ).getModel( ) );
	}

	private Listbox getListbox( )
	{
		return listbox;
	}

	@Listen( "onClick=#cmdInepSave" )
	public void onSave( MouseEvent evt )
	{
		if ( evt != null )
			evt.stopPropagation( );
		Set<InepOralDistribution> items = getModel( ).getSelection( );
		if ( SysUtils.isEmpty( items ) || items.size( ) > 1 ) {
			Messagebox.show( "Por favor, escolha um item da lista antes de salvar a nota", "Nota da Prova Oral", Messagebox.OK, Messagebox.EXCLAMATION );
			return;
		}
		int nota = getNotas( ).getSelectedIndex( );
		if ( nota < 0 ) {
			Messagebox.show( "Por favor, escolha a sua nota da prova entre as opções de 0 a 5", "Nota da Prova Oral", Messagebox.OK,
					Messagebox.EXCLAMATION );
			return;
		}
		for ( InepOralDistribution item : items ) {
			getSession( ).updateGrade( item, nota );
		}
		getModel( ).removeAll( items );
		getNotas( ).setSelectedItem( null );
	}

	public Radiogroup getNotas( )
	{
		return notas;
	}
}
