package br.com.mcampos.jpa.system;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.mcampos.jpa.BasicEntityRenderer;
import br.com.mcampos.jpa.user.Collaborator;
import br.com.mcampos.sysutils.SysUtils;

/**
 * The persistent class for the file_upload database table.
 * 
 */
@Entity
@Table( name = "file_upload", schema = "public" )
@NamedQueries( {
		@NamedQuery( name = FileUpload.getAllByCompany, query = "select o from FileUpload o where o.collaborator.company = ?1" ),
		@NamedQuery( name = FileUpload.getFilesToProcess, query = "select o from FileUpload o where o.id.companyId = ?1 and o.status.id in (1, 2)" ),
		@NamedQuery( name = FileUpload.getAllByMedia, query = "select o from FileUpload o where o.media = ?1" )
} )
public class FileUpload implements BasicEntityRenderer<FileUpload>, Comparable<FileUpload>
{
	private static final long serialVersionUID = 1L;
	public static final String getAllByCompany = "FileUpload.getAllByCompany";
	public static final String getFilesToProcess = "FileUpload.getFilesToProcess";
	public static final String getAllByMedia = "FileUpload.getAllByMedia";

	@EmbeddedId
	private FileUploadPK id;

	// bi-directional many-to-one association to UploadStatus
	@ManyToOne( optional = false )
	@JoinColumn( name = "ups_id_in", nullable = false )
	private UploadStatus status;

	// bi-directional many-to-one association to UploadStatus
	@ManyToOne( optional = false )
	@JoinColumn( name = "med_id_in", insertable = false, updatable = false, nullable = false )
	private Media media;

	@ManyToOne( optional = true )
	@JoinColumn( name = "med_dup_id_in", insertable = true, updatable = true, nullable = true )
	private Media duplicated;

	// bi-directional many-to-one association to UploadStatus
	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn( name = "usr_id_in", insertable = false, updatable = false, nullable = true, referencedColumnName = "usr_id_in" ),
			@JoinColumn( name = "col_seq_in", insertable = false, updatable = false, nullable = true, referencedColumnName = "col_seq_in" )
	} )
	private Collaborator collaborator;

	@Column( name = "fup_records_in", nullable = true, columnDefinition = "integer" )
	private Integer records;

	@Column( name = "fup_rejected_in", nullable = true, columnDefinition = "integer" )
	private Integer rejecteds;

	@Column( name = "col_seq_in", nullable = true, columnDefinition = "integer" )
	private Integer collaborator_id;

	@Column( name = "fup_error_ch", nullable = true, columnDefinition = "varchar", length = 512 )
	private String error;

	public FileUpload( )
	{
	}

	@Override
	public FileUploadPK getId( )
	{
		if ( this.id == null ) {
			this.id = new FileUploadPK( );
		}
		return this.id;
	}

	public void setId( FileUploadPK id )
	{
		this.id = id;
	}

	public UploadStatus getStatus( )
	{
		return this.status;
	}

	public void setStatus( UploadStatus uploadStatus )
	{
		this.status = uploadStatus;
	}

	public Media getMedia( )
	{
		return this.media;
	}

	public void setMedia( Media media )
	{
		this.media = media;
		this.getId( ).setMedia( media == null ? 0 : media.getId( ) );
	}

	public Collaborator getCollaborator( )
	{
		return this.collaborator;
	}

	public void setCollaborator( Collaborator collaborator )
	{
		this.collaborator = collaborator;
		if ( collaborator != null ) {
			this.getId( ).set( collaborator.getId( ) );
			this.collaborator_id = collaborator.getId( ).getSequence( );
		}
		else {
			this.getId( ).set( null );
		}
	}

	@Override
	public int compareTo( FileUpload other )
	{
		if ( this == other ) {
			return 0;
		}
		if ( other == null ) {
			return -1;
		}
		return this.getId( ).compareTo( other.getId( ) );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		default:
			return null;
		}
	}

	@Override
	public int compareTo( FileUpload object, Integer field )
	{
		switch ( field ) {
		default:
			return -1;
		}
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof FileUploadPK ) ) {
			return false;
		}
		FileUpload castOther = (FileUpload) other;
		return this.getId( ).equals( castOther );
	}

	@Transient
	public Integer getMediaId( )
	{
		if ( this.getMedia( ) != null ) {
			return this.getMedia( ).getId( );
		}
		else {
			return 0;
		}
	}

	@Transient
	public String getName( )
	{
		if ( this.getMedia( ) != null ) {
			return this.getMedia( ).getName( );
		}
		else {
			return null;
		}

	}

	@Transient
	public String getDate( )
	{
		if ( this.getMedia( ) != null ) {
			return SysUtils.formatDate( this.getMedia( ).getInsertDate( ), "dd/MM/yyyy" );
		}
		else {
			return null;
		}

	}

	@Transient
	public String getTime( )
	{
		if ( this.getMedia( ) != null ) {
			return SysUtils.formatDate( this.getMedia( ).getInsertDate( ), "HH:mm:ss" );
		}
		else {
			return null;
		}
	}

	@Transient
	public String getResponsable( )
	{
		if ( this.getCollaborator( ) != null ) {
			return this.getCollaborator( ).getPerson( ).getName( );
		}
		else {
			return null;
		}

	}

	@Transient
	public String getStatusDescription( )
	{
		return this.getStatus( ).getDescription( );
	}

	public Integer getRecords( )
	{
		return this.records;
	}

	public void setRecords( Integer records )
	{
		this.records = records;
	}

	public Integer getRejecteds( )
	{
		return this.rejecteds;
	}

	public void setRejecteds( Integer rejecteds )
	{
		this.rejecteds = rejecteds;
	}

	public Media getDuplicated( )
	{
		return this.duplicated;
	}

	public void setDuplicated( Media duplicated )
	{
		this.duplicated = duplicated;
	}

	public Integer getCollaborator_id( )
	{
		return this.collaborator_id;
	}

	public void setCollaborator_id( Integer collaborator_id )
	{
		this.collaborator_id = collaborator_id;
	}

	public String getError( )
	{
		return this.error;
	}

	public void setError( String error )
	{
		this.error = error;
		this.setStatus( new UploadStatus( 3 ) );
	}

}