package br.com.mcampos.web.inep.controller;

import java.util.List;

import org.omg.CORBA.portable.ApplicationException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import br.com.mcampos.jpa.inep.InepDistribution;
import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.inep.controller.event.CoordinatorEventChange;

public abstract class CoordinatorGradeController extends BaseController<Window>
{
	private static final long serialVersionUID = 5912281475527567535L;

	@Wire
	Button cmdInepSave;

	@Wire
	Button cmdCancel;

	@Wire
	Radiogroup sv6;

	@Wire( "radio" )
	Radio[ ] options;

	InepDistribution current;

	protected abstract void showGrade( int nIndex, InepDistribution grade );

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.getCmdInepSave( ).setVisible( false );
		this.getCmdCancel( ).setVisible( false );
		this.sv6.setSelectedItem( null );
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
						CoordinatorGradeController.this.onNotify( (CoordinatorEventChange) evt );
					}
					catch ( ApplicationException e ) {
						e.printStackTrace( );
						e = null;
					}
				}
			}
		} );

	}

	private Button getCmdInepSave( )
	{
		return this.cmdInepSave;
	}

	private Button getCmdCancel( )
	{
		return this.cmdCancel;
	}

	public void onNotify( CoordinatorEventChange evt ) throws ApplicationException
	{
		int nIndex = 0;
		List<InepDistribution> grades = evt.getGrades( );

		for ( InepDistribution grade : grades ) {
			if ( grade.getNota( ) == null ) {
				continue;
			}
			this.showGrade( nIndex++, grade );
		}
	}

	protected void showGrade( InepDistribution d )
	{
		if ( d == null || d.getNota( ) == null ) {
			return;
		}
		try {
			this.sv6.setSelectedIndex( d.getNota( ) );
		}
		catch ( Exception e ) {
			e = null;
		}
		for ( Radio r : this.options ) {
			r.setDisabled( true );
			r.setSclass( "" );
		}
		if ( d.getNota( ) != null ) {
			this.options[ d.getNota( ) ].setSclass( "strongRadio" );
		}
		this.setCurrent( d );
	}

	protected InepDistribution getCurrent( )
	{
		return this.current;
	}

	protected void setCurrent( InepDistribution current )
	{
		this.current = current;
	}
}
