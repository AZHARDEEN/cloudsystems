package br.com.mcampos.ejb.timer;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.inep.media.InepMediaSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.system.fileupload.FileUploadSessionLocal;
import br.com.mcampos.ejb.system.fileupload.UploadStatusSessionLocal;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.system.FileUpload;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class InepTimerServiceBean
 */
@Singleton
public class InepTimerServiceBean
{
	private static final Logger LOGGER = LoggerFactory.getLogger( InepTimerServiceBean.class );

	private final static Integer ID = 13623;

	@EJB
	private CompanySessionLocal companySession;

	@EJB
	private FileUploadSessionLocal uploadSession;

	@EJB
	private UploadStatusSessionLocal statusSession;

	@EJB
	private InepSubscriptionSessionLocal subscriptionSession;

	@EJB
	private InepMediaSessionLocal inepMediaSession;

	@Schedule( minute = "*/3", hour = "*", persistent = false )
	public void process( )
	{
		Company c = this.companySession.get( ID );
		List<FileUpload> items = this.uploadSession.getFilesToProcess( c );
		if ( SysUtils.isEmpty( items ) ) {
			return;
		}
		LOGGER.info( "We have about " + items.size( ) + " records to process " );
		for ( FileUpload item : items ) {
			if ( item.getMedia( ) == null ) {
				item.setError( "Arquivo sem media associado" );
				continue;
			}
			String fileName = item.getMedia( ).getName( );
			String subscription = fileName.substring( 0, 12 );
			LOGGER.info( "Searching for subscription [" + subscription + "] " );

			InepSubscription s = this.subscriptionSession.get( subscription );
			if ( s == null ) {
				item.setError( "Inscription " + subscription + " Não encontrado" );
				LOGGER.error( "Inscription " + subscription + " Não encontrado" );
				continue;
			}
			InepMedia inepMedia = new InepMedia( s );
			inepMedia.setMedia( item.getMedia( ) );
			if ( "audio/mpeg".equalsIgnoreCase( item.getMedia( ).getMimeType( ) ) || "audio/wav".equalsIgnoreCase( item.getMedia( ).getMimeType( ) ) ) {
				inepMedia.setType( 2 );
			}
			else {
				item.setError( item.getMedia( ).getMimeType( ) + " Não está previsto no sistema." );
				LOGGER.error( item.getMedia( ).getMimeType( ) + " Não está previsto no sistema." );
				continue;
			}
			this.inepMediaSession.add( inepMedia );
			item.setStatus( this.statusSession.get( 4 ) );
		}
	}
}
