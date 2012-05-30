package br.com.mcampos.web.inep.controller;

import java.util.Collection;
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

import br.com.mcampos.ejb.inep.packs.InepPackage;
import br.com.mcampos.ejb.inep.subscription.InepSubscription;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.dbwidgets.DBWidget;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.inep.controller.renderer.InepSubscriptionRenderer;

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
	protected void showFields( Collection<InepSubscription> entities )
	{
		List<InepSubscription> fields = (List<InepSubscription>) entities;
		for ( int nIndex = 0; nIndex < this.infoLabels.size( ); nIndex++ ) {
			this.infoLabels.get( nIndex ).setValue( fields != null ? fields.get( 0 ).getField( nIndex ) : "" );
		}
		for ( int nIndex = 0; nIndex < this.inputs.size( ); nIndex++ ) {
			DBWidget input = this.inputs.get( nIndex );
			input.setText( fields != null ? fields.get( 0 ).getField( nIndex ) : "" );
			if ( getStatus( ) == statusUpdate ) {
				if ( input.isPrimaryKey( ) ) {
					input.setDisabled( fields != null );
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
		InepPackage event = (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( );
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
		loadCombobox( );
	}

	public Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	private void loadCombobox( )
	{
		List<InepPackage> events = getSession( ).getEvents( getAuthentication( ) );

		if ( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for ( InepPackage e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectEvent( Event evt )
	{
		Comboitem item = getComboEvent( ).getSelectedItem( );
		getListbox( ).setModel( new ListModelList<InepSubscription>( getSession( ).getAll( (InepPackage) item.getValue( ) ) ) );
		evt.stopPropagation( );
	}

	@Override
	protected ListitemRenderer<InepSubscription> getListRenderer( )
	{
		return new InepSubscriptionRenderer( );
	}
}
