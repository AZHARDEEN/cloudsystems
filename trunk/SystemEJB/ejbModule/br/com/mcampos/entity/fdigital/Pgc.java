package br.com.mcampos.entity.fdigital;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import br.com.mcampos.ejb.core.BasicEntityRenderer;
import br.com.mcampos.entity.system.Media;
import br.com.mcampos.entity.system.RevisionStatus;
import br.com.mcampos.sysutils.SysUtils;

/**
 * The persistent class for the pgc database table.
 * 
 */
@Entity
@Table( name = "pgc", schema = "public" )
public class Pgc implements BasicEntityRenderer<Pgc>, Comparable<Pgc>
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
	private PgcStatus status;

	// bi-directional many-to-one association to PgcAttachment
	@OneToMany( mappedBy = "pgc" )
	private List<PgcAttachment> pgcAttachments;

	// bi-directional many-to-one association to PgcPage
	@OneToMany( mappedBy = "pgc" )
	private List<PgcPage> pgcPages;

	// bi-directional many-to-many association to AnotoPenPage
	@ManyToMany
	@JoinTable(
			name = "pgc_pen_page", schema = "public"
			, joinColumns = {
					@JoinColumn( name = "pgc_id_in", nullable = false )
			}
			, inverseJoinColumns = {
					@JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch", nullable = false ),
					@JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false ),
					@JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in", nullable = false ),
					@JoinColumn( name = "pdp_seq_in", referencedColumnName = "pdp_seq_in", nullable = false )
			} )
	private List<AnotoPenPage> penPages;

	// bi-directional many-to-one association to PgcProperty
	@OneToMany( mappedBy = "pgc" )
	private List<PgcProperty> properties;

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
		return id;
	}

	public void setId( Integer pgcIdIn )
	{
		id = pgcIdIn;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String pgcDescriptionCh )
	{
		description = pgcDescriptionCh;
	}

	public Date getInsertDate( )
	{
		return insertDate;
	}

	public void setInsertDate( Date pgcInsertDt )
	{
		insertDate = pgcInsertDt;
	}

	public String getPenId( )
	{
		return penId;
	}

	public void setPenId( String pgcPenId )
	{
		penId = pgcPenId;
	}

	public BigDecimal getTimeDiff( )
	{
		return timeDiff;
	}

	public void setTimeDiff( BigDecimal pgcTimeDiffIn )
	{
		timeDiff = pgcTimeDiffIn;
	}

	public PgcStatus getStatus( )
	{
		if ( status == null ) {
			status = new PgcStatus( );
		}
		return status;
	}

	public void setStatus( PgcStatus pgcStatus )
	{
		status = pgcStatus;
	}

	public List<PgcAttachment> getPgcAttachments( )
	{
		return pgcAttachments;
	}

	public void setPgcAttachments( List<PgcAttachment> pgcAttachments )
	{
		this.pgcAttachments = pgcAttachments;
	}

	public List<PgcPage> getPgcPages( )
	{
		return pgcPages;
	}

	public void setPgcPages( List<PgcPage> pgcPages )
	{
		this.pgcPages = pgcPages;
	}

	public List<AnotoPenPage> getPenPages( )
	{
		return penPages;
	}

	public void setPenPages( List<AnotoPenPage> anotoPenPages )
	{
		penPages = anotoPenPages;
	}

	public List<PgcProperty> getProperties( )
	{
		if ( properties == null ) {
			properties = new ArrayList<PgcProperty>( );
		}
		return properties;
	}

	public void setProperties( List<PgcProperty> pgcProperties )
	{
		properties = pgcProperties;
	}

	public Media getMedia( )
	{
		return media;
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
		return revisionStatus;
	}

	public void setRevisionStatus( RevisionStatus revisionStatus )
	{
		this.revisionStatus = revisionStatus;
	}

	public PgcProperty add( PgcProperty item )
	{
		if ( item != null ) {
			int nIndex = getProperties( ).indexOf( item );
			if ( nIndex < 0 ) {
				getProperties( ).add( item );
				item.setPgc( this );
			}
		}
		return item;
	}

	public PgcProperty remove( PgcProperty item )
	{
		SysUtils.remove( getProperties( ), item );
		if ( item != null ) {
			item.setPgc( null );
		}
		return item;
	}

	public void setStatus( Integer status )
	{
		getStatus( ).setId( status );
	}

	@Override
	public int compareTo( Pgc o )
	{
		if ( getId( ) == null ) {
			return -1;
		}
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).toString( );
		case 1:
			return getDescription( ).toString( );
		}
		return null;
	}

	@Override
	public int compareTo( Pgc object, Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).compareTo( object.getId( ) );
		case 1:
			return getDescription( ).compareTo( object.getDescription( ) );
		}
		return 0;
	}

}