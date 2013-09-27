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
		return this.comboEvent;
	}

	protected void setComboEvent( Combobox comboEvent )
	{
		this.comboEvent = comboEvent;
	}

	protected InepEvent getCurrentEvent( )
	{
		Comboitem item = this.comboEvent.getSelectedItem( );
		if ( item != null && item.getValue( ) != null ) {
			return (InepEvent) item.getValue( );
		}
		else {
			return null;
		}
	}

	protected InepRevisor getRevisor( )
	{
		if ( this.revisor == null ) {
			this.revisor = this.getSession( ).getRevisor( (InepEvent) this.getComboEvent( ).getSelectedItem( ).getValue( ),
					this.getPrincipal( ) );
		}
		return this.revisor;
	}

	protected BaseDialogWindow createDialog( String uri )
	{
		Component c = this.getMainWindow( ).getFellowIfAny( "dlgMainWnd" );
		if ( c != null ) {
			c.detach( );
			c = null;
		}
		BaseDialogWindow dlg = (BaseDialogWindow) this.createComponents( uri, this.getMainWindow( ), null );
		return dlg;
	}

	private void loadEvents( )
	{
		List<InepEvent> events = this.getSession( ).getEvents( this.getPrincipal( ) );

		if ( SysUtils.isEmpty( this.getComboEvent( ).getItems( ) ) == false ) {
			this.getComboEvent( ).getItems( ).clear( );
		}
		for ( InepEvent e : events ) {
			Comboitem item = this.getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( this.getComboEvent( ).getItemCount( ) > 0 ) {
			this.getComboEvent( ).setSelectedIndex( 0 );
			this.onSelectPackage( null );
		}
		this.getComboEvent( ).setDisabled( events.size( ) <= 1 );
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
		this.loadEvents( );
	}

	protected void resetRevisor( )
	{
		this.revisor = null;
	}

}
