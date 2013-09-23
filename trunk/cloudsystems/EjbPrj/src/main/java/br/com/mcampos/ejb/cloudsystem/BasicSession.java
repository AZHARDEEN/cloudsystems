package br.com.mcampos.ejb.cloudsystem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.mcampos.dto.core.Paging;

/**
 * @param <T>
 */
@Stateless
@TransactionAttribute( value = TransactionAttributeType.SUPPORTS )
public class BasicSession<T> implements IBasicSession<T>, IBasicSessionLocal<T>, Serializable
{
	private static final long serialVersionUID = -730881462584819731L;

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager em;

	private transient final T entity = null;

	public BasicSession( )
	{
		super( );
	}

	@Override
	public List<T> findAll( )
	{
		return findAll( null );
	}

	@Override
	public List<T> findAll( Paging paging )
	{
		Query query = getEm( ).createQuery( " FROM " + entity.getClass( ).getSimpleName( ) );
		if ( paging != null )
			page( paging, query );
		return query.getResultList( );
	}

	private void page( Paging paging, Query query )
	{
		if ( paging != null ) {
			query.setFirstResult( paging.getPageNumber( ) * paging.getPageSize( ) );
			query.setMaxResults( paging.getPageSize( ) );
		}
	}

	@Override
	public T findById( Object id )
	{
		// return ( getEm().find( entity.getClass(), id ) );
		return null;
	}

	@Override
	public List<T> find( String queryStr, Object[ ] params )
	{
		return find( queryStr, null, params );
	}

	private void setQueryParams( Query query, Object... params )
	{
		if ( params != null && params.length > 0 )
			for ( int i = 0; i < params.length; i++ )
				query.setParameter( i + 1, params[ i ] );
	}

	private void setQueryParams( Query query, Map<String, Object> params )
	{
		for ( Entry<String, Object> entry : params.entrySet( ) )
			query.setParameter( entry.getKey( ), entry.getValue( ) );
	}

	@Override
	public List<T> find( String queryStr, Paging paging, Object[ ] params )
	{
		Query query = getEm( ).createQuery( queryStr );
		setQueryParams( query, params );
		if ( paging != null )
			page( paging, query );
		return query.getResultList( );
	}

	@Override
	public List<T> findByNamedQuery( String namedQuery, Object[ ] params )
	{
		return findByNamedQuery( namedQuery, null, params );
	}

	@Override
	public List<T> findByNamedQuery( String namedQuery, Paging paging, Object[ ] params )
	{
		Query query = getEm( ).createNamedQuery( namedQuery );
		setQueryParams( query, params );
		page( paging, query );
		return query.getResultList( );
	}

	@Override
	public List<T> findByNamedParams( String queryname, Map<String, Object> params )
	{
		return findByNamedParams( queryname, null, params );
	}

	@Override
	public List<T> findByNamedParams( String queryname, Paging paging, Map<String, Object> params )
	{
		Query query = getEm( ).createNamedQuery( queryname );
		setQueryParams( query, params );
		page( paging, query );
		return query.getResultList( );
	}

	@Override
	public List<T> findByNativeQuery( String sql, Object[ ] params )
	{
		return findByNativeQuery( sql, null, params );
	}

	@Override
	public List<T> findByNativeQuery( String sql, Paging paging, Object[ ] params )
	{
		Query query = getEm( ).createNativeQuery( sql );
		setQueryParams( query, params );
		page( paging, query );
		return query.getResultList( );
	}

	@Override
	public T refresh( T entity )
	{
		getEm( ).refresh( entity );
		return entity;
	}

	@Override
	@TransactionAttribute( value = TransactionAttributeType.REQUIRED )
	public T add( T entity )
	{
		getEm( ).persist( entity );
		return refresh( entity );
	}

	@Override
	@TransactionAttribute( value = TransactionAttributeType.REQUIRED )
	public T update( T entity )
	{
		return getEm( ).merge( entity );
	}

	@Override
	@TransactionAttribute( value = TransactionAttributeType.REQUIRED )
	public T delete( Object id )
	{
		T entity = findById( id );
		if ( entity == null )
			throw new EntityNotFoundException( entity.getClass( ).getSimpleName( ) + " does not exists " );
		getEm( ).remove( entity );
		return entity;
	}

	private EntityManager getEm( )
	{
		return this.em;
	}

}
