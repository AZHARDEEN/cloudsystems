package br.com.mcampos.web.controller.upload;

import java.io.IOException;

import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.system.fileupload.FileUpload;
import br.com.mcampos.ejb.system.fileupload.FileUploadSession;
import br.com.mcampos.ejb.system.fileupload.UploadStatus;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.core.UploadMedia;

public class FileUploadController extends BaseDBLoggedController<FileUploadSession>
{
	private static final long serialVersionUID = 2778375565046918178L;

	@Wire( "#listFiles" )
	private Listbox listBox;

	@Listen( "onUpload=#btnNewFile" )
	public void onUpload( UploadEvent evt )
	{
		if ( evt != null ) {
			try {
				MediaDTO m = UploadMedia.getMedia( evt.getMedia( ) );
				FileUpload entity = getSession( ).addNewFile( getPrincipal( ), m );
				if ( entity.getStatus( ).getId( ).equals( UploadStatus.sucess ) ) {
					processFile( entity, m );
				}
				addToListBox( entity );
			}
			catch ( IOException e ) {
				e.printStackTrace( );
			}
			evt.stopPropagation( );
		}
	}

	protected boolean processFile( FileUpload fileUpload, MediaDTO media )
	{
		return true;
	}

	private Listbox getListbox( )
	{
		assert ( this.listBox != null );
		return this.listBox;
	}

	@Override
	protected Class<FileUploadSession> getSessionClass( )
	{
		return FileUploadSession.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getListbox( ).setItemRenderer( new FileUploadRenderer( ) );
		getListbox( ).setModel( new ListModelList<FileUpload>( getSession( ).getAll( ) ) );
	}

	private void addToListBox( FileUpload entity )
	{
		if ( entity == null ) {
			return;
		}

		ListModelList<FileUpload> model = getModel( );
		if ( model == null ) {
			model = new ListModelList<FileUpload>( );
			getListbox( ).setModel( model );
		}
		model.add( 0, entity );
	}

	@SuppressWarnings( "unchecked" )
	private ListModelList<FileUpload> getModel( )
	{
		return (ListModelList<FileUpload>) (Object) getListbox( ).getListModel( );
	}
}
