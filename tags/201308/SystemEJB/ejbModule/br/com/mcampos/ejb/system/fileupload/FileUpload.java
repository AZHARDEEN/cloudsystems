package br.com.mcampos.ejb.system.fileupload;

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

import br.com.mcampos.ejb.core.BasicEntityRenderer;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.sysutils.SysUtils;

/**
 * The persistent class for the file_upload database table.
 * 
 */
@Entity
@Table( name = "file_upload" )
@NamedQueries( {
		@NamedQuery( name = FileUpload.getAllByCompany, query = "from FileUpload o where o.collaborator.company = ?1" ),
} )
public class FileUpload implements BasicEntityRenderer<FileUpload>, Comparable<FileUpload>
{
	private static final long serialVersionUID = 1L;
	public static final String getAllByCompany = "FileUpload.getAllByCompany";

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

	// bi-directional many-to-one association to UploadStatus
	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn( name = "usr_id_in", insertable = false, updatable = false, nullable = false ),
			@JoinColumn( name = "col_seq_in", insertable = false, updatable = false, nullable = false )
	} )
	private Collaborator collaborator;

	@Column( name = "fup_records_in", nullable = true, columnDefinition = "integer" )
	private Integer records;

	@Column( name = "fup_rejected_in", nullable = true, columnDefinition = "integer" )
	private Integer rejecteds;

	public FileUpload( )
	{
	}

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
		getId( ).setMedia( media == null ? 0 : media.getId( ) );
	}

	public Collaborator getCollaborator( )
	{
		return this.collaborator;
	}

	public void setCollaborator( Collaborator collaborator )
	{
		this.collaborator = collaborator;
		if ( collaborator != null ) {
			getId( ).set( collaborator.getId( ) );
		}
		else {
			getId( ).set( null );
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
		return getId( ).compareTo( other.getId( ) );
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
		return getId( ).equals( castOther );
	}

	@Transient
	public Integer getMediaId( )
	{
		if ( getMedia( ) != null ) {
			return getMedia( ).getId( );
		}
		else {
			return 0;
		}
	}

	@Transient
	public String getName( )
	{
		if ( getMedia( ) != null ) {
			return getMedia( ).getName( );
		}
		else {
			return null;
		}

	}

	@Transient
	public String getDate( )
	{
		if ( getMedia( ) != null ) {
			return SysUtils.formatDate( getMedia( ).getInsertDate( ), "dd/MM/yyyy" );
		}
		else {
			return null;
		}

	}

	@Transient
	public String getTime( )
	{
		if ( getMedia( ) != null ) {
			return SysUtils.formatDate( getMedia( ).getInsertDate( ), "HH:mm:ss" );
		}
		else {
			return null;
		}
	}

	@Transient
	public String getResponsable( )
	{
		if ( getCollaborator( ) != null ) {
			return getCollaborator( ).getPerson( ).getName( );
		}
		else {
			return null;
		}

	}

	@Transient
	public String getStatusDescription( )
	{
		return getStatus( ).getDescription( );
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

}