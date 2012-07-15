package br.com.mcampos.ejb.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.mcampos.ejb.core.entity.ProgramException;
import br.com.mcampos.ejb.core.entity.ProgramExceptionTrace;
import br.com.mcampos.sysutils.SysUtils;

public abstract class ReadOnlySessionBean<T> implements ReadOnlySessionInterface<T>
{
	@Resource
	SessionContext sessionContext;
	@PersistenceContext( unitName = "SystemEJB" )
	private EntityManager em;

	private Class<T> persistentClass;

	protected abstract Class<T> getEntityClass( );

	protected abstract void setParameters( Query query );

	public ReadOnlySessionBean( )
	{
		super( );
		setClass( );
	}

	protected EntityManager getEntityManager( )
	{
		return this.em;
	}

	private void setClass( )
	{
		this.persistentClass = getEntityClass( );
	}

	public Class<T> getPersistentClass( )
	{
		return this.persistentClass;
	}

	@Override
	public T get( Serializable key )
	{
		T entity;
		try {
			entity = getEntityManager( ).find( getPersistentClass( ), key );
		}
		catch ( Exception e ) {
			storeException( e );
			entity = null;
		}
		return entity;
	}

	protected String allQueryOrderByClause( String entityAlias )
	{
		return null;
	}

	protected Query getAllQuery( String whereClause )
	{
		String sqlQuery;

		sqlQuery = "select t from " + getPersistentClass( ).getSimpleName( ) + " as t ";
		if ( whereClause != null && whereClause.isEmpty( ) == false ) {
			whereClause = whereClause.trim( ).toLowerCase( );
			if ( whereClause.startsWith( "where" ) ) {
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

	@Override
	public Collection<T> getAll( )
	{
		return getResultList( getAllQuery( null ) );
	}

	@SuppressWarnings( "unchecked" )
	private Collection<T> getResultList( Query query )
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
	private T getSingleResult( Query query )
	{
		try {
			return (T) query.getSingleResult( );
		}
		catch ( Exception e ) {
			storeException( e );
			return null;
		}
	}

	@Override
	public Collection<T> getAll( String whereClause, DBPaging page )
	{
		Query query = getAllQuery( whereClause );
		if ( page != null ) {
			query.setMaxResults( page.getRows( ) );
			query.setFirstResult( page.getRows( ) * page.getPage( ) );
		}
		setParameters( query );
		return getResultList( query );
	}

	private void setQueryParams( Query query, Object... params )
	{
		if ( params != null && params.length > 0 ) {
			for ( int i = 0; i < params.length; i++ ) {
				query.setParameter( i + 1, params[ i ] );
			}
		}
	}

	private void setQueryParams( Query query, Map<String, Object> params )
	{
		for ( Entry<String, Object> entry : params.entrySet( ) ) {
			query.setParameter( entry.getKey( ), entry.getValue( ) );
		}
	}

	@Override
	public List<T> findByNamedQuery( String namedQuery, Object... params )
	{
		return findByNamedQuery( namedQuery, null, params );
	}

	@Override
	public List<T> findByNamedQuery( String namedQuery, DBPaging paging, Object... params )
	{
		Query query = getEntityManager( ).createNamedQuery( namedQuery );
		setQueryParams( query, params );
		page( paging, query );
		return (List<T>) getResultList( query );
	}

	@Override
	public List<T> findByNamedParams( String queryname, Map<String, Object> params )
	{
		return findByNamedParams( queryname, null, params );
	}

	@Override
	public List<T> findByNamedParams( String queryname, DBPaging paging, Map<String, Object> params )
	{
		Query query = getEntityManager( ).createNamedQuery( queryname );
		setQueryParams( query, params );
		page( paging, query );
		return (List<T>) getResultList( query );
	}

	@Override
	public List<T> findByNativeQuery( String sql, Object... params )
	{
		return findByNativeQuery( sql, null, params );
	}

	@Override
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
	public T getByNamedQuery( String namedQuery, Object... params )
	{
		return getByNamedQuery( namedQuery, null, params );
	}

	@Override
	public T getByNamedQuery( String namedQuery, DBPaging paging, Object... params )
	{
		Query query = getEntityManager( ).createNamedQuery( namedQuery );
		setQueryParams( query, params );
		page( paging, query );
		return getSingleResult( query );
	}

	@Override
	public T getByNamedParams( String queryname, Map<String, Object> params )
	{
		return getByNamedParams( queryname, null, params );
	}

	@Override
	public T getByNamedParams( String queryname, DBPaging paging, Map<String, Object> params )
	{
		Query query = getEntityManager( ).createNamedQuery( queryname );
		setQueryParams( query, params );
		page( paging, query );
		return getSingleResult( query );
	}

	@Override
	public T getByNativeQuery( String sql, Object... params )
	{
		return getByNativeQuery( sql, null, params );
	}

	@Override
	public T getByNativeQuery( String sql, DBPaging paging, Object... params )
	{
		Query query = getEntityManager( ).createNativeQuery( sql );
		setQueryParams( query, params );
		page( paging, query );
		return getSingleResult( query );
	}

	@Override
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
	public void storeException( Exception e )
	{
		if ( e == null ) {
			return;
		}
		e.printStackTrace( );
		try {
			ProgramException entity = new ProgramException( );
			entity.setDescription( e.getMessage( ) );
			getEntityManager( ).persist( entity );
			StackTraceElement[ ] elements = e.getStackTrace( );
			int nIndex = 1;
			for ( StackTraceElement item : elements ) {
				ProgramExceptionTrace trace = new ProgramExceptionTrace( );
				trace.getId( ).setId( nIndex++ );
				trace.setClassName( item.getClassName( ) );
				trace.setFileName( item.getFileName( ) );
				trace.setLine( item.getLineNumber( ) );
				trace.setMethod( item.getMethodName( ) );
				entity.add( trace );
			}
		}
		catch ( Exception ex ) {
			ex = null;
			// just it doesn't matter here
		}
	}
}
