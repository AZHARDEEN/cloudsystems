package br.com.mcampos.entity.fdigital;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the pgc_status database table.
 * 
 */
@Entity
@Table( name = "pgc_status" )
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
		return this.id;
	}

	@Override
	public void setId( Integer pgsIdIn )
	{
		this.id = pgsIdIn;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String pgsDescriptionCh )
	{
		this.description = pgsDescriptionCh;
	}
}