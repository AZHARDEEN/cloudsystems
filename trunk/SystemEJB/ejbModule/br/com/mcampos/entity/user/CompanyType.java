package br.com.mcampos.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the company_type database table.
 * 
 */
@Entity
@Table( name = "company_type", schema = "public" )
public class CompanyType extends SimpleTable<CompanyType>
{
	private static final long serialVersionUID = 1L;

	public static Integer defaultType = 224;

	@Id
	@Column( name = "ctp_id_in" )
	private Integer id;

	@Column( name = "ctp_description_ch" )
	private String description;

	public CompanyType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer ctpIdIn )
	{
		id = ctpIdIn;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String ctpDescriptionCh )
	{
		description = ctpDescriptionCh;
	}
}