package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.mcampos.jpa.system.RevisionStatus;

/**
 * The persistent class for the pgc_page database table.
 * 
 */
@Entity
@Table( name = "pgc_page", schema = "public" )
public class PgcPage implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcPagePK id;

	@ManyToOne( optional = false )
	@JoinColumn( name = "rst_id_in" )
	private RevisionStatus revisionStatus;

	// bi-directional many-to-one association to PgcField
	@OneToMany( mappedBy = "pgcPage" )
	private List<PgcField> pgcFields;

	// bi-directional many-to-one association to AnotoPage
	@ManyToOne
	@JoinColumns( {
			@JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch", nullable = false ),
			@JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false ),
			@JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in", nullable = false )
	} )
	private AnotoPage anotoPage;

	// bi-directional many-to-one association to Pgc
	@ManyToOne
	@JoinColumn( name = "pgc_id_in", nullable = false, insertable = false, updatable = false )
	private Pgc pgc;

	// bi-directional many-to-one association to PgcPageAttachment
	@OneToMany( mappedBy = "pgcPage" )
	private List<PgcPageAttachment> pgcPageAttachments;

	// bi-directional many-to-one association to PgcProcessedImage
	@OneToMany( mappedBy = "pgcPage" )
	private List<PgcProcessedImage> pgcProcessedImages;

	public PgcPage( )
	{
	}

	public PgcPagePK getId( )
	{
		if ( id == null ) {
			id = new PgcPagePK( );
		}
		return id;
	}

	public void setId( PgcPagePK id )
	{
		this.id = id;
	}

	public List<PgcField> getPgcFields( )
	{
		return pgcFields;
	}

	public void setPgcFields( List<PgcField> pgcFields )
	{
		this.pgcFields = pgcFields;
	}

	public AnotoPage getAnotoPage( )
	{
		return anotoPage;
	}

	public void setAnotoPage( AnotoPage anotoPage )
	{
		this.anotoPage = anotoPage;
	}

	public Pgc getPgc( )
	{
		return pgc;
	}

	public void setPgc( Pgc pgc )
	{
		this.pgc = pgc;
		getId( ).setPgcId( getPgc( ) != null ? getPgc( ).getId( ) : null );
	}

	public List<PgcPageAttachment> getPgcPageAttachments( )
	{
		return pgcPageAttachments;
	}

	public void setPgcPageAttachments( List<PgcPageAttachment> pgcPageAttachments )
	{
		this.pgcPageAttachments = pgcPageAttachments;
	}

	public List<PgcProcessedImage> getPgcProcessedImages( )
	{
		return pgcProcessedImages;
	}

	public void setPgcProcessedImages( List<PgcProcessedImage> pgcProcessedImages )
	{
		this.pgcProcessedImages = pgcProcessedImages;
	}

	public RevisionStatus getRevisionStatus( )
	{
		return revisionStatus;
	}

	public void setRevisionStatus( RevisionStatus revisionStatus )
	{
		this.revisionStatus = revisionStatus;
	}

}