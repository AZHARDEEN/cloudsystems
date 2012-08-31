package br.com.mcampos.web.core;

import java.util.List;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import br.com.mcampos.ejb.core.SimpleEntity;
import br.com.mcampos.web.core.dbwidgets.DBWidget;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public abstract class SimpleTableController<SESSION, ENTITY> extends BaseDBListController<SESSION, ENTITY>
{
	private static final long serialVersionUID = -5698179983078743481L;

	@Wire( "#id, #description" )
	private List<DBWidget> inputs;

	@Wire( "#infoId, #infoDescription" )
	private List<Label> infoLabels;

	public SimpleTableController( )
	{
		super( );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	protected void showFields( ENTITY entity )
	{
		SimpleEntity<ENTITY> simpleEntity = null;

		if ( entity instanceof SimpleEntity ) {
			simpleEntity = (SimpleEntity<ENTITY>) entity;
		}
		for ( int nIndex = 0; nIndex < this.infoLabels.size( ); nIndex++ ) {
			this.infoLabels.get( nIndex ).setValue( simpleEntity != null ? simpleEntity.getField( nIndex ) : "" );
		}
		for ( int nIndex = 0; nIndex < this.inputs.size( ); nIndex++ ) {
			DBWidget input = this.inputs.get( nIndex );
			input.setText( simpleEntity != null ? simpleEntity.getField( nIndex ) : "" );
			if ( getStatus( ).equals( statusUpdate ) ) {
				if ( input.isPrimaryKey( ) ) {
					input.setDisabled( entity != null );
				}
			}
		}
	}

	@Override
	protected void updateTargetEntity( ENTITY target )
	{
		@SuppressWarnings( "unchecked" )
		SimpleEntity<ENTITY> entity = (SimpleEntity<ENTITY>) target;
		for ( DBWidget input : this.inputs ) {
			if ( input.getId( ).equals( "id" ) ) {
				entity.setId( Integer.parseInt( input.getText( ) ) );
			}
			else {
				entity.setCode( input.getText( ) );
			}
		}
	}

	@Override
	protected boolean validateEntity( ENTITY target, int operation )
	{
		@SuppressWarnings( "unchecked" )
		SimpleEntity<ENTITY> entity = (SimpleEntity<ENTITY>) target;

		if ( entity.getId( ) == null || entity.getId( ) == 0 ) {
			showError( "O campo Código não pode estar vazio" );
			return false;
		}
		if ( entity.getCode( ) == null || entity.getCode( ).isEmpty( ) ) {
			showError( "O campo descrição não pode estar vazio" );
			return false;
		}
		return true;
	}

	private void showError( String message )
	{
		Messagebox.show( message, "Erro", Messagebox.OK, Messagebox.ERROR );
	}

}
