package br.com.mcampos.entity.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the product_type database table.
 * 
 */
@Entity
@Table( name = "product_type", schema = "public" )
public class ProductType extends SimpleTable<ProductType> implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "pdt_id_in" )
	private Integer id;

	@Column( name = "pdt_default_bt" )
	private Boolean defaultType;

	@Column( name = "pdt_description_ch" )
	private String description;

	public ProductType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer pdtIdIn )
	{
		id = pdtIdIn;
	}

	public Boolean getDefaultType( )
	{
		return defaultType;
	}

	public void setDefaultType( Boolean pdtDefaultBt )
	{
		defaultType = pdtDefaultBt;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String pdtDescriptionCh )
	{
		description = pdtDescriptionCh;
	}

}