package br.com.mcampos.web.controller.tables;

import java.util.List;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;

import br.com.mcampos.ejb.params.SystemParameterSession;
import br.com.mcampos.entity.system.SystemParameters;
import br.com.mcampos.web.core.dbwidgets.DBWidget;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public class SystemParametersController extends BaseDBListController<SystemParameterSession, SystemParameters>
{
	private static final long serialVersionUID = -1110326569922255687L;

	@Wire( "#id, #description, #value" )
	private List<DBWidget> inputs;

	@Wire( "#infoId, #infoDescription, #infoValue" )
	private List<Label> infoLabels;

	@Override
	protected Class<SystemParameterSession> getSessionClass( )
	{
		return SystemParameterSession.class;
	}

	@Override
	protected void updateTargetEntity( SystemParameters target )
	{
		// super.updateTargetEntity( target );
		// target.setValue( value.getText() );
		for ( DBWidget input : this.inputs ) {
			if ( input.getId( ).equals( "id" ) ) {
				target.setId( input.getText( ) );
			}
			else if ( input.getId( ).equals( "description" ) ) {
				target.setDescription( input.getText( ) );
			}
			else {
				target.setValue( input.getText( ) );
			}
		}
	}

	@Override
	protected void showFields( SystemParameters entity )
	{
		for ( int nIndex = 0; nIndex < this.infoLabels.size( ); nIndex++ ) {
			this.infoLabels.get( nIndex ).setValue( entity != null ? entity.getField( nIndex ) : "" );
		}
		for ( int nIndex = 0; nIndex < this.inputs.size( ); nIndex++ ) {
			DBWidget input = this.inputs.get( nIndex );
			input.setText( entity != null ? entity.getField( nIndex ) : "" );
			if ( getStatus( ).equals( statusUpdate ) ) {
				if ( input.isPrimaryKey( ) ) {
					input.setDisabled( entity != null );
				}
			}
		}
	}

	@Override
	protected boolean validateEntity( SystemParameters entity, int operation )
	{
		return true;
	}
}
