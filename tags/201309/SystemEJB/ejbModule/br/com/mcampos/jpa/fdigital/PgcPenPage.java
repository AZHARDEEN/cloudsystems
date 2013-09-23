package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the pgc_pen_page database table.
 * 
 */
@Entity
@Table( name = "pgc_pen_page", schema = "public" )
public class PgcPenPage implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcPenPagePK id;

	public PgcPenPage( )
	{
	}

	public PgcPenPagePK getId( )
	{
		return id;
	}

	public void setId( PgcPenPagePK id )
	{
		this.id = id;
	}

}