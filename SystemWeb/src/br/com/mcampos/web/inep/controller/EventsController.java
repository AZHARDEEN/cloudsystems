package br.com.mcampos.web.inep.controller;

import java.util.Collection;
import java.util.List;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.ejb.inep.packs.InepPackage;
import br.com.mcampos.ejb.inep.packs.InepPackageSession;
import br.com.mcampos.web.core.dbwidgets.DBWidget;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.inep.controller.renderer.EventListRenderer;

public class EventsController extends BaseDBListController<InepPackageSession, InepPackage>
{
	private static final long serialVersionUID = 3519756939620280341L;

	@Wire( "#id, #description" )
	private List<DBWidget> inputs;

	@Wire( "#infoId, #infoDescription" )
	private List<Label> infoLabels;

	@Override
	protected Class<InepPackageSession> getSessionClass( )
	{
		return InepPackageSession.class;
	}

	@Override
	protected void showFields( Collection<InepPackage> entities )
	{
		List<InepPackage> fields = (List<InepPackage>) entities;

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
	protected void updateTargetEntity( InepPackage entity )
	{
		for ( DBWidget input : this.inputs ) {
			if ( input.getId( ).equals( "id" ) ) {
				entity.getId( ).setId( Integer.parseInt( input.getText( ) ) );
				entity.getId( ).setCompanyId( getAuthentication( ) );
			}
			else {
				entity.setDescription( input.getText( ) );
			}
		}
	}

	@Override
	protected boolean validateEntity( InepPackage entity, int operation )
	{
		return true;
	}

	@Override
	protected ListitemRenderer<InepPackage> getListRenderer( )
	{
		return new EventListRenderer( );
	}

	@Override
	protected void loadPage( int activePage )
	{
		List<InepPackage> list = getSession( ).getAll( getAuthentication( ) );
		getListbox( ).setModel( new ListModelList<InepPackage>( list ) );
	}

}
