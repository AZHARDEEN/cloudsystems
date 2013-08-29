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

import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.task.InepTaskSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.dbwidgets.DBWidget;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.inep.controller.renderer.InepTaskListRenderer;

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
		for( int nIndex = 0; nIndex < infoLabels.size( ); nIndex++ ) {
			infoLabels.get( nIndex ).setValue( entity != null ? entity.getField( nIndex ) : "" );
		}
		for( int nIndex = 0; nIndex < inputs.size( ); nIndex++ ) {
			DBWidget input = inputs.get( nIndex );
			input.setText( entity != null ? entity.getField( nIndex ) : "" );
			if( getStatus( ).equals( statusUpdate ) ) {
				if( input.isPrimaryKey( ) ) {
					input.setDisabled( entity != null );
				}
			}
		}
	}

	@Override
	protected void updateTargetEntity( InepTask entity )
	{
		for( DBWidget input : inputs ) {
			if( input.getId( ).equals( "id" ) ) {
				entity.getId( ).setId( Integer.parseInt( input.getText( ) ) );
				entity.getId( ).setCompanyId( getPrincipal( ).getCompanyID( ) );
			}
			else {
				entity.setDescription( input.getText( ) );
			}
		}
		InepPackage event = (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( );
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
		loadCombobox( );
	}

	public Combobox getComboEvent( )
	{
		return comboEvent;
	}

	private void loadCombobox( )
	{
		List<InepPackage> events = getSession( ).getEvents( getPrincipal( ) );

		if( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for( InepPackage e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
			onSelectEvent( null );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectEvent( Event evt )
	{
		Comboitem item = getComboEvent( ).getSelectedItem( );
		List<InepTask> list = getSession( ).getAll( (InepPackage) item.getValue( ) );
		getListbox( ).setModel( new ListModelList<InepTask>( list ) );
		if( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Override
	protected ListitemRenderer<InepTask> getListRenderer( )
	{
		return new InepTaskListRenderer( );
	}

}
