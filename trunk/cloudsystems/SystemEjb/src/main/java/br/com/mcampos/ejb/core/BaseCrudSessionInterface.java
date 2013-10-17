package br.com.mcampos.ejb.core;

import java.io.Serializable;
import java.util.Collection;

import br.com.mcampos.dto.core.PrincipalDTO;

public interface BaseCrudSessionInterface<T> extends PagingSessionInterface<T>
{
	@Deprecated
	public T merge( T newEntity );

	@Deprecated
	public T persist( T newEntity );

	@Deprecated
	public Collection<T> merge( Collection<T> entities );

	/*
	 * Version 2 functions
	 */

	public T update( PrincipalDTO auth, T newEntity );

	public T add( PrincipalDTO auth, T newEntity );

	public T add( T newEntity );

	public T updateAndRefresh( PrincipalDTO auth, T newEntity );

	public T addAndRefresh( PrincipalDTO auth, T newEntity );

	public T remove( PrincipalDTO auth, Serializable key );

	public void remove( PrincipalDTO auth, Collection<T> entities );

	public T refresh( T entity );

	public Class<T> getPersistentClass( );
}
