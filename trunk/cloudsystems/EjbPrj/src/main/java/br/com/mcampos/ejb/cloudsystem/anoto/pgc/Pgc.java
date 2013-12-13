package br.com.mcampos.ejb.cloudsystem.anoto.pgc;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcstatus.PgcStatus;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity.RevisionStatus;

@Entity
@NamedQueries( { @NamedQuery( name = Pgc.findAllQueryName, query = "select o from Pgc o order by o.insertDate desc" ),
		@NamedQuery( name = Pgc.findSuspended,
				query = "select o from Pgc o where o.pgcStatus.id <> 1 order by o.insertDate desc" ) } )
@Table( name = "pgc" )
public class Pgc implements Serializable, EntityCopyInterface<PGCDTO>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2902695103319766847L;
	public static final String findAllQueryName = "Pgc.findAll";
	public static final String findSuspended = "Pgc.findSuspended";

	@Id
	@Column( name = "pgc_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "pgc_insert_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date insertDate;

	@ManyToOne( optional = false )
	@JoinColumn( name = "pgc_id_in", referencedColumnName = "med_id_in", insertable = false, updatable = false, nullable = false )
	Media media;

	@ManyToOne
	@JoinColumn( name = "pgs_id_in" )
	private PgcStatus pgcStatus;

	@ManyToOne( optional = false )
	@JoinColumn( name = "rst_id_in" )
	private RevisionStatus revisionStatus;

	@Column( name = "pgc_description_ch", nullable = false )
	private String description;

	@Column( name = "pgc_pen_id" )
	private String penId;

	@Column( name = "pgc_time_diff_in" )
	private Long timediff;

	@OneToMany( mappedBy = "pgc" )
	private List<PgcPage> pages;

	@OneToMany( mappedBy = "pgc" )
	private List<PgcPenPage> pgcPenPages;

	public Pgc( )
	{
	}

	public Pgc( Integer pk, Timestamp insertDate )
	{
		this.id = pk;
		this.insertDate = insertDate;
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer pk )
	{
		this.id = pk;
	}

	public Date getInsertDate( )
	{
		return this.insertDate;
	}

	public void setInsertDate( Date indate )
	{
		this.insertDate = indate;
	}

	@Override
	public PGCDTO toDTO( )
	{
		PGCDTO dto = new PGCDTO( this.getMedia( ).toDTO( ) );
		dto.setPgcStatus( this.getPgcStatus( ).toDTO( ) );
		dto.setInsertDate( this.getInsertDate( ) );
		return dto;
	}

	public void setMedia( Media media )
	{
		this.media = media;
		if ( media != null ) {
			this.setId( media.getId( ) );
		}
	}

	public Media getMedia( )
	{
		return this.media;
	}

	public void setPgcStatus( PgcStatus pgcStatus )
	{
		this.pgcStatus = pgcStatus;
	}

	public PgcStatus getPgcStatus( )
	{
		return this.pgcStatus;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setPenId( String penId )
	{
		this.penId = penId;
	}

	public String getPenId( )
	{
		return this.penId;
	}

	public void setTimediff( Long timediff )
	{
		this.timediff = timediff;
	}

	public Long getTimediff( )
	{
		return this.timediff;
	}

	public void setPages( List<PgcPage> books )
	{
		this.pages = books;
	}

	public List<PgcPage> getPages( )
	{
		return this.pages;
	}

	public void setPgcPenPages( List<PgcPenPage> pgcPenPages )
	{
		this.pgcPenPages = pgcPenPages;
	}

	public List<PgcPenPage> getPgcPenPages( )
	{
		return this.pgcPenPages;
	}

	public void setRevisionStatus( RevisionStatus revisionStatus )
	{
		this.revisionStatus = revisionStatus;
	}

	public RevisionStatus getRevisionStatus( )
	{
		return this.revisionStatus;
	}

	@Override
	public String toString( )
	{
		return "ID: PgcId: " + this.getId( ) + " Pen: " + this.getPenId( ) + " InsertDate: " + this.getInsertDate( ).toString( );
	}
}
