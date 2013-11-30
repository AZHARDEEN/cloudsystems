package br.com.mcampos.jpa.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the rejected_records database table.
 * 
 */
@Entity
@Table( name = "rejected_records", schema = "public" )
public class RejectedRecord implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RejectedRecordPK id;

	@Column( name = "rrd_cause_ch" )
	private String cause;

	@Column( name = "rrd_value_ch" )
	private String value;

	@Column( name = "col_seq_in", insertable = true, updatable = true, nullable = true )
	private Integer sequence;

	// bi-directional many-to-one association to UploadStatus
	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn( name = "usr_id_in", insertable = false, updatable = false, nullable = true, referencedColumnName = "usr_id_in" ),
			@JoinColumn( name = "med_id_in", insertable = false, updatable = false, nullable = true, referencedColumnName = "med_id_in" )
	} )
	private FileUpload fileUpload;

	public RejectedRecord( )
	{
	}

	public RejectedRecordPK getId( )
	{
		if ( this.id == null ) {
			this.id = new RejectedRecordPK( );
		}
		return this.id;
	}

	public void setId( RejectedRecordPK id )
	{
		this.id = id;
	}

	public String getCause( )
	{
		return this.cause;
	}

	public void setCause( String rrdCauseCh )
	{
		this.cause = rrdCauseCh;
	}

	public String getValue( )
	{
		return this.value;
	}

	public void setValue( String rrdValueCh )
	{
		this.value = rrdValueCh;
	}

	public FileUpload getFileUpload( )
	{
		return this.fileUpload;
	}

	public void setFileUpload( FileUpload fileUpload )
	{
		this.fileUpload = fileUpload;
		this.getId( ).set( fileUpload );
	}
}