package br.com.mcampos.ejb.system.fileupload;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.dto.upload.AssefazDTO;
import br.com.mcampos.dto.upload.RejectedDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.ejb.user.client.Client;
import br.com.mcampos.ejb.user.client.ClientSessionLocal;
import br.com.mcampos.ejb.user.client.entry.ClientEntry;
import br.com.mcampos.ejb.user.client.entry.ClientEntrySessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.document.type.DocumentTypeSessionLocal;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.ejb.user.person.PersonSessionLocal;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class FileUploadSessionBean
 */
@Stateless( mappedName = "FileUploadSession", name = "FileUploadSession" )
@LocalBean
public class FileUploadSessionBean extends SimpleSessionBean<FileUpload> implements FileUploadSession
{
	@EJB
	MediaSessionBeanLocal mediaSession;

	@EJB
	UploadStatusSessionLocal statusSession;

	@EJB
	PersonSessionLocal personSession;

	@EJB
	ClientSessionLocal clientSession;

	@EJB
	ClientEntrySessionLocal entrySession;

	@EJB
	DocumentTypeSessionLocal documentTypeSession;

	@Override
	public FileUpload addNewFile( Collaborator c, MediaDTO media )
	{
		if ( c == null || media == null || SysUtils.isEmpty( media.getName( ) ) ) {
			throw new InvalidParameterException( );
		}
		FileUpload entity = new FileUpload( );
		Media mediaEntity = this.mediaSession.findByName( media.getName( ) );
		if ( mediaEntity == null ) {
			mediaEntity = this.mediaSession.add( media );
			entity.setStatus( this.statusSession.get( UploadStatus.sucess ) );
		}
		else {
			entity.setStatus( this.statusSession.get( UploadStatus.duplicated ) );
		}
		entity.setMedia( mediaEntity );
		entity.setCollaborator( c );
		return persist( entity );
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
		return findByNamedQuery( FileUpload.getAllByCompany, c.getCompany( ) );
	}

	@Override
	public FileUpload update( FileUpload entity, ArrayList<RejectedDTO> rejecteds )
	{
		FileUpload newEntity = merge( entity );
		if ( rejecteds != null && rejecteds.size( ) > 0 ) {
			newEntity.setRejecteds( rejecteds.size( ) );
			for ( RejectedDTO line : rejecteds ) {
				RejectedRecord rejected = new RejectedRecord( );
				rejected.setFileUpload( newEntity );
				rejected.getId( ).setId( line.getLineNumber( ) );
				rejected.setValue( line.getRecord( ) );
				rejected.setCause( line.getCause( ) );
				getEntityManager( ).persist( rejected );
			}
		}
		else {
			newEntity.setRejecteds( 0 );
		}
		return newEntity;
	}

	@Override
	public AssefazDTO add( FileUpload entity, AssefazDTO dto, boolean bCreateIfNotExists )
	{
		Person p = findPerson( entity.getCollaborator( ), dto );
		if ( p == null ) {
			if ( bCreateIfNotExists )
			{
				/*
				 * TODO: Shoulda Create a person????
				 */
				p = createPerson( entity, dto );
			}
			if ( p == null ) {
				dto.invalidate( "Person does not exists." );
				return dto;
			}
		}
		Client c = this.clientSession.getClient( entity.getCollaborator( ), p );
		if ( c == null ) {
			/*
			 * TODO: Shoulda Create a client????
			 */
			c = new Client( );
			c.setClient( p );
			c.setCompany( entity.getCollaborator( ).getCompany( ) );
			c = this.clientSession.addNewPerson( entity.getCollaborator( ), c );
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
						p.add( new UserDocument( dto.getId( ).getId( ), this.documentTypeSession.get( UserDocument.typeAssefaz ) ) );
						p.add( new UserDocument( dto.getCpf( ), this.documentTypeSession.get( UserDocument.typeCPF ) ) );
						p = this.personSession.merge( p );
					}
				}
			}
			else {
				p.add( new UserDocument( dto.getId( ).getId( ), this.documentTypeSession.get( UserDocument.typeAssefaz ) ) );
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
		p.add( new UserDocument( dto.getId( ).getId( ), this.documentTypeSession.get( UserDocument.typeAssefaz ) ) );
		if ( !SysUtils.isEmpty( dto.getCpf( ) ) ) {
			p.add( new UserDocument( dto.getCpf( ), this.documentTypeSession.get( UserDocument.typeCPF ) ) );
		}
		if ( !SysUtils.isEmpty( dto.getEmail( ) ) ) {
			p.add( new UserDocument( dto.getEmail( ), this.documentTypeSession.get( UserDocument.typeEmail ) ) );
		}
		return this.personSession.merge( p );
	}
}
