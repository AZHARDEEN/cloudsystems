package br.com.mcampos.ejb.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ReadOnlySessionInterface<T>
{
	public T get( Serializable key );

	public Collection<T> getAll( );

	public Collection<T> getAll( String whereClause, DBPaging page );

	List<T> findByNamedQuery( String namedQuery, Object... params );

	List<T> findByNamedQuery( String namedQuery, DBPaging paging, Object... params );

	List<T> findByNamedParams( String queryname, Map<String, Object> params );

	List<T> findByNamedParams( String queryname, DBPaging paging, Map<String, Object> params );

	List<T> findByNativeQuery( String sql, Object... params );

	List<T> findByNativeQuery( String sql, DBPaging paging, Object... params );

	T getByNamedQuery( String namedQuery, Object... params );

	T getByNamedQuery( String namedQuery, DBPaging paging, Object... params );

	T getByNamedParams( String queryname, Map<String, Object> params );

	T getByNamedParams( String queryname, DBPaging paging, Map<String, Object> params );

	T getByNativeQuery( String sql, Object... params );

	T getByNativeQuery( String sql, DBPaging paging, Object... params );

	Integer getNextId( );

	Integer getNextId( String namedQuery, Object... params );

	void storeException( Exception e );
}
