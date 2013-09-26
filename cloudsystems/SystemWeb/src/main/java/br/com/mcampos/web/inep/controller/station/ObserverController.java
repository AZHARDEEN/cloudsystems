package br.com.mcampos.web.inep.controller.station;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Radiogroup;

public class ObserverController extends BaseStationController
{
	@Override
	protected void cleanUp( )
	{
		if ( this.grades == null ) {
			return;
		}
		for ( Radiogroup item : this.grades ) {
			if ( item.getSelectedItem( ) != null ) {
				item.setSelectedIndex( -1 );
			}
		}
	}

	private static final long serialVersionUID = -8629305115083303102L;

	@Wire( "radiogroup" )
	private Radiogroup[ ] grades;

	@Override
	protected boolean validate( )
	{
		if ( this.validateGrades( ) == false ) {
			Messagebox.show( "Por favor, informe todos os items da avaliação", "Erro", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		return true;
	}

	@Override
	protected void proceed( )
	{
	}

	private boolean validateGrades( )
	{
		if ( this.grades == null ) {
			return false;
		}
		for ( Radiogroup item : this.grades ) {
			if ( item.getSelectedItem( ) == null ) {
				return false;
			}
		}
		return true;
	}

}
