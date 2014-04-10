package br.com.mcampos.web.inep.controller.station;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.inep.StationGradeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepSubscriptionPK;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.UploadMedia;

public class AudioUploadController extends BaseStationController
{
	private static final Logger LOGGER = LoggerFactory.getLogger( BaseStationController.class );

	private static final long serialVersionUID = 9068632794057474631L;

	private static final String SAVE_DIR = "/var/local/jboss/upload";

	@Wire
	private Button uploadAudio;

	@Override
	protected boolean validate( )
	{
		return true;
	}

	@Override
	protected void proceed( )
	{
	}

	@Override
	protected void cleanUp( )
	{
		super.cleanUp( );
	}

	@Listen( "onUpload=button" )
	public void onUploadAudio( UploadEvent evt ) throws IOException
	{
		InepSubscription subscription = getCurrentSubscription( );
		if( evt != null && subscription != null ) {
			evt.stopPropagation( );
			Media media = evt.getMedia( );
			String msg = media.getName( ) + " for subscription " + subscription.getId( ).getId( ) + ". Size: "
					+ media.getStreamData( ).available( );
			try {
				saveAudio( subscription, media );
				LOGGER.info( "Uploading audio " + msg );
				Messagebox.show( "Audio enviado com sucesso: " + msg, "Enviar Audio", Messagebox.OK, Messagebox.INFORMATION );
			}
			catch ( Exception e ) {
				LOGGER.error( "ERROR Uploading audio " + media.getName( ) + " for subscription " + subscription.getId( ).getId( ) + ". Size: "
						+ media.getStreamData( ).available( ), e );
				this.getSession( ).storeException( e );
				Messagebox.show( "ERRO ao enviar: " + msg, "Enviar Audio", Messagebox.OK, Messagebox.ERROR );
			}
		}
	}

	private void saveAudio( InepSubscription subscription, Media audio ) throws IOException
	{
		PrincipalDTO auth = getPrincipal( );

		List<InepMedia> medias = this.getSession( ).lookupForName( auth, subscription, audio.getName( ) );
		onOkSave( subscription, audio );
		if ( !SysUtils.isEmpty( medias ) ) {
			String subscriptions = "";
			for ( InepMedia item : medias ) {
				subscriptions += item.getSubscription( ).getId( ).getId( ) + "\n";
			}
			if ( medias.size( ) == 1 ) {
				Messagebox.show( "ATENÇÃO: Este audio, " + audio.getName( ) + ", já está associada a uma inscrição:\n" + subscriptions,
						"Enviar Audio", Messagebox.OK, Messagebox.EXCLAMATION );
			}
			else {

				Messagebox.show( "ATENÇÃO: Este audio, " + audio.getName( ) + ", já está associada a mais de uma inscrição, o que é estranho:\n"
						+ subscriptions,
						"Enviar Audio", Messagebox.OK, Messagebox.EXCLAMATION );
			}
		}
		cleanUp( );
	}

	public void onOkSave( InepSubscription subscription, Media audio ) throws IOException
	{
		PrincipalDTO auth = getPrincipal( );
		InepSubscriptionPK id = subscription.getId( );
		File dir = new File( SAVE_DIR + "/" + id.getCompanyId( ) + "/" + id.getEventId( ) + "/audio" );
		if ( !dir.exists( ) ) {
			dir.mkdirs( );
		}
		String[ ] parts = audio.getName( ).split( "\\." );

		File file = new File( dir, id.getId( ) + "." + parts[ parts.length - 1 ] );
		if ( file.exists( ) ) {
			file.delete( );
		}
		FileOutputStream out = new FileOutputStream( file );
		int nRead = 0;
		byte[ ] buffer = new byte[ audio.getStreamData( ).available( ) ];
		nRead = audio.getStreamData( ).read( buffer );
		out.write( buffer, 0, nRead );
		out.close( );
		MediaDTO dto = UploadMedia.getMedia( audio, false );
		dto.setSize( buffer.length );
		dto.setPath( file.getPath( ) );
		this.getSession( ).storeUploadInformation( auth, subscription, dto );
		buffer = null;
	}

	@Override
	protected void showGradeIfexists( StationGradeDTO grade )
	{

	}

	@Override
	public void onCancel( )
	{
		super.onCancel( );
		uploadAudio.setVisible( false );
	}

	@Override
	protected void showInfo( InepSubscription s )
	{
		super.showInfo( s );
		uploadAudio.setVisible( true );
	}

}
