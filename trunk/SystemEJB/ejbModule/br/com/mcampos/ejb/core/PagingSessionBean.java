package br.com.mcampos.ejb.core;

import javax.persistence.Query;

public abstract class PagingSessionBean<T> extends ReadOnlySessionBean<T> implements PagingSessionInterface<T>
{

	@Override
	public int count( )
	{
		Long result;

		Query query = getCountQuery( null );
		result = (Long) query.getSingleResult( );
		return result.intValue( );
	}

	@Override
	public int count( String filter )
	{
		Long result;

		Query query = getCountQuery( filter );
		setParameters( query );
		result = (Long) query.getSingleResult( );
		return result.intValue( );
	}

	@Override
	public int count( String filter, Object... params )
	{
		Long result;

		Query query = getCountQuery( filter );
		setQueryParams( query, params );
		result = (Long) query.getSingleResult( );
		return result.intValue( );
	}

	protected String getCountQL( String whereClause )
	{
		String sqlQuery;

		sqlQuery = "select count(t) as registros from " + getPersistentClass( ).getSimpleName( ) + " as t ";
		if ( whereClause != null && whereClause.isEmpty( ) == false ) {
			whereClause = whereClause.trim( ).toLowerCase( );
			if ( whereClause.startsWith( "where" ) ) {
				sqlQuery += " " + whereClause;
			}
			else {
				sqlQuery += " where " + whereClause;
			}
		}
		return sqlQuery;
	}

	protected Query getCountQuery( String whereClause )
	{
		Query query = getEntityManager( ).createQuery( getCountQL( whereClause ) );
		return query;
	}
}
