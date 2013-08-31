package br.com.mcampos.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the revision_status database table.
 * 
 */
@Entity
@Table( name = "revision_status" )
public class RevisionStatus extends SimpleTable<RevisionStatus>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "rst_id_in" )
	private Integer id;

	@Column( name = "rst_description_ch" )
	private String description;

	public RevisionStatus( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer rstIdIn )
	{
		this.id = rstIdIn;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String rstDescriptionCh )
	{
		this.description = rstDescriptionCh;
	}

}