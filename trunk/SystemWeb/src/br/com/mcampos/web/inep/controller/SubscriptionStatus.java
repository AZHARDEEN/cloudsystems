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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.entity.InepTest;
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
	Label task1;
	@Wire
	Label task2;
	@Wire
	Label task3;
	@Wire
	Label task4;
	@Wire
	Label station;
	@Wire
	Label interviewerGrade;
	@Wire
	Label observerGrade;
	@Wire
	Label stationGrade;
	@Wire
	Label agreementGrade;
	@Wire
	Label agreement2Grade;

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
		listBox.setItemRenderer( new SubscriptionItemRenderer( ) );
		listDetail.setItemRenderer( new DistributionInfoListRenderer( ) );
		loadCombobox( );

		if ( getRevisor( ) == null ) {
			cmbTask.setVisible( true );
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

		cmbTask.getItems( ).clear( );
		for ( InepTask e : events ) {
			if ( getRevisor( ) == null || getRevisor( ).getTask( ).equals( e ) ) {
				Comboitem item = cmbTask.appendItem( e.getDescription( ) );
				item.setValue( e );
			}
		}
		if ( cmbTask.getItemCount( ) > 0 ) {
			cmbTask.setSelectedIndex( 0 );
		}
	}

	private Combobox getComboEvent( )
	{
		return comboEvent;
	}

	private void search( String value )
	{
		if ( SysUtils.isEmpty( value ) == false && value.length( ) >= 3 ) {
			if ( showSubscriptions( value ) == false ) {
				subscription.close( );
			}
		}
		else {
			subscription.close( );
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
			subscription.open( );
			logger.info( "onOK[bandbox]: " + subscription.getValue( ) );
			search( subscription.getValue( ) );
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
		listBox.setModel( new ListModelList<InepSubscription>( list ) );
		return true;
	}

	@Listen( "onSelect = #list" )
	public void onSelect( Event evt )
	{
		Listitem item = listBox.getSelectedItem( );
		if ( item == null || item.getValue( ) == null ) {
			return;
		}
		InepSubscription s = item.getValue( );
		subscription.setValue( s.getId( ).getId( ) );
		subscription.close( );
		showInfo( s );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onSelect = #cmbTask" )
	public void onSelectTask( Event evt )
	{
		onSelect( evt );
	}

	private void showInfo( InepSubscription s )
	{
		if ( s == null ) {
			return;
		}
		List<InepDistribution> list = getSession( ).getDistribution( s );
		listDetail.setModel( new ListModelList<InepDistribution>( list ) );
		showFrame( s );
		showOralGrade( s );
		showTasks( s );

	}

	private void showOralGrade( InepSubscription s )
	{
		InepOralTest test = getSession( ).getOralTest( s );
		station.setValue( "" );
		interviewerGrade.setValue( "" );
		observerGrade.setValue( "" );
		stationGrade.setValue( "" );
		agreementGrade.setValue( "" );
		agreement2Grade.setValue( "" );
		if ( test != null ) {
			station.setValue( test.getStation( ) );
			interviewerGrade.setValue( test.getInterviewGrade( ).toString( ) );
			observerGrade.setValue( test.getObserverGrade( ).toString( ) );
			stationGrade.setValue( test.getFinalGrade( ).toString( ) );
			if ( test.getAgreementGrade( ) != null )
				agreementGrade.setValue( test.getAgreementGrade( ).toString( ) );
			if ( test.getAgreement2Grade( ) != null )
				agreement2Grade.setValue( test.getAgreement2Grade( ).toString( ) );
		}
	}

	private void showTasks( InepSubscription s )
	{
		List<InepTest> tests = getSession( ).getTests( s );
		if ( !SysUtils.isEmpty( tests ) ) {
			for ( InepTest test : tests ) {
				switch ( test.getId( ).getTaskId( ) ) {
				case 1:
					task1.setValue( test.getGrade( ).toString( ) );
					break;
				case 2:
					task2.setValue( test.getGrade( ).toString( ) );
					break;
				case 3:
					task3.setValue( test.getGrade( ).toString( ) );
					break;
				case 4:
					task4.setValue( test.getGrade( ).toString( ) );
					break;
				}
			}
		}
	}

	private void showFrame( InepSubscription s )
	{
		InepTask task = (InepTask) cmbTask.getSelectedItem( ).getValue( );
		byte[ ] obj = getSession( ).getMedia( s, task );
		AMedia media = new AMedia( null, null, null, obj );
		framePdf.setContent( media );
	}

	public InepRevisor getRevisor( )
	{
		if ( revisor == null ) {
			if ( getComboEvent( ).getSelectedItem( ) != null ) {
				revisor = getSession( ).getRevisor( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ),
						getCurrentCollaborator( ) );
			}
		}
		return revisor;
	}

}
