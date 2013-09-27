package br.com.mcampos.ejb.core;

import java.io.Serializable;
import java.util.Collection;

import javax.validation.constraints.NotNull;

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

	public T update( @NotNull PrincipalDTO auth, @NotNull T newEntity );

	public T add( @NotNull PrincipalDTO auth, @NotNull T newEntity );

	public T add( @NotNull T newEntity );

	public T updateAndRefresh( @NotNull PrincipalDTO auth, @NotNull T newEntity );

	public T addAndRefresh( @NotNull PrincipalDTO auth, @NotNull T newEntity );

	public T remove( @NotNull PrincipalDTO auth, @NotNull Serializable key );

	public void remove( @NotNull PrincipalDTO auth, @NotNull Collection<T> entities );

	public T refresh( @NotNull T entity );

	public Class<T> getPersistentClass( );
}
