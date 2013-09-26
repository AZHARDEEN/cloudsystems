package br.com.mcampos.web.inep.controller.station;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Radiogroup;

public class InterviewerController extends BaseStationController
{
	private static final long serialVersionUID = -8814974018344374047L;

	@Wire( "radiogroup" )
	private Radiogroup grade;

	@Override
	protected void cleanUp( )
	{
		for ( Checkbox checkbox : this.elements ) {
			if ( checkbox.isChecked( ) ) {
				checkbox.setChecked( false );
			}
		}
		this.grade.setSelectedIndex( -1 );
	}

	@Wire( "checkbox" )
	private Checkbox[ ] elements;

	@Override
	protected boolean validate( )
	{
		if ( this.grade.getSelectedItem( ) == null ) {
			Messagebox.show( "Por favor, informe todos os items da avaliação - Nota da entrevista", "Nota da Entrevista", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		if ( this.validateElements( ) == false ) {
			Messagebox.show( "Por favor, informe 3(três) elementos provocadores", "Elementos Provocadores", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		return true;
	}

	@Override
	protected void proceed( )
	{
	}

	public boolean validateElements( )
	{
		if ( this.elements == null ) {
			return false;
		}
		int nSize = 0;
		for ( Checkbox checkbox : this.elements ) {
			if ( checkbox.isChecked( ) ) {
				nSize++;
			}
			if ( nSize > 3 ) {
				return false;
			}
		}
		return nSize == 3;
	}

}
