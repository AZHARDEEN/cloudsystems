package br.com.mcampos.jpa.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.jpa.BaseCompanyEntity;
import br.com.mcampos.jpa.BasicEntityRenderer;
import br.com.mcampos.sysutils.SysUtils;

/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@NamedQueries( {
		@NamedQuery(
				name = Product.getAll,
				query = "SELECT p FROM Product p where p.id.companyId = ?1 and ( p.toDate is null or p.toDate < CURRENT_TIMESTAMP)" ),
		@NamedQuery(
				name = Product.getCount,
				query = "SELECT count(p) as registros FROM Product p where p.id.companyId = ?1 and ( p.toDate is null or p.toDate < CURRENT_TIMESTAMP)" ),
		@NamedQuery( name = Product.getNextId, query = "select coalesce ( max(o.id.id), 0 ) + 1 from Product o where o.id.companyId = ?1" )
} )
@Table( name = "product", schema = "public" )
public class Product extends BaseCompanyEntity implements Serializable, Comparable<Product>, BasicEntityRenderer<Product>
{
	private static final long serialVersionUID = 1L;

	public static final String getAll = "Product.getAll";
	public static final String getCount = "Product.getCount";
	public static final String getNextId = "Product.getNextId";

	@EmbeddedId
	private ProductPK id;

	@ManyToOne( optional = false, fetch = FetchType.EAGER )
	@JoinColumn( name = "pdt_id_in", referencedColumnName = "pdt_id_in", nullable = false, insertable = true, updatable = true )
	private ProductType type;

	@Column( name = "prd_description_tx", nullable = true )
	private String description;

	@Column( name = "prd_from_dt", nullable = false )
	@Temporal( TemporalType.DATE )
	private Date fromDate;

	@Column( name = "prd_name_ch", nullable = false )
	private String name;

	@Column( name = "prd_to_dt", nullable = true )
	@Temporal( TemporalType.DATE )
	private Date toDate;

	@Column( name = "prd_visible_bt" )
	private Boolean visible;

	@OneToMany( mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Price> prices;

	@OneToMany( mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<ProductKeyword> keys;

	public Product( )
	{
	}

	@Override
	public ProductPK getId( )
	{
		if ( id == null )
			id = new ProductPK( );
		return id;
	}

	public void setId( ProductPK id )
	{
		this.id = id;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String prdDescriptionTx )
	{
		description = prdDescriptionTx;
	}

	public Date getFromDate( )
	{
		return fromDate;
	}

	public void setFromDate( Date prdFromDt )
	{
		fromDate = prdFromDt;
	}

	public String getName( )
	{
		return name;
	}

	public void setName( String prdNameCh )
	{
		name = prdNameCh;
	}

	public Date getToDate( )
	{
		return toDate;
	}

	public void setToDate( Date prdToDt )
	{
		toDate = prdToDt;
	}

	public Boolean isVisible( )
	{
		return visible;
	}

	public void setVisible( Boolean prdVisibleBt )
	{
		visible = prdVisibleBt;
	}

	public ProductType getType( )
	{
		return type;
	}

	public void setType( ProductType type )
	{
		this.type = type;
	}

	@Override
	public int compareTo( Product o )
	{
		if ( o == null )
			return -1;
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj )
			return true;
		if ( obj instanceof Product )
			return getId( ).equals( ( (Product) obj ).getId( ) );
		else if ( obj instanceof ProductPK ) {
			return getId( ).equals( obj );
		}
		else
			return false;
	}

	public Integer getProductId( )
	{
		return getId( ).getId( );
	}

	public String getTypeDescription( )
	{
		return getType( ).getDescription( );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 1:
			return "id";
		case 2:
			return "Name";
		case 3:
			return "typeDescription";
		}
		return null;
	}

	@Override
	public int compareTo( Product object, Integer field )
	{
		switch ( field ) {
		case 1:
			return getProductId( ).compareTo( object.getProductId( ) );
		case 2:
			return getName( ).compareTo( object.getName( ) );
		case 3:
			return getTypeDescription( ).compareTo( object.getTypeDescription( ) );
		}
		return -1;
	}

	public List<Price> getPrices( )
	{
		if ( prices == null )
			prices = new ArrayList<Price>( );
		return prices;
	}

	public void setPrices( List<Price> prices )
	{
		this.prices = prices;
	}

	public Price add( Price item )
	{
		if ( item != null ) {
			int nIndex = getPrices( ).indexOf( item );
			if ( nIndex < 0 ) {
				getPrices( ).add( item );
				if ( item.getId( ).getFromDate( ) == null ) {
					item.getId( ).setFromDate( new Date( ) );
				}
				item.setProduct( this );
			}
		}
		return item;
	}

	public Price remove( Price item )
	{
		SysUtils.remove( getPrices( ), item );
		if ( item != null ) {
			item.setProduct( null );
		}
		return item;
	}

	public List<ProductKeyword> getKeys( )
	{
		if ( keys == null )
			keys = new ArrayList<ProductKeyword>( );
		return keys;
	}

	public void setKeys( List<ProductKeyword> keys )
	{
		this.keys = keys;
	}

	public ProductKeyword add( ProductKeyword item )
	{
		if ( item != null ) {
			int nIndex = getPrices( ).indexOf( item );
			if ( nIndex < 0 ) {
				getKeys( ).add( item );
				item.setProduct( this );
			}
		}
		return item;
	}

	public ProductKeyword remove( ProductKeyword item )
	{
		SysUtils.remove( getKeys( ), item );
		if ( item != null ) {
			item.setProduct( null );
		}
		return item;
	}

}