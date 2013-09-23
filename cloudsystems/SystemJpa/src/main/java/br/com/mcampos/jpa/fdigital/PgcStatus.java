package br.com.mcampos.jpa.fdigital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

/**
 * The persistent class for the pgc_status database table.
 * 
 */
@Entity
@Table( name = "pgc_status", schema = "public" )
public class PgcStatus extends SimpleTable<PgcStatus>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "pgs_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "pgs_description_ch", nullable = false, length = 64 )
	private String description;

	public PgcStatus( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer pgsIdIn )
	{
		id = pgsIdIn;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String pgsDescriptionCh )
	{
		description = pgsDescriptionCh;
	}
}