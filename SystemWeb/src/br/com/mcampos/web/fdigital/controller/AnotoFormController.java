package br.com.mcampos.web.fdigital.controller;

import java.io.IOException;

import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.fdigital.Pad;
import br.com.mcampos.ejb.fdigital.form.AnotoForm;
import br.com.mcampos.ejb.fdigital.form.AnotoFormSession;
import br.com.mcampos.ejb.fdigital.form.media.FormMedia;
import br.com.mcampos.ejb.fdigital.form.user.AnotoFormUser;
import br.com.mcampos.web.core.SimpleTableController;
import br.com.mcampos.web.core.UploadMedia;
import br.com.mcampos.web.core.listbox.BasicListRenderer;
import br.com.mcampos.web.fdigital.renderer.ClientListRenderer;
import br.com.mcampos.web.fdigital.renderer.FormMediaListRenderer;
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

	@Wire
	private Listbox listAttachsOther;

	@Wire
	private Listbox listUsers;

	@Wire
	private Button btnAddAttach;

	@Wire
	private Button btnAddUser;

	@Wire
	private Button btnAddAttachOther;

	@Override
	protected Class<AnotoFormSession> getSessionClass( )
	{
		return AnotoFormSession.class;
	}

	@Override
	protected void showFields( AnotoForm entity )
	{
		if ( entity != null ) {
			entity = getSession( ).getRelationships( entity );
			this.recordIP.setValue( entity.getApplication( ) );
			this.recordImagePath.setValue( entity.getImagePath( ) );
			this.recordIcrImage.setValue( entity.getIcr( ).toString( ) );
			this.recordConcatPgc.setValue( entity.getConcatenate( ).toString( ) );

			this.application.setText( entity.getApplication( ) );
			this.editIcrImage.setChecked( entity.getIcr( ) );
			this.editConcatPgc.setChecked( entity.getConcatenate( ) );
			this.editImagePath.setText( entity.getImagePath( ) );

			this.listAttachs.setModel( new ListModelList<Pad>( entity.getPads( ) ) );
			this.listUsers.setModel( new ListModelList<AnotoFormUser>( entity.getClients( ) ) );
			listAttachsOther.setModel( new ListModelList<FormMedia>( entity.getMedias( ) ) );
		}
		else {
			this.recordIP.setValue( "" );
			this.recordImagePath.setValue( "" );
			this.recordIcrImage.setValue( "" );
			this.recordConcatPgc.setValue( "" );

			this.application.setRawValue( "" );
			this.editIcrImage.setChecked( false );
			this.editConcatPgc.setChecked( false );
			this.editImagePath.setRawValue( "" );
			this.listAttachs.setModel( new ListModelList<Pad>( ) );
			this.listUsers.setModel( new ListModelList<AnotoFormUser>( ) );
			listAttachsOther.setModel( new ListModelList<FormMedia>( ) );

		}
		super.showFields( entity );
		this.btnAddAttach.setDisabled( entity == null );
		this.btnAddUser.setDisabled( entity == null );
		this.btnAddAttachOther.setDisabled( entity == null );
	}

	@Override
	protected void updateTargetEntity( AnotoForm target )
	{
		super.updateTargetEntity( target );
		if ( target != null ) {
			target.setApplication( this.application.getText( ) );
			target.setConcatenate( this.editConcatPgc.isChecked( ) );
			target.setIcr( this.editIcrImage.isChecked( ) );
			target.setImagePath( this.editImagePath.getText( ) );
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
		this.listAttachs.setItemRenderer( new PadListRenderer( ) );
		this.listUsers.setItemRenderer( new ClientListRenderer( ) );
		listAttachsOther.setItemRenderer( new FormMediaListRenderer( ) );
	}

	@Override
	public void onRefresh( )
	{
		super.onRefresh( );
		this.btnAddAttach.setDisabled( true );
		this.btnAddUser.setDisabled( true );
		this.btnAddAttachOther.setDisabled( true );
	}

	@Listen( "onUpload=button" )
	public void onUpload( UploadEvent evt )
	{
		if ( evt != null && getListbox( ).getSelectedItem( ) != null ) {
			try {
				MediaDTO m = UploadMedia.getMedia( evt.getMedia( ) );
				processUpload( m );
			} catch( IOException e ) {
				e.printStackTrace( );
			}
			evt.stopPropagation( );
		}
	}

	private void processUpload( MediaDTO m )
	{
		if ( m == null )
			return;
		if ( m.getFormat( ).equalsIgnoreCase( "PAD" ) ) {
			uploadPADFile( m );
		}
		else
			uploadOtherFile( m );
	}

	private void uploadPADFile( MediaDTO m )
	{
		AnotoForm form = getListbox( ).getSelectedItem( ).getValue( );
		getSession( ).add( form, m );

	}

	private void uploadOtherFile( MediaDTO m )
	{
		AnotoForm form = getListbox( ).getSelectedItem( ).getValue( );
		getSession( ).add( form, m );
	}
}
