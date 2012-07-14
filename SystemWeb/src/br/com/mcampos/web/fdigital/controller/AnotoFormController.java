package br.com.mcampos.web.fdigital.controller;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.fdigital.Pad;
import br.com.mcampos.ejb.fdigital.form.AnotoForm;
import br.com.mcampos.ejb.fdigital.form.AnotoFormSession;
import br.com.mcampos.web.core.SimpleTableController;
import br.com.mcampos.web.core.listbox.BasicListRenderer;
import br.com.mcampos.web.fdigital.renderer.PadListRenderer;

public class AnotoFormController extends SimpleTableController<AnotoFormSession, AnotoForm>
{
	private static final long serialVersionUID = 4679060005827778998L;

	@Wire
	private Label recordIP;

	@Wire
	private Label recordIcrImage;

	@Wire
	private Label recordConcatPgc;

	@Wire
	private Label recordImagePath;

	@Wire( "#editIP" )
	private Textbox application;

	@Wire
	private Checkbox editIcrImage;

	@Wire
	private Checkbox editConcatPgc;

	@Wire
	private Textbox editImagePath;

	@Wire
	private Listbox listAttachs;

	@Override
	protected Class<AnotoFormSession> getSessionClass( )
	{
		return AnotoFormSession.class;
	}

	@Override
	protected void showFields( AnotoForm entity )
	{
		if ( entity != null ) {
			this.recordIP.setValue( entity.getApplication( ) );
			this.recordImagePath.setValue( entity.getImagePath( ) );
			this.recordIcrImage.setValue( entity.getIcr( ).toString( ) );
			this.recordConcatPgc.setValue( entity.getConcatenate( ).toString( ) );

			this.application.setText( entity.getApplication( ) );
			this.editIcrImage.setChecked( entity.getIcr( ) );
			editConcatPgc.setChecked( entity.getConcatenate( ) );
			editImagePath.setText( entity.getImagePath( ) );
			listAttachs.setModel( new ListModelList<Pad>( entity.getPads( ) ) );
		}
		else {
			this.recordIP.setValue( "" );
			this.recordImagePath.setValue( "" );
			this.recordIcrImage.setValue( "" );
			this.recordConcatPgc.setValue( "" );

			this.application.setRawValue( "" );
			this.editIcrImage.setChecked( false );
			editConcatPgc.setChecked( false );
			editImagePath.setRawValue( "" );
			listAttachs.setModel( new ListModelList<Pad>( ) );

		}
		super.showFields( entity );
	}

	@Override
	protected void updateTargetEntity( AnotoForm target )
	{
		super.updateTargetEntity( target );
		if ( target != null ) {
			target.setApplication( application.getText( ) );
			target.setConcatenate( editConcatPgc.isChecked( ) );
			target.setIcr( editIcrImage.isChecked( ) );
			target.setImagePath( editImagePath.getText( ) );
		}
	}

	@Override
	protected ListitemRenderer<?> getListRenderer( )
	{
		return new BasicListRenderer( );
	}

	@Override
	protected boolean validateEntity( AnotoForm target, int operation )
	{
		return true;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		listAttachs.setItemRenderer( new PadListRenderer( ) );
	}

}
