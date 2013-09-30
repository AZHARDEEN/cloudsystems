package br.com.mcampos.web.inep.controller.station;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Messagebox;

import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepSubscriptionPK;

public class AudioUploadController extends BaseStationController
{
	private static final Logger LOGGER = LoggerFactory.getLogger( BaseStationController.class );

	private static final long serialVersionUID = 9068632794057474631L;

	private static final String SAVE_DIR = "/var/local/jboss/upload";

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
	}

	@Listen( "onUpload=#uploadAudio" )
	public void onUploadAudio( UploadEvent evt ) throws IOException
	{
		InepSubscription subscription = this.getCurrentSubscription( );
		if ( evt != null ) {
			Media media = evt.getMedia( );
			String msg = media.getName( ) + " for subscription " + subscription.getId( ).getId( ) + ". Size: "
					+ media.getStreamData( ).available( );
			try {
				this.saveAudio( subscription, media );
				LOGGER.info( "Uploading audio " + msg );
				Messagebox.show( "Audio enviado com sucesso: " + msg, "Enviar Audio", Messagebox.OK, Messagebox.INFORMATION );
			}
			catch ( Exception e ) {
				LOGGER.error( "ERROR Uploading audio " + media.getName( ) + " for subscription " + subscription.getId( ).getId( ) + ". Size: "
						+ media.getStreamData( ).available( ), e );
				this.getSession( ).storeException( e );
				Messagebox.show( "ERRO ao enviar: " + msg, "Enviar Audio", Messagebox.OK, Messagebox.INFORMATION );
			}
			evt.stopPropagation( );
		}
	}

	private void saveAudio( InepSubscription subscription, Media audio ) throws IOException
	{
		InepSubscriptionPK id = subscription.getId( );
		File dir = new File( SAVE_DIR + "/" + id.getCompanyId( ) + "/" + id.getEventId( ) );
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
	}
}
