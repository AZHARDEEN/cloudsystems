package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.omg.CORBA.portable.ApplicationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Toolbarbutton;

import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.inep.controller.event.CoordinatorEventChange;

public abstract class CoordinatorGradeController extends BaseController
{
	private static final long serialVersionUID = 5912281475527567535L;

	@Wire
	Toolbarbutton cmdInepSave;

	@Wire
	Toolbarbutton cmdCancel;

	@Wire
	Radiogroup sv6;

	@Wire( "radio" )
	Radio[ ] options;

	InepDistribution current;

	protected abstract void showGrade( int nIndex, InepDistribution grade );

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getCmdInepSave( ).setVisible( false );
		getCmdCancel( ).setVisible( false );
		this.sv6.setSelectedIndex( 0 );
		for ( Radio r : this.options ) {
			r.setDisabled( true );
		}
		EventQueues.lookup( TasksController.coordinatorEvent ).subscribe( new EventListener<Event>( )
		{
			@Override
			public void onEvent( Event evt )
			{
				if ( evt instanceof CoordinatorEventChange ) {
					try {
						onNotify( (CoordinatorEventChange) evt );
					}
					catch ( ApplicationException e ) {
						e.printStackTrace( );
						e = null;
					}
				}
			}
		} );

	}

	private Toolbarbutton getCmdInepSave( )
	{
		return this.cmdInepSave;
	}

	private Toolbarbutton getCmdCancel( )
	{
		return this.cmdCancel;
	}

	public void onNotify( CoordinatorEventChange evt ) throws ApplicationException
	{
		int nIndex = 0;
		List<InepDistribution> grades = evt.getGrades( );

		for ( InepDistribution grade : grades ) {
			showGrade( nIndex++, grade );
		}
	}

	protected void showGrade( InepDistribution d )
	{
		if ( d == null ) {
			return;
		}
		this.sv6.setSelectedIndex( d.getNota( ) );
		for ( Radio r : this.options ) {
			r.setDisabled( true );
			r.setSclass( "" );
		}
		this.options[ d.getNota( ) ].setSclass( "strongRadio" );
		setCurrent( d );
	}

	protected InepDistribution getCurrent( )
	{
		return this.current;
	}

	protected void setCurrent( InepDistribution current )
	{
		this.current = current;
	}

	@Listen( "onClick = #cmdObs" )
	public void onClickComments( )
	{
		if ( getCurrent( ) != null ) {
			Component comp = Executions.createComponents( "/private/inep/dlg_comment.zul", null, null );
			if ( comp instanceof DlgComment ) {
				DlgComment dlg = ( (DlgComment) comp );

				dlg.getCmdSaveComment( ).setVisible( false );
				dlg.setDistribution( getCurrent( ) );
				dlg.doModal( );
			}
		}
		else {
			Messagebox.show( "Antes de visualizar um comentário sobre a correção, uma tarefa deve ser selecionada primeiro",
					"Correção", Messagebox.OK, Messagebox.INFORMATION );
		}
	}

}
