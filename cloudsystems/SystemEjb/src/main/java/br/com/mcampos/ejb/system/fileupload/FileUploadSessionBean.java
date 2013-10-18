package br.com.mcampos.ejb.system.fileupload;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.dto.AssefazDTO;
import br.com.mcampos.dto.RejectedDTO;
import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.ejb.user.client.ClientSessionLocal;
import br.com.mcampos.ejb.user.client.entry.ClientEntrySessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;
import br.com.mcampos.ejb.user.document.type.DocumentTypeSessionLocal;
import br.com.mcampos.ejb.user.person.PersonSessionLocal;
import br.com.mcampos.jpa.system.FileUpload;
import br.com.mcampos.jpa.system.Media;
import br.com.mcampos.jpa.system.RejectedRecord;
import br.com.mcampos.jpa.system.UploadStatus;
import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.ClientEntry;
import br.com.mcampos.jpa.user.Collaborator;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class FileUploadSessionBean
 */
@Stateless( mappedName = "FileUploadSession", name = "FileUploadSession" )
public class FileUploadSessionBean extends SimpleSessionBean<FileUpload> implements FileUploadSession, FileUploadSessionLocal
{
	private static final long serialVersionUID = -6741288363235165463L;

	@EJB
	private MediaSessionBeanLocal mediaSession;

	@EJB
	private UploadStatusSessionLocal statusSession;

	@EJB
	private PersonSessionLocal personSession;

	@EJB
	private ClientSessionLocal clientSession;

	@EJB
	private ClientEntrySessionLocal entrySession;

	@EJB
	private CollaboratorSessionLocal collaboratorSession;

	@EJB
	private DocumentTypeSessionLocal documentTypeSession;

	@Override
	public FileUpload addNewFile( PrincipalDTO auth, MediaDTO media )
	{
		if ( auth == null || media == null || SysUtils.isEmpty( media.getName( ) ) ) {
			throw new InvalidParameterException( );
		}
		FileUpload entity = new FileUpload( );
		Media mediaEntity = this.mediaSession.findByName( media.getName( ) );
		if ( mediaEntity == null ) {
			entity.setStatus( this.statusSession.get( UploadStatus.sucess ) );
		}
		else {
			entity.setStatus( this.statusSession.get( UploadStatus.duplicated ) );
		}
		mediaEntity = this.mediaSession.add( media );
		entity.setMedia( mediaEntity );
		entity.setCollaborator( this.collaboratorSession.find( auth ) );
		return this.add( auth, entity );
	}

	public void deleteAllByMediaId( PrincipalDTO auth, Serializable mediaId )
	{
		String sql;

		sql = "delete from FileUpload o where o.media.id = ?1";
		Query query = this.getEntityManager( ).createQuery( sql ).setParameter( 1, mediaId );
		query.executeUpdate( );
	}

	@Override
	protected Class<FileUpload> getEntityClass( )
	{
		return FileUpload.class;
	}

	public List<FileUpload> getAll( Collaborator c )
	{
		if ( c == null || c.getCompany( ) == null ) {
			throw new InvalidParameterException( );
		}
		return this.findByNamedQuery( FileUpload.getAllByCompany, c.getCompany( ) );
	}

	@Override
	public FileUpload update( FileUpload entity, ArrayList<RejectedDTO> rejecteds )
	{
		FileUpload newEntity = this.merge( entity );
		if ( rejecteds != null && rejecteds.size( ) > 0 ) {
			newEntity.setRejecteds( rejecteds.size( ) );
			for ( RejectedDTO line : rejecteds ) {
				RejectedRecord rejected = new RejectedRecord( );
				rejected.setFileUpload( newEntity );
				rejected.getId( ).setId( line.getLineNumber( ) );
				rejected.setValue( line.getRecord( ) );
				rejected.setCause( line.getCause( ) );
				this.getEntityManager( ).persist( rejected );
			}
		}
		else {
			newEntity.setRejecteds( 0 );
		}
		return newEntity;
	}

	@Override
	public AssefazDTO add( PrincipalDTO auth, FileUpload entity, AssefazDTO dto, boolean bCreateIfNotExists )
	{
		Person p = this.findPerson( entity.getCollaborator( ), dto );
		if ( p == null ) {
			if ( bCreateIfNotExists )
			{
				/*
				 * TODO: Shoulda Create a person????
				 */
				p = this.createPerson( entity, dto );
			}
			if ( p == null ) {
				dto.invalidate( "Person does not exists." );
				return dto;
			}
		}
		Client c = this.clientSession.getClient( auth, p );
		if ( c == null ) {
			/*
			 * TODO: Shoulda Create a client????
			 */
			c = new Client( );
			c.setClient( p );
			c.setCompany( entity.getCollaborator( ).getCompany( ) );
			c = this.clientSession.addNewPerson( auth, c );
		}
		ClientEntry entry = new ClientEntry( );

		entry.setClient( c );
		entry.setCollaboratorId( entity.getId( ).getSequence( ) );
		entry.setCycle( 201304 );
		entry.setMediaId( entity.getId( ).getMedia( ) );
		entry.setValue( new BigDecimal( dto.getPayment( ) ) );
		entry.setLineNumber( dto.getLineNumber( ) );
		this.entrySession.merge( entry );
		return dto;
	}

	private Person findPerson( Collaborator c, AssefazDTO dto )
	{
		Person p = this.personSession.getByDocument( dto.getId( ).getId( ) );
		if ( p == null ) {
			p = this.personSession.getByDocument( dto.getCpf( ) );
			/*
			 * Found by cpf. Put Assefaz code???
			 */
			if ( p == null ) {
				if ( SysUtils.isEmpty( dto.getEmail( ) ) == false ) {
					p = this.personSession.getByDocument( dto.getEmail( ) );
					if ( p != null ) {
						/*
						 * Found by email. Put Assefaz code and cpf???
						 */
						p.add( new UserDocument( dto.getId( ).getId( ), this.documentTypeSession.get( UserDocument.ASSEFAZ ) ) );
						p.add( new UserDocument( dto.getCpf( ), this.documentTypeSession.get( UserDocument.CPF ) ) );
						p = this.personSession.merge( p );
					}
				}
			}
			else {
				p.add( new UserDocument( dto.getId( ).getId( ), this.documentTypeSession.get( UserDocument.ASSEFAZ ) ) );
				p = this.personSession.merge( p );
			}
		}
		return p;
	}

	private Person createPerson( FileUpload file, AssefazDTO dto )
	{
		Person p = new Person( );

		p.setBirthDate( dto.getBirthDate( ) );
		p.setName( dto.getName( ) );
		p.add( new UserDocument( dto.getId( ).getId( ), this.documentTypeSession.get( UserDocument.ASSEFAZ ) ) );
		if ( !SysUtils.isEmpty( dto.getCpf( ) ) ) {
			p.add( new UserDocument( dto.getCpf( ), this.documentTypeSession.get( UserDocument.CPF ) ) );
		}
		if ( !SysUtils.isEmpty( dto.getEmail( ) ) ) {
			p.add( new UserDocument( dto.getEmail( ), this.documentTypeSession.get( UserDocument.EMAIL ) ) );
		}
		return this.personSession.merge( p );
	}

	@Override
	public List<FileUpload> getAllByMedia( Media media )
	{
		return this.findByNamedQuery( FileUpload.getAllByMedia, media );
	}
}
