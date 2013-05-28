package br.com.mcampos.web.inep.controller;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.InepOralFacade;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.inep.controller.renderer.InepOralTestListRenderer;

public class OralVarianceCoordinatorController extends BaseDBLoggedController<InepOralFacade>
{
	private static final long serialVersionUID = -2775088765815980017L;

	@Wire
	private Combobox comboEvent;

	@Wire( "listbox#listTable" )
	private Listbox listbox;

	private InepRevisor revisor;

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
		loadCombobox( );
	}

	private void loadCombobox( )
	{
		List<InepPackage> events = getSession( ).getEvents( getCurrentCollaborator( ) );

		if ( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) == false ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for ( InepPackage e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
			onSelectPackage( null );
		}
		getComboEvent( ).setDisabled( events.size( ) <= 1 );
	}

	private Combobox getComboEvent( )
	{
		return comboEvent;
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		List<InepOralTest> list = Collections.emptyList( );
		Comboitem item = comboEvent.getSelectedItem( );
		if ( item != null ) {
			if ( getRevisor( ) == null || getRevisor( ).isCoordenador( ) ) {
				list = getSession( ).getVarianceOralOnly( getCurrentCollaborator( ), (InepPackage) item.getValue( ) );
			}
		}
		setModel( list );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	public InepRevisor getRevisor( )
	{
		if ( revisor == null ) {
			revisor = getSession( ).getRevisor( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ),
					getCurrentCollaborator( ) );
		}
		return revisor;
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

}
