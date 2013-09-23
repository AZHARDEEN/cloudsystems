package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.InepOralFacade;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.core.BaseDialogWindow;

public abstract class BaseOralController extends BaseDBLoggedController<InepOralFacade>
{
	private static final long serialVersionUID = -706579969846036677L;
	private InepRevisor revisor;

	@Wire
	private Combobox comboEvent;

	public abstract void onSelectPackage( Event evt );

	protected Combobox getComboEvent( )
	{
		return comboEvent;
	}

	protected void setComboEvent( Combobox comboEvent )
	{
		this.comboEvent = comboEvent;
	}

	protected InepEvent getCurrentEvent( )
	{
		Comboitem item = comboEvent.getSelectedItem( );
		if( item != null && item.getValue( ) != null ) {
			return (InepEvent) item.getValue( );
		}
		else
			return null;
	}

	protected InepRevisor getRevisor( )
	{
		if( revisor == null ) {
			revisor = getSession( ).getRevisor( (InepEvent) getComboEvent( ).getSelectedItem( ).getValue( ),
					getPrincipal( ) );
		}
		return revisor;
	}

	protected BaseDialogWindow createDialog( String uri )
	{
		Component c = getMainWindow( ).getFellowIfAny( "dlgMainWnd" );
		if( c != null ) {
			c.detach( );
			c = null;
		}
		BaseDialogWindow dlg = (BaseDialogWindow) createComponents( uri, getMainWindow( ), null );
		return dlg;
	}

	private void loadEvents( )
	{
		List<InepEvent> events = getSession( ).getEvents( getPrincipal( ) );

		if( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) == false ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for( InepEvent e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
			onSelectPackage( null );
		}
		getComboEvent( ).setDisabled( events.size( ) <= 1 );
	}

	@Override
	protected Class<InepOralFacade> getSessionClass( )
	{
		return InepOralFacade.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		loadEvents( );
	}

	protected void resetRevisor( )
	{
		revisor = null;
	}

}