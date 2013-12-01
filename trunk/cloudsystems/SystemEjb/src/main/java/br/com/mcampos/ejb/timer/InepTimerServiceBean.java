package br.com.mcampos.ejb.timer;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.inep.media.InepMediaSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.inep.test.InepTestSessionLocal;
import br.com.mcampos.ejb.system.fileupload.FileUploadSessionLocal;
import br.com.mcampos.ejb.system.fileupload.UploadStatusSessionLocal;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTaskPK;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.jpa.inep.InepTestPK;
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

	@PersistenceContext( unitName = "SystemEJB" )
	private EntityManager em;// **< Entity Manager. Esta variável é fundamental para todo o sistema

	@EJB
	private CompanySessionLocal companySession;

	@EJB
	private InepTaskSessionLocal taskSession;

	@EJB
	private InepTestSessionLocal testSession;

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
			try {
				if ( "audio/mpeg".equalsIgnoreCase( item.getMedia( ).getMimeType( ) ) || "audio/wav".equalsIgnoreCase( item.getMedia( ).getMimeType( ) ) ) {
					this.processAudioFile( item );
				}
				else if ( "application/pdf".equalsIgnoreCase( item.getMedia( ).getMimeType( ) ) ) {
					this.processPDFFile( item );
				}
				else {
					item.setError( item.getMedia( ).getMimeType( ) + " Não está previsto no sistema." );
					LOGGER.error( item.getMedia( ).getMimeType( ) + " Não está previsto no sistema." );
					continue;
				}
			}
			catch ( Exception e ) {
				LOGGER.error( "Error processing item " + item.getId( ).getMedia( ) + " for company " + item.getId( ).getCompanyId( ) );
				item.setError( e.getMessage( ) );
			}
			this.em.flush( );
		}
	}

	private InepTask getTaskFromFilename( FileUpload item, InepSubscription s )
	{
		String fileName = item.getMedia( ).getName( );
		try {
			String task = fileName.substring( 13, 14 );
			if ( SysUtils.isEmpty( task ) ) {
				item.setError( "tarefa Inválida para o arquivo " + fileName );
				return null;
			}
			Integer taskId = 0;
			try {
				taskId = Integer.valueOf( task );
			}
			catch ( Exception e ) {
				item.setError( "tarefa Inválida para o arquivo " + fileName );
				return null;
			}
			InepTask taskEntity = this.getTask( s, taskId );
			if ( taskEntity == null ) {
				item.setError( "A tarefa " + taskId + "não foi criada para o evento " + s.getEvent( ).getId( ) );
				return null;
			}
			return taskEntity;
		}
		catch ( Exception e ) {
			LOGGER.error( "Could not get task id from filename " + fileName, e );
			return null;
		}
	}

	private void processPDFFile( FileUpload item )
	{
		String fileName = this.getFilename( item );
		String subscription = fileName.substring( 0, 12 );
		InepSubscription s = this.getSubscription( subscription );
		if ( s == null ) {
			item.setError( "Inscrição " + subscription + " Não encontrado para o arquivo " + fileName );
			return;
		}
		InepTask taskEntity = this.getTaskFromFilename( item, s );
		if ( taskEntity == null ) {
			item.setError( "Nao foi possível obter a tarefa para o evento " + s.getEvent( ).getId( ) + " a partir do arquivo " );
			return;
		}
		InepTest test = this.getTest( s, taskEntity );
		this.inepMediaSession.addPDF( test, s.getId( ) + "_" + test.getId( ).getTaskId( ), item );
		item.setStatus( this.statusSession.get( 4 ) );
	}

	private String getFilename( FileUpload item )
	{
		String fileName = item.getMedia( ).getName( );
		fileName = fileName.replaceAll( "\\s+", "" );
		if ( !fileName.equals( item.getMedia( ).getName( ) ) ) {
			item.getMedia( ).setName( fileName );
		}
		return fileName;
	}

	private void processAudioFile( FileUpload item )
	{
		String fileName = this.getFilename( item );
		String subscription = fileName.substring( 0, 12 );
		LOGGER.info( "Searching for subscription [" + subscription + "] " );
		InepSubscription s = this.getSubscription( subscription );
		if ( s == null ) {
			item.setError( "Inscription " + subscription + " Não encontrado" );
			return;
		}
		this.inepMediaSession.addAudio( s, item.getMedia( ) );
		item.setStatus( this.statusSession.get( 4 ) );
	}

	private InepSubscription getSubscription( String id )
	{
		InepSubscription s = this.subscriptionSession.get( id );
		if ( s == null ) {
			LOGGER.error( "Inscription " + id + " Não encontrado" );
		}
		return s;
	}

	private InepTask getTask( InepSubscription s, Integer id )
	{
		InepTaskPK k = new InepTaskPK( s.getEvent( ), id );
		return this.taskSession.get( k );
	}

	private InepTest getTest( InepSubscription subscription, InepTask task )
	{
		InepTest entity = this.testSession.get( new InepTestPK( task, subscription ) );
		if ( entity == null ) {
			entity = new InepTest( task );
			entity.setSubscription( subscription );
			entity = this.testSession.add( entity );
		}
		return entity;
	}

}
