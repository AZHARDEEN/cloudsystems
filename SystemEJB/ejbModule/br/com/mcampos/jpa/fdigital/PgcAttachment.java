package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the pgc_attachment database table.
 * 
 */
@Entity
@Table( name = "pgc_attachment", schema = "public" )
public class PgcAttachment implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcAttachmentPK id;

	// bi-directional many-to-one association to Pgc
	@ManyToOne
	@JoinColumn( name = "pgc_id_in", nullable = false, insertable = false, updatable = false )
	private Pgc pgc;

	public PgcAttachment( )
	{
	}

	public PgcAttachmentPK getId( )
	{
		return id;
	}

	public void setId( PgcAttachmentPK id )
	{
		this.id = id;
	}

	public Pgc getPgc( )
	{
		return pgc;
	}

	public void setPgc( Pgc pgc )
	{
		this.pgc = pgc;
	}

}