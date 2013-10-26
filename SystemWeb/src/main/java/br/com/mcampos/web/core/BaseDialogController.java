package br.com.mcampos.web.core;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;

import br.com.mcampos.ejb.core.BaseSessionInterface;

public abstract class BaseDialogController<BEAN extends BaseSessionInterface> extends BaseDBController<BEAN>
{
	private static final long serialVersionUID = 7694041038977189864L;
	@Wire( "#cmdSubmit" )
	private Component submit;

	protected abstract void onOk( );

	protected abstract boolean validate( );

	public BaseDialogController( )
	{
		super( );
	}

	@Listen( "onClick = #cmdSubmit; onOK = textbox " )
	public void onClickOk( )
	{
		if ( validate( ) == true ) {
			try {
				onOk( );
			}
			catch ( Exception e ) {
				showErrorMessage( "Não foi possível completar a ação", "Confirmar Ação" );
			}
		}
	}

	@Listen( "onClick = #cmdCancel" )
	public void onClickCancel( )
	{
		Component target = this.submit.getParent( );

		while ( target != null ) {
			if ( target.getId( ).equals( "mdiApplication" ) ) {
				target.getChildren( ).clear( );
				break;
			}
			target = target.getParent( );
		}
	}

}
