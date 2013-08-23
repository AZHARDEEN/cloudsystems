package br.com.mcampos.ejb.product.entity;

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
@Table( name = "product_type" )
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

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer pdtIdIn )
	{
		this.id = pdtIdIn;
	}

	public Boolean getDefaultType( )
	{
		return defaultType;
	}

	public void setDefaultType( Boolean pdtDefaultBt )
	{
		this.defaultType = pdtDefaultBt;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String pdtDescriptionCh )
	{
		this.description = pdtDescriptionCh;
	}

}