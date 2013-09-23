package br.com.mcampos.ejb.product;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCompanySessionBean;
import br.com.mcampos.jpa.product.Price;
import br.com.mcampos.jpa.product.Product;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class ProductSessionBean
 */
@Stateless( mappedName = "ProductSession", name = "ProductSession" )
public class ProductSessionBean extends BaseCompanySessionBean<Product> implements ProductSession, ProductSessionLocal
{

	@Override
	protected Class<Product> getEntityClass( )
	{
		return Product.class;
	}

	@Override
	public Product add( PrincipalDTO auth, Product newEntity )
	{
		if ( auth == null || newEntity == null ) {
			throw new InvalidParameterException( );
		}
		if ( newEntity.getId( ).getId( ) == null || newEntity.getId( ).getId( ).equals( 0 ) ) {
			Integer nextVal = getNextId( Product.getNextId, auth.getCompanyID( ) );
			newEntity.getId( ).setId( nextVal );
		}
		if ( newEntity.getFromDate( ) == null ) {
			newEntity.setFromDate( new Date( ) );
		}
		newEntity.getId( ).setCompanyId( auth.getCompanyID( ) );
		if ( SysUtils.isEmpty( newEntity.getPrices( ) ) == false ) {
			for ( Price price : newEntity.getPrices( ) ) {
				price.getId( ).setCompanyId( auth.getCompanyID( ) );
				price.getId( ).setProductId( newEntity.getProductId( ) );
			}
		}
		return super.add( auth, newEntity );
	}

	@Override
	public Product remove( PrincipalDTO auth, Serializable key )
	{
		Product toRemove = get( key );
		if ( toRemove == null )
			return null;
		toRemove.setToDate( new Date( ) );
		return toRemove;
	}

	@Override
	protected String getCountQL( @NotNull PrincipalDTO auth, String whereClause )
	{
		String sqlQuery;

		sqlQuery = "select count(t) as registros from " + getPersistentClass( ).getSimpleName( ) + " as t ";
		if ( SysUtils.isEmpty( whereClause ) ) {
			whereClause = " (t.id.companyId = " + auth.getCompanyID( ) + ") ";
		}
		else {
			whereClause = whereClause + " and ( t.id.companyId = " + auth.getCompanyID( ) + ") ";
		}
		whereClause += " AND ( t.toDate is null or t.toDate < CURRENT_TIMESTAMP ) ";
		if ( whereClause != null && whereClause.isEmpty( ) == false ) {
			String aux = whereClause.trim( ).toLowerCase( );
			if ( aux.startsWith( "where" ) ) {
				sqlQuery += " " + whereClause;
			}
			else {
				sqlQuery += " where " + whereClause;
			}
		}
		return sqlQuery;
	}

	@Override
	protected Query getAllQuery( PrincipalDTO auth, String whereClause )
	{
		if ( SysUtils.isEmpty( whereClause ) ) {
			whereClause = " ( t.toDate is null or t.toDate < CURRENT_TIMESTAMP )";
		}
		else {
			whereClause = whereClause + " AND ( t.toDate is null or t.toDate < CURRENT_TIMESTAMP )";
		}
		return super.getAllQuery( auth, whereClause );
	}

	@Override
	public Product loadObjects( PrincipalDTO auth, Product product )
	{
		product = getEntityManager( ).merge( product );
		product.getPrices( ).size( );
		product.getKeys( ).size( );
		return product;
	}
}
