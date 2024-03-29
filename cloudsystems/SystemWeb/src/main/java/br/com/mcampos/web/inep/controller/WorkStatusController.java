package br.com.mcampos.web.inep.controller;

import java.math.BigInteger;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Flashchart;
import org.zkoss.zul.SimplePieModel;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;

public class WorkStatusController extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = -4722826337038191799L;

	@Wire
	private Combobox comboEvent;

	@Wire
	private Flashchart chartTask1;

	@Wire
	private Flashchart chartTask2;

	@Wire
	private Flashchart chartTask3;

	@Wire
	private Flashchart chartTask4;

	@Wire
	private Flashchart chartEvent;

	@Wire
	private Flashchart chartSubscriptions;

	@Wire
	@Override
	protected Class<TeamSession> getSessionClass( )
	{
		return TeamSession.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.loadCombobox( );
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
			this.onSelectEvent( null );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectEvent( Event evt )
	{
		Comboitem item = this.getComboEvent( ).getSelectedItem( );
		if ( item != null && item.getValue( ) != null ) {
			this.loadData( (InepEvent) item.getValue( ) );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private void loadData( InepEvent event )
	{
		SimplePieModel model1 = new SimplePieModel( );
		SimplePieModel model2 = new SimplePieModel( );
		SimplePieModel model3 = new SimplePieModel( );
		SimplePieModel model4 = new SimplePieModel( );
		SimplePieModel model = new SimplePieModel( );

		List<Object[ ]> list = this.getSession( ).getWorkStatus( event );
		String desc;

		for ( Object[ ] item : list ) {
			Integer task = (Integer) item[ 0 ];
			Integer status = (Integer) item[ 1 ];
			Long value = (Long) item[ 2 ];

			desc = this.getStatusDescription( status );
			switch ( task ) {
			case 1:
				model1.setValue( desc, value );
				break;
			case 2:
				model2.setValue( desc, value );
				break;
			case 3:
				model3.setValue( desc, value );
				break;
			case 4:
				model4.setValue( desc, value );
				break;
			}
			Number n = model.getValue( desc );
			if ( n == null ) {
				model.setValue( desc, value );
			}
			else {
				model.setValue( desc, n.longValue( ) + value );
			}
		}
		this.chartTask1.setModel( model1 );
		this.chartTask2.setModel( model2 );
		this.chartTask3.setModel( model3 );
		this.chartTask4.setModel( model4 );
		this.chartEvent.setModel( model );

		list = this.getSession( ).getSubscriptionStatus( event );
		if ( SysUtils.isEmpty( list ) == false ) {
			Object[ ] item = list.get( 0 );
			model = new SimplePieModel( );
			model.setValue( "Total", (BigInteger) item[ 0 ] );
			model.setValue( "Finalizadas", (BigInteger) item[ 1 ] );
			this.chartSubscriptions.setModel( model );
			this.chartSubscriptions.setVisible( true );
		}
		else {
			this.chartSubscriptions.setVisible( false );
		}

	}

	public Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	private String getStatusDescription( Integer status )
	{
		switch ( status )
		{
		case 1:
			return "Distribuído";
		case 2:
			return "Corrigido";
		case 3:
			return "Discrepância";
		case 4:
			return "Validado";
		}
		return "Indefinido";
	}

}
