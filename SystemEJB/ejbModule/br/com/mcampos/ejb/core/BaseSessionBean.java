package br.com.mcampos.ejb.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;

import br.com.mcampos.ejb.params.SystemParameterSessionLocal;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.utils.dto.PrincipalDTO;

public abstract class BaseSessionBean<T> extends PagingSessionBean<T> implements BaseSessionInterface<T>
{

	@EJB
	private SystemParameterSessionLocal property;

	@Override
	@Deprecated
	public T merge( T newEntity )
	{
		if( newEntity == null ) {
			return null;
		}
		T merged = getEntityManager( ).merge( newEntity );
		return merged;
	}

	public T merge( @NotNull PrincipalDTO auth, @NotNull T newEntity )
	{
		T merged = getEntityManager( ).merge( newEntity );
		return merged;
	}

	@Override
	public T update( @NotNull PrincipalDTO auth, @NotNull T newEntity )
	{
		return merge( auth, newEntity );
	}

	@Override
	public T updateAndRefresh( @NotNull PrincipalDTO auth, @NotNull T newEntity )
	{
		T merged = merge( auth, newEntity );
		refresh( merged );
		return merged;
	}

	@Override
	public T add( @NotNull PrincipalDTO auth, @NotNull T newEntity )
	{
		/*
		 * Do not call this class persist, please!
		 */
		getEntityManager( ).persist( newEntity );
		return newEntity;
	}

	@Override
	public T addAndRefresh( @NotNull PrincipalDTO auth, @NotNull T newEntity )
	{
		if( newEntity == null ) {
			return null;
		}
		add( auth, newEntity );
		refresh( newEntity );
		return newEntity;
	}

	@Override
	@Deprecated
	public T persist( T newEntity )
	{
		if( newEntity == null ) {
			return null;
		}
		return merge( newEntity );
	}

	@Override
	public Collection<T> merge( Collection<T> entities )
	{
		List<T> merged = Collections.emptyList( );
		if( SysUtils.isEmpty( entities ) == false ) {
			merged = new ArrayList<T>( entities.size( ) );
			for( T item : entities ) {
				merged.add( merge( item ) );
			}
		}
		return merged;
	}

	@Override
	public T remove( @NotNull PrincipalDTO auth, @NotNull Serializable key )
	{
		T removed = get( key );
		if( removed != null ) {
			getEntityManager( ).remove( removed );
			return removed;
		}
		else
			return null;
	}

	@Override
	public T refresh( T entity )
	{
		if( entity == null ) {
			return null;
		}
		getEntityManager( ).flush( );
		getEntityManager( ).refresh( entity );
		return entity;
	}

	protected SystemParameterSessionLocal getProperty( )
	{
		return this.property;
	}

	@Override
	public void remove( @NotNull PrincipalDTO auth, @NotNull Collection<T> entities )
	{
		for( T item : entities ) {
			if( item instanceof BaseEntity ) {
				BaseEntity baseEntity = (BaseEntity) item;
				remove( auth, baseEntity.getId( ) );
			}
			else {
				throw new ClassCastException( item.getClass( ).getSimpleName( ) + " is not an instance of BaseEntity " );
			}
		}
	}
}