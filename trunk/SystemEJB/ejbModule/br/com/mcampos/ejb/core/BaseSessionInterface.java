package br.com.mcampos.ejb.core;

import java.util.Collection;

public interface BaseSessionInterface<T> extends PagingSessionInterface<T>
{
	public T merge( T newEntity );

	public T persist( T newEntity );

	public Collection<T> merge( Collection<T> entities );

	public T remove( T entity );

	public T refresh( T entity );

	public Collection<T> remove( Collection<T> entities );

	public Class<T> getPersistentClass( );

	public T update( T newEntity );

	public T add( T newEntity );

	public T updateAndRefresh( T newEntity );

	public T addAndRefresh( T newEntity );

}
