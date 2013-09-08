package br.com.mcampos.ejb.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import br.com.mcampos.utils.dto.PrincipalDTO;

public interface ReadOnlySessionInterface<T> extends BaseSessionInterface
{
	public T get( @NotNull Serializable key );

	public Collection<T> getAll( @NotNull PrincipalDTO auth );

	public Collection<T> getAll( @NotNull PrincipalDTO auth, String whereClause, DBPaging page );

	public Collection<T> getAll( @NotNull PrincipalDTO auth, String whereClause, DBPaging page, Object... params );

	List<T> findByNamedQuery( String namedQuery, Object... params );

	List<T> findByNamedQuery( String namedQuery, DBPaging paging, Object... params );

	List<T> findByNamedParams( String queryname, Map<String, Object> params );

	List<T> findByNamedParams( String queryname, DBPaging paging, Map<String, Object> params );

	List<T> findByNativeQuery( String sql, Object... params );

	List<T> findByNativeQuery( String sql, DBPaging paging, Object... params );

	List<T> findByQuery( String sql, Object... params );

	List<T> findByQuery( String sql, DBPaging paging, Object... params );

	List<T> findByQuery( String sql, Map<String, Object> params );

	List<T> findByQuery( String sql, DBPaging paging, Map<String, Object> params );

	T getByNamedQuery( String namedQuery, Object... params );

	T getByNamedQuery( String namedQuery, DBPaging paging, Object... params );

	T getByNamedParams( String queryname, Map<String, Object> params );

	T getByNamedParams( String queryname, DBPaging paging, Map<String, Object> params );

	T getByNativeQuery( String sql, Object... params );

	T getByNativeQuery( String sql, DBPaging paging, Object... params );

	Integer getNextId( );

	Integer getNextId( String namedQuery, Object... params );

}
