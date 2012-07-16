package br.com.mcampos.web.fdigital.controller;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.fdigital.pen.AnotoPen;
import br.com.mcampos.ejb.fdigital.pen.AnotoPenSession;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public class AnotoPenController extends BaseDBListController<AnotoPenSession, AnotoPen>
{
	private static final long serialVersionUID = 3669046391987942113L;

	@Wire
	private Label infoId;

	@Wire
	private Label infoDescription;

	@Wire
	private Label infoPin;

	@Wire
	private Label infoSerial;

	@Wire
	private Textbox id;

	@Wire
	private Textbox description;

	@Wire
	private Intbox pin;

	@Wire
	private Textbox serial;

	@Override
	protected void showFields( AnotoPen targetEntity )
	{
		if ( targetEntity != null ) {
			infoId.setValue( targetEntity.getId( ) );
			infoDescription.setValue( targetEntity.getDescription( ) );
			if ( targetEntity.getPin( ) != null )
				infoPin.setValue( targetEntity.getPin( ).toString( ) );
			else
				infoPin.setValue( "" );
			infoSerial.setValue( targetEntity.getSerial( ) );

			id.setText( targetEntity.getId( ) );
			description.setText( targetEntity.getDescription( ) );
			pin.setValue( targetEntity.getPin( ) );
			serial.setText( targetEntity.getSerial( ) );
		}
		else {
			infoId.setValue( "" );
			infoDescription.setValue( "" );
			infoPin.setValue( "" );
			infoSerial.setValue( "" );

			id.setRawValue( "" );
			description.setRawValue( "" );
			pin.setRawValue( 0 );
			serial.setRawValue( "" );
		}
	}

	@Override
	protected void updateTargetEntity( AnotoPen entity )
	{
		entity.setId( id.getText( ) );
		entity.setDescription( description.getText( ) );
		entity.setPin( pin.getValue( ) );
		entity.setSerial( serial.getValue( ) );
	}

	@Override
	protected boolean validateEntity( AnotoPen entity, int operation )
	{
		return true;
	}

	@Override
	protected Class<AnotoPenSession> getSessionClass( )
	{
		return AnotoPenSession.class;
	}

}
