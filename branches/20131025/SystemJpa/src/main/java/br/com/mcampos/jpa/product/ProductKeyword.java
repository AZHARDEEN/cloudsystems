package br.com.mcampos.jpa.product;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the product_keyword database table.
 * 
 */
@Entity
@Table( name = "product_keyword", schema = "public" )
@NamedQuery( name = "ProductKeyword.findAll", query = "SELECT p FROM ProductKeyword p" )
public class ProductKeyword extends BaseProduct implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductKeywordPK id;

	public ProductKeyword( )
	{
	}

	@Override
	public ProductKeywordPK getId( )
	{
		return this.id;
	}

	public void setId( ProductKeywordPK id )
	{
		this.id = id;
	}

}