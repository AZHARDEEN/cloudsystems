package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.inep.utils.DistributionInfoListRenderer;
import br.com.mcampos.web.inep.utils.SubscriptionItemRenderer;

public class SubscriptionStatus extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = 4073500689607424974L;
	private static final Logger logger = LoggerFactory.getLogger( SubscriptionStatus.class );

	private InepRevisor revisor;

	@Wire
	private Combobox comboEvent;

	@Wire
	private Bandbox subscription;

	@Wire( "#list" )
	private Listbox listBox;

	@Wire
	private Listbox listDetail;

	@Wire( "#framePdf" )
	private Iframe framePdf;

	@Wire
	private Combobox cmbTask;

	@Override
	protected Class<TeamSession> getSessionClass( )
	{
		return TeamSession.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.listBox.setItemRenderer( new SubscriptionItemRenderer( ) );
		this.listDetail.setItemRenderer( new DistributionInfoListRenderer( ) );
		loadCombobox( );

		if ( getRevisor( ) == null ) {
			this.cmbTask.setVisible( true );
		}
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
			loadComboboxTask( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ) );
		}
	}

	private void loadComboboxTask( InepPackage event )
	{
		List<InepTask> events = getSession( ).getTasks( event );

		this.cmbTask.getItems( ).clear( );
		for ( InepTask e : events ) {
			if ( getRevisor( ) == null || getRevisor( ).getTask( ).equals( e ) ) {
				Comboitem item = this.cmbTask.appendItem( e.getDescription( ) );
				item.setValue( e );
			}
		}
		if ( this.cmbTask.getItemCount( ) > 0 ) {
			this.cmbTask.setSelectedIndex( 0 );
		}
	}

	private Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	private void search( String value )
	{
		if ( SysUtils.isEmpty( value ) == false && value.length( ) >= 3 ) {
			if ( showSubscriptions( value ) == false ) {
				this.subscription.close( );
			}
		}
		else {
			this.subscription.close( );
		}
	}

	@Listen( "onOpen=bandbox" )
	public void onOpenBandbox( OpenEvent evt )
	{
		if ( evt != null ) {
			logger.info( "onOpen[bandbox]: " + evt.getValue( ) );
			search( evt.getValue( ).toString( ) );
			evt.stopPropagation( );
		}
	}

	@Listen( "onOK=bandbox" )
	public void onOkBandbox( Event evt )
	{
		if ( evt != null ) {
			this.subscription.open( );
			logger.info( "onOK[bandbox]: " + this.subscription.getValue( ) );
			search( this.subscription.getValue( ) );
			evt.stopPropagation( );
		}
	}

	private boolean showSubscriptions( String part )
	{
		if ( getComboEvent( ).getSelectedItem( ) == null ) {
			return false;
		}
		InepPackage event = getComboEvent( ).getSelectedItem( ).getValue( );
		if ( event == null ) {
			return false;
		}
		logger.info( "Searching for: " + part );
		List<InepSubscription> list = getSession( ).getSubscriptions( event, part );
		if ( SysUtils.isEmpty( list ) ) {
			return false;
		}
		this.listBox.setModel( new ListModelList<InepSubscription>( list ) );
		return true;
	}

	@Listen( "onSelect = #list" )
	public void onSelect( Event evt )
	{
		Listitem item = this.listBox.getSelectedItem( );
		if ( item == null || item.getValue( ) == null ) {
			return;
		}
		InepSubscription s = item.getValue( );
		this.subscription.setValue( s.getId( ).getId( ) );
		this.subscription.close( );
		showInfo( s );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private void showInfo( InepSubscription s )
	{
		if ( s == null ) {
			return;
		}
		List<InepDistribution> list = getSession( ).getDistribution( s );
		this.listDetail.setModel( new ListModelList<InepDistribution>( list ) );

		showFrame( s );
	}

	private void showFrame( InepSubscription s )
	{
		byte[ ] obj = getSession( ).getMedia( s, (InepTask) this.cmbTask.getSelectedItem( ).getValue( ) );
		AMedia media = new AMedia( null, null, null, obj );
		this.framePdf.setContent( media );
	}

	public InepRevisor getRevisor( )
	{
		if ( this.revisor == null ) {
			if ( getComboEvent( ).getSelectedItem( ) != null ) {
				this.revisor = getSession( ).getRevisor( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ),
						getCurrentCollaborator( ) );
			}
		}
		return this.revisor;
	}

}
