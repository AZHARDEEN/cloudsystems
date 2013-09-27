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
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralDistribution;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.renderer.inep.InepOralDistributionCoordinatoListRenderer;
import br.com.mcampos.web.renderer.inep.InepOralDistributionListRenderer;

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
		this.setRadio( );
		if ( this.getRevisor( ) != null ) {
			if ( this.getRevisor( ).isCoordenador( ) ) {
				this.getListbox( ).setItemRenderer( new InepOralDistributionCoordinatoListRenderer( ) );
			}
			else {
				this.getListbox( ).setItemRenderer( new InepOralDistributionListRenderer( ) );
			}
		}
	}

	private void setRadio( )
	{
		if ( this.rdVoid != null ) {
			this.rdVoid.setVisible( false );
		}
		if ( this.rdBlank != null ) {
			this.rdBlank.setVisible( false );
		}
	}

	@Override
	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		List<InepOralDistribution> list = Collections.emptyList( );
		InepEvent item = this.getCurrentEvent( );
		if ( item != null ) {
			this.resetRevisor( );
			list = this.getSession( ).getOralTests( this.getRevisor( ) );
		}
		this.setModel( list );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private void setModel( List<InepOralDistribution> list )
	{
		@SuppressWarnings( "rawtypes" )
		ListModelList model = new ListModelList<InepOralDistribution>( list );

		model.setMultiple( true );
		this.getListbox( ).setModel( model );
	}

	@SuppressWarnings( "unchecked" )
	private ListModelList<InepOralDistribution> getModel( )
	{
		return (ListModelList<InepOralDistribution>) ( (Object) this.getListbox( ).getModel( ) );
	}

	private Listbox getListbox( )
	{
		return this.listbox;
	}

	@Listen( "onClick=#cmdInepSave" )
	public void onSave( MouseEvent evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		Set<InepOralDistribution> items = this.getModel( ).getSelection( );
		if ( SysUtils.isEmpty( items ) || items.size( ) > 1 ) {
			Messagebox.show( "Por favor, escolha um item da lista antes de salvar a nota", "Nota da Prova Oral", Messagebox.OK, Messagebox.EXCLAMATION );
			return;
		}
		int nota = this.getNotas( ).getSelectedIndex( );
		if ( nota < 0 ) {
			Messagebox.show( "Por favor, escolha a sua nota da prova entre as opções de 0 a 5", "Nota da Prova Oral", Messagebox.OK,
					Messagebox.EXCLAMATION );
			return;
		}
		for ( InepOralDistribution item : items ) {
			this.getSession( ).updateGrade( item, nota );
		}
		this.getModel( ).removeAll( items );
		this.getNotas( ).setSelectedItem( null );
	}

	public Radiogroup getNotas( )
	{
		return this.notas;
	}
}
