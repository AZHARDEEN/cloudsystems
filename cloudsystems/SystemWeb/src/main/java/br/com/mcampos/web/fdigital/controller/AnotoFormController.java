package br.com.mcampos.web.fdigital.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
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

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.fdigital.form.AnotoFormSession;
import br.com.mcampos.jpa.fdigital.AnotoForm;
import br.com.mcampos.jpa.fdigital.AnotoFormUser;
import br.com.mcampos.jpa.fdigital.AnotoPage;
import br.com.mcampos.jpa.fdigital.AnotoPageField;
import br.com.mcampos.jpa.fdigital.FormMedia;
import br.com.mcampos.jpa.fdigital.Pad;
import br.com.mcampos.web.core.SimpleTableController;
import br.com.mcampos.web.core.UploadMedia;
import br.com.mcampos.web.core.listbox.BasicListRenderer;
import br.com.mcampos.web.fdigital.util.PadFile;
import br.com.mcampos.web.renderer.fdigital.ClientListRenderer;
import br.com.mcampos.web.renderer.fdigital.FormMediaListRenderer;
import br.com.mcampos.web.renderer.fdigital.PadListRenderer;

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
		if( entity != null ) {
			entity = lazyInitForm( entity );
			recordIP.setValue( entity.getApplication( ) );
			recordImagePath.setValue( entity.getImagePath( ) );
			recordIcrImage.setValue( entity.getIcr( ).toString( ) );
			recordConcatPgc.setValue( entity.getConcatenate( ).toString( ) );

			application.setText( entity.getApplication( ) );
			editIcrImage.setChecked( entity.getIcr( ) );
			editConcatPgc.setChecked( entity.getConcatenate( ) );
			editImagePath.setText( entity.getImagePath( ) );

			listAttachs.setModel( new ListModelList<Pad>( entity.getPads( ) ) );
			listUsers.setModel( new ListModelList<AnotoFormUser>( entity.getClients( ) ) );
			listAttachsOther.setModel( new ListModelList<FormMedia>( entity.getMedias( ) ) );
		}
		else {
			recordIP.setValue( "" );
			recordImagePath.setValue( "" );
			recordIcrImage.setValue( "" );
			recordConcatPgc.setValue( "" );

			application.setRawValue( "" );
			editIcrImage.setChecked( false );
			editConcatPgc.setChecked( false );
			editImagePath.setRawValue( "" );
			listAttachs.setModel( new ListModelList<Pad>( ) );
			listUsers.setModel( new ListModelList<AnotoFormUser>( ) );
			listAttachsOther.setModel( new ListModelList<FormMedia>( ) );

		}
		super.showFields( entity );
		btnAddAttach.setDisabled( entity == null );
		btnAddUser.setDisabled( entity == null );
		btnAddAttachOther.setDisabled( entity == null );
	}

	@Override
	protected void updateTargetEntity( AnotoForm target )
	{
		super.updateTargetEntity( target );
		if( target != null ) {
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
		listUsers.setItemRenderer( new ClientListRenderer( ) );
		listAttachsOther.setItemRenderer( new FormMediaListRenderer( ) );
	}

	@Override
	public void onRefresh( )
	{
		super.onRefresh( );
		btnAddAttach.setDisabled( true );
		btnAddUser.setDisabled( true );
		btnAddAttachOther.setDisabled( true );
	}

	@Listen( "onUpload=#btnAddAttach" )
	public void onUpload( UploadEvent evt )
	{
		if( evt != null && getListbox( ).getSelectedItem( ) != null ) {
			try {
				MediaDTO m = UploadMedia.getMedia( evt.getMedia( ) );
				uploadPADFile( m );
			}
			catch( IOException e ) {
				e.printStackTrace( );
			}
			evt.stopPropagation( );
		}
	}

	/*
	 * private void processUpload( MediaDTO m ) { if ( m == null ) { return; }
	 * if ( m.getFormat( ).equalsIgnoreCase( "PAD" ) ) { uploadPADFile( m ); }
	 * else { uploadOtherFile( m ); } }
	 */

	private AnotoForm lazyInitForm( AnotoForm form )
	{
		if( form == null ) {
			return null;
		}
		try {
			if( form.getPads( ).size( ) > 0 ) {
				form.getPads( ).get( 0 );
			}
			if( form.getMedias( ).size( ) > 0 ) {
				form.getMedias( ).get( 0 );
			}
			if( form.getClients( ).size( ) > 0 ) {
				form.getClients( ).get( 0 );
			}
		}
		catch( Exception e ) {
			form = getSession( ).getRelationships( form );
		}
		return form;
	}

	private void uploadPADFile( MediaDTO m )
	{
		AnotoForm form = getListbox( ).getSelectedItem( ).getValue( );
		form = lazyInitForm( form );
		getListbox( ).getSelectedItem( ).setValue( form );
		PadFile file = new PadFile( getSession( ), (AnotoForm) getListbox( ).getSelectedItem( ).getValue( ) );
		if( file.isPadFile( m ) ) {
			List<AnotoPage> pages = new ArrayList<AnotoPage>( file.getPages( ).size( ) );
			for( Element e : file.getPages( ) ) {
				AnotoPage page = new AnotoPage( );
				page.getId( ).setId( file.getPageAddress( e ) );
				page.setDescription( file.getPageName( e ) );
				pages.add( page );
				getFields( file, e, page );
			}
			form = getSession( ).add( getPrincipal( ), form, m, pages );
			listAttachs.setModel( new ListModelList<Pad>( form.getPads( ) ) );
		}
		else {
			showErrorMessage( "Erro ao carregar o arquivo " + m.getName( ), "Inserir arquivo PAD" );
		}
	}

	private void getFields( PadFile file, Element e, AnotoPage page )
	{
		for( Element item : file.getFields( e ) )
		{
			AnotoPageField field = new AnotoPageField( );
			field.getId( ).setId( file.getProperty( item, "name" ) );
			field.setTop( Integer.parseInt( file.getProperty( item, "top" ) ) );
			field.setHeight( Integer.parseInt( file.getProperty( item, "height" ) ) );
			field.setLeft( Integer.parseInt( file.getProperty( item, "left" ) ) );
			field.setWidth( Integer.parseInt( file.getProperty( item, "width" ) ) );
			field.setExport( true );
			field.setIrc( false );
			field.setSearch( true );
			page.add( field );
		}
	}

	/*
	 * private void uploadOtherFile( MediaDTO m ) { AnotoForm form = getListbox(
	 * ).getSelectedItem( ).getValue( ); getSession( ).add( form, m ); }
	 */
}
