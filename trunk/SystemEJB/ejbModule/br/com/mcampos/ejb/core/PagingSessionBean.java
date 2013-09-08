package br.com.mcampos.ejb.core;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import br.com.mcampos.utils.dto.PrincipalDTO;

public abstract class PagingSessionBean<T> extends ReadOnlySessionBean<T> implements PagingSessionInterface<T>
{

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public int count( @NotNull PrincipalDTO auth )
	{
		Long result;

		try {
			Query query = getCountQuery( auth, null );
			result = (Long) query.getSingleResult( );
			return result.intValue( );
		}
		catch ( Exception e ) {
			storeException( e );
			throw e;
		}

	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public int count( @NotNull PrincipalDTO auth, String filter )
	{
		Long result;

		try {
			Query query = getCountQuery( auth, filter );
			setParameters( query );
			result = (Long) query.getSingleResult( );
			return result.intValue( );
		}
		catch ( Exception e ) {
			storeException( e );
			throw e;
		}

	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public int count( @NotNull PrincipalDTO auth, String filter, Object... params )
	{
		Long result;

		try {
			Query query = getCountQuery( auth, filter );
			setQueryParams( query, params );
			result = (Long) query.getSingleResult( );
			return result.intValue( );
		}
		catch ( Exception e ) {
			storeException( e );
			throw e;
		}

	}

	protected String getCountQL( @NotNull PrincipalDTO auth, String whereClause )
	{
		String sqlQuery;

		sqlQuery = "select count(t) as registros from " + getPersistentClass( ).getSimpleName( ) + " as t ";
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

	protected Query getCountQuery( @NotNull PrincipalDTO auth, String whereClause )
	{
		try {
			Query query = getEntityManager( ).createQuery( getCountQL( auth, whereClause ) );
			return query;
		}
		catch ( Exception e ) {
			storeException( e );
			throw e;
		}
	}
}
