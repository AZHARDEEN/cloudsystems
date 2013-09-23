package br.com.mcampos.ejb.core;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.search.Searchable;
import br.com.mcampos.ejb.core.search.Searchables;
import br.com.mcampos.sysutils.SysUtils;

/*
 * This is the base interface for all session beans
 */
public abstract class ReadOnlySessionBean<T> extends BaseSessionBean implements ReadOnlySessionInterface<T>
{
	private static final long serialVersionUID = 4852211960728247204L;

	private Class<T> persistentClass;

	protected abstract Class<T> getEntityClass( );

	protected abstract void setParameters( Query query );

	public ReadOnlySessionBean( )
	{
		super( );
	}

	private void setClass( )
	{
		this.persistentClass = getEntityClass( );
	}

	public Class<T> getPersistentClass( )
	{
		if ( this.persistentClass == null )
			setClass( );
		return this.persistentClass;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public T get( @NotNull Serializable key )
	{
		T entity;
		try {
			entity = getEntityManager( ).find( getPersistentClass( ), key );
		}
		catch ( IllegalArgumentException e )
		{
			storeException( e );
			throw e;
		}
		catch ( Exception e ) {
			storeException( e );
			throw e;
		}
		return entity;
	}

	protected String allQueryOrderByClause( String entityAlias )
	{
		return null;
	}

	protected Query getAllQuery( PrincipalDTO auth, String whereClause )
	{
		String sqlQuery;

		try {
			sqlQuery = "select t from " + getPersistentClass( ).getSimpleName( ) + " as t ";
			if ( whereClause != null && whereClause.isEmpty( ) == false ) {
				whereClause = whereClause.trim( );
				String where = whereClause.substring( 0, 5 );
				if ( where.compareToIgnoreCase( "where" ) == 0 ) {
					sqlQuery += " " + whereClause;
				}
				else {
					sqlQuery += " where " + whereClause;
				}
			}
			String orderBy = allQueryOrderByClause( "t" );
			if ( SysUtils.isEmpty( orderBy ) == false ) {
				orderBy = orderBy.toLowerCase( );
				if ( orderBy.indexOf( "order by" ) >= 0 ) {
					sqlQuery += orderBy;
				}
				else {
					sqlQuery += " order by " + orderBy;
				}
			}
			Query query = getEntityManager( ).createQuery( sqlQuery );
			return query;
		}
		catch ( Exception e ) {
			storeException( e );
			throw e;
		}
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Collection<T> getAll( PrincipalDTO auth )
	{
		return getResultList( getAllQuery( auth, null ) );
	}

	@SuppressWarnings( "unchecked" )
	private Collection<T> getResultList( @NotNull Query query )
	{
		try {
			return query.getResultList( );
		}
		catch ( Exception e ) {
			storeException( e );
			return Collections.emptyList( );
		}
	}

	@SuppressWarnings( "unchecked" )
	private T getSingleResult( @NotNull Query query )
	{
		try {
			Object obj;
			obj = query.getSingleResult( );
			if ( obj == null ) {
				return null;
			}
			return (T) obj;
		}
		catch ( Exception e ) {
			storeException( e );
			return null;
		}
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Collection<T> getAll( @NotNull PrincipalDTO auth, String whereClause, DBPaging page )
	{
		Query query = getAllQuery( auth, whereClause );
		if ( page != null ) {
			query.setMaxResults( page.getRows( ) );
			query.setFirstResult( page.getRows( ) * page.getPage( ) );
		}
		setParameters( query );
		return getResultList( query );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Collection<T> getAll( @NotNull PrincipalDTO auth, String whereClause, DBPaging page, Object... params )
	{
		Query query = getAllQuery( auth, whereClause );
		if ( page != null ) {
			query.setMaxResults( page.getRows( ) );
			query.setFirstResult( page.getRows( ) * page.getPage( ) );
		}
		setQueryParams( query, params );
		return getResultList( query );
	}

	protected void setQueryParams( Query query, Object... params )
	{
		if ( params != null && params.length > 0 ) {
			for ( int i = 0; i < params.length; i++ ) {
				query.setParameter( i + 1, params[ i ] );
			}
		}
	}

	protected void setQueryParams( Query query, Map<String, Object> params )
	{
		try {
			for ( Entry<String, Object> entry : params.entrySet( ) ) {
				query.setParameter( entry.getKey( ), entry.getValue( ) );
			}
		}
		catch ( Exception e ) {
			storeException( e );
			throw e;
		}

	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByNamedQuery( String namedQuery, Object... params )
	{
		return findByNamedQuery( namedQuery, null, params );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByNamedQuery( String namedQuery, DBPaging paging, Object... params )
	{
		Query query = getEntityManager( ).createNamedQuery( namedQuery );
		setQueryParams( query, params );
		page( paging, query );
		return (List<T>) getResultList( query );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByNamedParams( String queryname, Map<String, Object> params )
	{
		return findByNamedParams( queryname, null, params );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByNamedParams( String queryname, DBPaging paging, Map<String, Object> params )
	{
		Query query = getEntityManager( ).createNamedQuery( queryname );
		setQueryParams( query, params );
		page( paging, query );
		return (List<T>) getResultList( query );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByNativeQuery( String sql, Object... params )
	{
		return findByNativeQuery( sql, null, params );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByNativeQuery( String sql, DBPaging paging, Object... params )
	{
		Query query = getEntityManager( ).createNativeQuery( sql );
		setQueryParams( query, params );
		page( paging, query );
		return (List<T>) getResultList( query );
	}

	private void page( DBPaging paging, Query query )
	{
		if ( paging != null ) {
			query.setFirstResult( paging.getPage( ) * paging.getRows( ) );
			query.setMaxResults( paging.getRows( ) );
		}
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public T getByNamedQuery( String namedQuery, Object... params )
	{
		return getByNamedQuery( namedQuery, null, params );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public T getByNamedQuery( String namedQuery, DBPaging paging, Object... params )
	{
		Query query = getEntityManager( ).createNamedQuery( namedQuery );
		setQueryParams( query, params );
		page( paging, query );
		return getSingleResult( query );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public T getByNamedParams( String queryname, Map<String, Object> params )
	{
		return getByNamedParams( queryname, null, params );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public T getByNamedParams( String queryname, DBPaging paging, Map<String, Object> params )
	{
		Query query = getEntityManager( ).createNamedQuery( queryname );
		setQueryParams( query, params );
		page( paging, query );
		return getSingleResult( query );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public T getByNativeQuery( String sql, Object... params )
	{
		return getByNativeQuery( sql, null, params );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public T getByNativeQuery( String sql, DBPaging paging, Object... params )
	{
		Query query = getEntityManager( ).createNativeQuery( sql );
		setQueryParams( query, params );
		page( paging, query );
		return getSingleResult( query );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Integer getNextId( )
	{
		Query query = getEntityManager( ).createQuery(
				"select max( o.id ) + 1 from " + getPersistentClass( ).getSimpleName( ) + " o" );
		Integer id;
		try {
			id = (Integer) query.getSingleResult( );
			if ( id == null || id.equals( 0 ) ) {
				id = 1;
			}
		}
		catch ( Exception e ) {
			storeException( e );
			id = 1;
		}
		return id;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public Integer getNextId( String namedQuery, Object... params )
	{
		Query query = getEntityManager( ).createNamedQuery( namedQuery );
		Integer id;
		try {
			setQueryParams( query, params );
			id = (Integer) query.getSingleResult( );
			if ( id == null || id.equals( 0 ) ) {
				id = 1;
			}
		}
		catch ( Exception e ) {
			storeException( e );
			id = 1;
		}
		return id;
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByQuery( String sql, Object... params )
	{
		return findByQuery( sql, null, params );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByQuery( String sql, DBPaging paging, Object... params )
	{
		Query query = getEntityManager( ).createQuery( sql );
		setQueryParams( query, params );
		page( paging, query );
		return (List<T>) getResultList( query );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByQuery( String sql, Map<String, Object> params )
	{
		return findByQuery( sql, null, params );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<T> findByQuery( String sql, DBPaging paging, Map<String, Object> params )
	{
		Query query = getEntityManager( ).createQuery( sql );
		setQueryParams( query, params );
		page( paging, query );
		return (List<T>) getResultList( query );
	}

	public List<Searchable> getSearchables( )
	{
		List<Searchable> list = new ArrayList<Searchable>( );

		Annotation annotation = getEntityClass( ).getAnnotation( Searchables.class );
		if ( annotation != null ) {
			Searchables s = (Searchables) annotation;
			for ( Searchable item : s.value( ) ) {
				list.add( item );
			}
		}
		else {
			annotation = getEntityClass( ).getAnnotation( Searchable.class );
			if ( annotation != null ) {
				list.add( (Searchable) annotation );
			}
		}
		return list;
	}
}
