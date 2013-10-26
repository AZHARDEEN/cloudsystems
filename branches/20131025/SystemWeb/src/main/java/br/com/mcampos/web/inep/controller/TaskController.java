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

import br.com.mcampos.ejb.inep.task.InepTaskSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.dbwidgets.DBWidget;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.renderer.inep.InepTaskListRenderer;

public class TaskController extends BaseDBListController<InepTaskSession, InepTask>
{
	private static final long serialVersionUID = 8231025035083979773L;

	@Wire( "#id, #description" )
	private List<DBWidget> inputs;

	@Wire( "#infoId, #infoDescription" )
	private List<Label> infoLabels;

	@Wire
	Combobox comboEvent;

	@Override
	protected Class<InepTaskSession> getSessionClass( )
	{
		return InepTaskSession.class;
	}

	@Override
	protected void showFields( InepTask entity )
	{
		for ( int nIndex = 0; nIndex < this.infoLabels.size( ); nIndex++ ) {
			this.infoLabels.get( nIndex ).setValue( entity != null ? entity.getField( nIndex ) : "" );
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
	protected void updateTargetEntity( InepTask entity )
	{
		for ( DBWidget input : this.inputs ) {
			if ( input.getId( ).equals( "id" ) ) {
				entity.getId( ).setId( Integer.parseInt( input.getText( ) ) );
				entity.getId( ).setCompanyId( this.getPrincipal( ).getCompanyID( ) );
			}
			else {
				entity.setDescription( input.getText( ) );
			}
		}
		InepEvent event = (InepEvent) this.getComboEvent( ).getSelectedItem( ).getValue( );
		entity.getId( ).set( event.getId( ) );
	}

	@Override
	protected boolean validateEntity( InepTask entity, int operation )
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
			this.onSelectEvent( null );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectEvent( Event evt )
	{
		Comboitem item = this.getComboEvent( ).getSelectedItem( );
		List<InepTask> list = this.getSession( ).getAll( (InepEvent) item.getValue( ) );
		this.getListbox( ).setModel( new ListModelList<InepTask>( list ) );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Override
	protected ListitemRenderer<InepTask> getListRenderer( )
	{
		return new InepTaskListRenderer( );
	}

}
