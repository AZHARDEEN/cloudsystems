package br.com.mcampos.jpa.fdigital;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the pgc_property database table.
 * 
 */
@Entity
@Table( name = "pgc_property", schema = "public" )
public class PgcProperty implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcPropertyPK id;

	@Column( name = "ppg_value_ch", nullable = false, length = 128 )
	private String value;

	// bi-directional many-to-one association to Pgc
	@ManyToOne
	@JoinColumn( name = "pgc_id_in", nullable = false, insertable = false, updatable = false )
	private Pgc pgc;

	public PgcProperty( )
	{
	}

	public PgcPropertyPK getId( )
	{
		if ( id == null ) {
			id = new PgcPropertyPK( );
		}
		return id;
	}

	public void setId( PgcPropertyPK id )
	{
		this.id = id;
	}

	public String getValue( )
	{
		return value;
	}

	public void setValue( String ppgValueCh )
	{
		value = ppgValueCh;
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
}