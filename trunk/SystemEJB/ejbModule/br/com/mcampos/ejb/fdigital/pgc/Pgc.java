package br.com.mcampos.ejb.fdigital.pgc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.ejb.fdigital.AnotoPenPage;
import br.com.mcampos.ejb.fdigital.PgcAttachment;
import br.com.mcampos.ejb.fdigital.PgcPage;
import br.com.mcampos.ejb.fdigital.PgcProperty;
import br.com.mcampos.ejb.fdigital.pgcstatus.PgcStatus;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.system.revisionstatus.RevisionStatus;

/**
 * The persistent class for the pgc database table.
 * 
 */
@Entity
@Table( name = "pgc" )
public class Pgc implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "pgc_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "pgc_description_ch", length = 64 )
	private String description;

	@Column( name = "pgc_insert_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date insertDate;

	@Column( name = "pgc_pen_id", length = 16 )
	private String penId;

	@Column( name = "pgc_time_diff_in", precision = 16 )
	private BigDecimal timeDiff;

	@ManyToOne( optional = false )
	@JoinColumn( name = "rst_id_in" )
	private RevisionStatus revisionStatus;

	// bi-directional many-to-one association to PgcStatus
	@ManyToOne( optional = false )
	@JoinColumn( name = "pgs_id_in", nullable = false, insertable = true, updatable = true )
	private PgcStatus pgcStatus;

	// bi-directional many-to-one association to PgcAttachment
	@OneToMany( mappedBy = "pgc" )
	private List<PgcAttachment> pgcAttachments;

	// bi-directional many-to-one association to PgcPage
	@OneToMany( mappedBy = "pgc" )
	private List<PgcPage> pgcPages;

	// bi-directional many-to-many association to AnotoPenPage
	@ManyToMany
	@JoinTable(
			name = "pgc_pen_page"
			, joinColumns = {
					@JoinColumn( name = "pgc_id_in", nullable = false )
			}
			, inverseJoinColumns = {
					@JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch", nullable = false ),
					@JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false ),
					@JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in", nullable = false ),
					@JoinColumn( name = "pdp_seq_in", referencedColumnName = "pdp_seq_in", nullable = false )
			} )
	private List<AnotoPenPage> anotoPenPages;

	// bi-directional many-to-one association to PgcProperty
	@OneToMany( mappedBy = "pgc" )
	private List<PgcProperty> pgcProperties;

	@ManyToOne( optional = false )
	@JoinColumn( name = "pgc_id_in", referencedColumnName = "med_id_in", insertable = false, updatable = false, nullable = false )
	Media media;

	public Pgc( )
	{
	}

	public Pgc( Media media )
	{
		setMedia( media );
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer pgcIdIn )
	{
		this.id = pgcIdIn;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String pgcDescriptionCh )
	{
		this.description = pgcDescriptionCh;
	}

	public Date getInsertDate( )
	{
		return this.insertDate;
	}

	public void setInsertDate( Date pgcInsertDt )
	{
		this.insertDate = pgcInsertDt;
	}

	public String getPenId( )
	{
		return this.penId;
	}

	public void setPenId( String pgcPenId )
	{
		this.penId = pgcPenId;
	}

	public BigDecimal getTimeDiff( )
	{
		return this.timeDiff;
	}

	public void setTimeDiff( BigDecimal pgcTimeDiffIn )
	{
		this.timeDiff = pgcTimeDiffIn;
	}

	public PgcStatus getPgcStatus( )
	{
		return this.pgcStatus;
	}

	public void setPgcStatus( PgcStatus pgcStatus )
	{
		this.pgcStatus = pgcStatus;
	}

	public List<PgcAttachment> getPgcAttachments( )
	{
		return this.pgcAttachments;
	}

	public void setPgcAttachments( List<PgcAttachment> pgcAttachments )
	{
		this.pgcAttachments = pgcAttachments;
	}

	public List<PgcPage> getPgcPages( )
	{
		return this.pgcPages;
	}

	public void setPgcPages( List<PgcPage> pgcPages )
	{
		this.pgcPages = pgcPages;
	}

	public List<AnotoPenPage> getAnotoPenPages( )
	{
		return this.anotoPenPages;
	}

	public void setAnotoPenPages( List<AnotoPenPage> anotoPenPages )
	{
		this.anotoPenPages = anotoPenPages;
	}

	public List<PgcProperty> getPgcProperties( )
	{
		return this.pgcProperties;
	}

	public void setPgcProperties( List<PgcProperty> pgcProperties )
	{
		this.pgcProperties = pgcProperties;
	}

	public Media getMedia( )
	{
		return this.media;
	}

	public void setMedia( Media media )
	{
		this.media = media;
		if ( getMedia( ) != null ) {
			setId( getMedia( ).getId( ) );
			setDescription( media.getName( ) );
		}
		else {
			setId( null );
			setDescription( null );
		}
	}

	public RevisionStatus getRevisionStatus( )
	{
		return this.revisionStatus;
	}

	public void setRevisionStatus( RevisionStatus revisionStatus )
	{
		this.revisionStatus = revisionStatus;
	}
}