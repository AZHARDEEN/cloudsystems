package br.com.mcampos.ejb.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the price_type database table.
 * 
 */
@Entity
@Table( name = "price_type" )
public class PriceType extends SimpleTable<PriceType> implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "pct_id_in" )
	private Integer id;

	@Column( name = "pct_default_bt" )
	private Boolean defaultType;

	@Column( name = "pct_description_ch" )
	private String description;

	public PriceType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer pctIdIn )
	{
		id = pctIdIn;
	}

	public Boolean getDefaultType( )
	{
		return defaultType;
	}

	public void setDefaultType( Boolean pctDefaultBt )
	{
		defaultType = pctDefaultBt;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String pctDescriptionCh )
	{
		description = pctDescriptionCh;
	}

}