package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.dbwidgets.DBWidget;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.renderer.inep.InepSubscriptionRenderer;

public class SubscriptionController extends BaseDBListController<InepSubscriptionSession, InepSubscription>
{
	private static final long serialVersionUID = 8231025035083979773L;

	@Wire( "#description" )
	private List<DBWidget> inputs;

	@Wire( "#infoId, #infoDescription" )
	private List<Label> infoLabels;

	@Wire
	Combobox comboEvent;

	@Override
	protected Class<InepSubscriptionSession> getSessionClass( )
	{
		return InepSubscriptionSession.class;
	}

	@Override
	protected void showFields( InepSubscription entity )
	{
		for ( int nIndex = 0; nIndex < this.getInfoLabels( ).size( ); nIndex++ ) {
			this.getInfoLabels( ).get( nIndex ).setValue( entity != null ? entity.getField( nIndex ) : "" );
		}
		for ( int nIndex = 0; nIndex < this.inputs.size( ); nIndex++ ) {
			DBWidget input = this.inputs.get( nIndex );
			input.setText( entity != null ? entity.getField( nIndex ) : "" );
			if ( this.getStatus( ).equals( statusUpdate ) ) {
				if ( input.isPrimaryKey( ) ) {
					input.setDisabled( entity != null );
				}
			}
		}
	}

	@Override
	protected void updateTargetEntity( InepSubscription entity )
	{
		for ( DBWidget input : this.inputs ) {
			entity.getId( ).setId( input.getText( ) );
		}
		InepEvent event = (InepEvent) this.getComboEvent( ).getSelectedItem( ).getValue( );
		entity.getId( ).set( event );
	}

	@Override
	protected boolean validateEntity( InepSubscription entity, int operation )
	{
		return true;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.loadCombobox( );
	}

	public Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	private void loadCombobox( )
	{
		List<InepEvent> events = this.getSession( ).getEvents( this.getPrincipal( ) );

		if ( SysUtils.isEmpty( this.getComboEvent( ).getItems( ) ) ) {
			this.getComboEvent( ).getItems( ).clear( );
		}
		for ( InepEvent e : events ) {
			Comboitem item = this.getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( this.getComboEvent( ).getItemCount( ) > 0 ) {
			this.getComboEvent( ).setSelectedIndex( 0 );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectEvent( Event evt )
	{
		Comboitem item = this.getComboEvent( ).getSelectedItem( );
		this.getListbox( ).setModel( new ListModelList<InepSubscription>( this.getSession( ).getAll( (InepEvent) item.getValue( ) ) ) );
		evt.stopPropagation( );
	}

	@Override
	protected ListitemRenderer<InepSubscription> getListRenderer( )
	{
		return new InepSubscriptionRenderer( );
	}

	private List<Label> getInfoLabels( )
	{
		return this.infoLabels;
	}
}
