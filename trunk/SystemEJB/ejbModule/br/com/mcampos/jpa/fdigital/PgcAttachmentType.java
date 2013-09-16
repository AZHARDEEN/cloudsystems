package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the pgc_attachment_type database table.
 * 
 */
@Entity
@Table( name = "pgc_attachment_type", schema = "public" )
public class PgcAttachmentType implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "pat_id_in", unique = true, nullable = false )
	private Integer patIdIn;

	@Column( name = "pat_description_ch", length = 64 )
	private String patDescriptionCh;

	// bi-directional many-to-one association to PgcPageAttachment
	@OneToMany( mappedBy = "pgcAttachmentType" )
	private List<PgcPageAttachment> pgcPageAttachments;

	public PgcAttachmentType( )
	{
	}

	public Integer getPatIdIn( )
	{
		return patIdIn;
	}

	public void setPatIdIn( Integer patIdIn )
	{
		this.patIdIn = patIdIn;
	}

	public String getPatDescriptionCh( )
	{
		return patDescriptionCh;
	}

	public void setPatDescriptionCh( String patDescriptionCh )
	{
		this.patDescriptionCh = patDescriptionCh;
	}

	public List<PgcPageAttachment> getPgcPageAttachments( )
	{
		return pgcPageAttachments;
	}

	public void setPgcPageAttachments( List<PgcPageAttachment> pgcPageAttachments )
	{
		this.pgcPageAttachments = pgcPageAttachments;
	}

}