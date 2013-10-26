package br.com.mcampos.jpa.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

/**
 * The persistent class for the price_type database table.
 * 
 */
@Entity
@Table( name = "price_type", schema = "public" )
public class PriceType extends SimpleTable<PriceType> implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final int typeNormal = 1;
	public static final int typeMin = 2;
	public static final int typeMax = 3;
	public static final int typeBuy = 4;

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