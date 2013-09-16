package br.com.mcampos.web.controller.tables;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.system.operators.OperatorSession;
import br.com.mcampos.jpa.system.Operator;
import br.com.mcampos.web.core.SimpleTableController;

public class OperatorController extends SimpleTableController<OperatorSession, Operator>
{
	private static final long serialVersionUID = -6455694061270231285L;

	@Wire
	private Label infoSymbol;

	@Wire
	private Textbox symbol;

	@Override
	protected Class<OperatorSession> getSessionClass( )
	{
		return OperatorSession.class;
	}

	@Override
	protected void showFields( Operator entity )
	{
		if ( entity != null ) {
			this.infoSymbol.setValue( entity.getSymbol( ) );
			this.symbol.setText( entity.getSymbol( ) );
		}
		else {
			this.infoSymbol.setValue( "" );
			this.symbol.setRawValue( "" );

		}
		super.showFields( entity );
	}

	@Override
	protected void updateTargetEntity( Operator target )
	{
		if ( target != null ) {
			target.setSymbol( this.symbol.getText( ) );
		}
		super.updateTargetEntity( target );
	}

}
