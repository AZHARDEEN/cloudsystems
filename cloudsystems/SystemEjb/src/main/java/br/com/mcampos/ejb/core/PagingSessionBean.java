package br.com.mcampos.ejb.core;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import br.com.mcampos.dto.core.PrincipalDTO;

public abstract class PagingSessionBean<T> extends ReadOnlySessionBean<T> implements PagingSessionInterface<T>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1176033381769950845L;

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public int count( PrincipalDTO auth )
	{
		Long result;

		try {
			Query query = this.getCountQuery( auth, null );
			result = (Long) query.getSingleResult( );
			return result.intValue( );
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}

	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public int count( PrincipalDTO auth, String filter )
	{
		Long result;

		try {
			Query query = this.getCountQuery( auth, filter );
			this.setParameters( query );
			result = (Long) query.getSingleResult( );
			return result.intValue( );
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}

	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public int count( PrincipalDTO auth, String filter, Object... params )
	{
		Long result;

		try {
			Query query = this.getCountQuery( auth, filter );
			this.setQueryParams( query, params );
			result = (Long) query.getSingleResult( );
			return result.intValue( );
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}

	}

	protected String getCountQL( PrincipalDTO auth, String whereClause )
	{
		String sqlQuery;

		sqlQuery = "select count(t) as registros from " + this.getPersistentClass( ).getSimpleName( ) + " as t ";
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

	protected Query getCountQuery( PrincipalDTO auth, String whereClause )
	{
		try {
			Query query = this.getEntityManager( ).createQuery( this.getCountQL( auth, whereClause ) );
			return query;
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}
	}
}
