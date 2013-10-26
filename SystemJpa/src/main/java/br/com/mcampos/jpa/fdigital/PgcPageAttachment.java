package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the pgc_page_attachment database table.
 * 
 */
@Entity
@Table( name = "pgc_page_attachment", schema = "public" )
public class PgcPageAttachment implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcPageAttachmentPK id;

	@Column( name = "med_id_in" )
	private Integer medIdIn;

	@Column( name = "pat_barcode_type_in" )
	private Integer patBarcodeTypeIn;

	@Column( name = "pat_value_ch", length = 128 )
	private String patValueCh;

	// bi-directional many-to-one association to PgcAttachmentType
	@ManyToOne
	@JoinColumn( name = "pat_id_in", nullable = false )
	private PgcAttachmentType pgcAttachmentType;

	// bi-directional many-to-one association to PgcPage
	@ManyToOne
	@JoinColumns( {
			@JoinColumn( name = "pgc_id_in", referencedColumnName = "pgc_id_in", nullable = false, insertable = false, updatable = false ),
			@JoinColumn( name = "ppg_book_id", referencedColumnName = "ppg_book_id", nullable = false, insertable = false, updatable = false ),
			@JoinColumn( name = "ppg_page_id", referencedColumnName = "ppg_page_id", nullable = false, insertable = false, updatable = false )
	} )
	private PgcPage pgcPage;

	public PgcPageAttachment( )
	{
	}

	public PgcPageAttachmentPK getId( )
	{
		return id;
	}

	public void setId( PgcPageAttachmentPK id )
	{
		this.id = id;
	}

	public Integer getMedIdIn( )
	{
		return medIdIn;
	}

	public void setMedIdIn( Integer medIdIn )
	{
		this.medIdIn = medIdIn;
	}

	public Integer getPatBarcodeTypeIn( )
	{
		return patBarcodeTypeIn;
	}

	public void setPatBarcodeTypeIn( Integer patBarcodeTypeIn )
	{
		this.patBarcodeTypeIn = patBarcodeTypeIn;
	}

	public String getPatValueCh( )
	{
		return patValueCh;
	}

	public void setPatValueCh( String patValueCh )
	{
		this.patValueCh = patValueCh;
	}

	public PgcAttachmentType getPgcAttachmentType( )
	{
		return pgcAttachmentType;
	}

	public void setPgcAttachmentType( PgcAttachmentType pgcAttachmentType )
	{
		this.pgcAttachmentType = pgcAttachmentType;
	}

	public PgcPage getPgcPage( )
	{
		return pgcPage;
	}

	public void setPgcPage( PgcPage pgcPage )
	{
		this.pgcPage = pgcPage;
	}

}