package br.com.mcampos.web.inep.controller;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.InepOralFacade;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepPackage;
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

	private Listbox getListbox( )
	{
		return listbox;
	}
}
