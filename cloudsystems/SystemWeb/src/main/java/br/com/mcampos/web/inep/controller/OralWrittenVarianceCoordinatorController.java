package br.com.mcampos.web.inep.controller;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;

import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.web.renderer.inep.InepOralWrittenListRenderer;

public class OralWrittenVarianceCoordinatorController extends OralVarianceCoordinatorController
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4811494622747415682L;

	@Override
	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		List<InepOralTest> list = Collections.emptyList( );
		InepEvent item = this.getCurrentEvent( );
		if ( item != null ) {
			this.resetRevisor( );
			if ( this.getRevisor( ) == null || this.getRevisor( ).isCoordenador( ) ) {
				list = this.getSession( ).getVarianceOralWritten( this.getPrincipal( ), item );
			}
		}
		this.setModel( list );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Override
	protected void setRenderer( )
	{
		this.getListbox( ).setItemRenderer( new InepOralWrittenListRenderer( ) );
	}

}
