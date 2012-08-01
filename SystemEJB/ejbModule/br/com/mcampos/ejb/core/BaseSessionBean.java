package br.com.mcampos.ejb.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;

import br.com.mcampos.ejb.params.SystemParameterSessionLocal;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseSessionBean<T> extends PagingSessionBean<T> implements BaseSessionInterface<T>
{

	@EJB
	SystemParameterSessionLocal property;

	@Override
	public T merge( T newEntity )
	{
		if ( newEntity == null ) {
			return null;
		}
		try {
			T merged = getEntityManager( ).merge( newEntity );
			getEntityManager( ).flush( );
			getEntityManager( ).refresh( merged );
			return merged;
		}
		catch ( Exception e ) {
			e.printStackTrace( );
			return null;
		}
	}

	@Override
	public T persist( T newEntity )
	{
		if ( newEntity == null ) {
			return null;
		}
		return merge( newEntity );
	}

	@Override
	public Collection<T> merge( Collection<T> entities )
	{
		List<T> merged = Collections.emptyList( );
		if ( SysUtils.isEmpty( entities ) == false ) {
			merged = new ArrayList<T>( entities.size( ) );
			for ( T item : entities ) {
				merged.add( merge( item ) );
			}
		}
		return merged;
	}

	@Override
	public Collection<T> remove( Collection<T> entities )
	{
		if ( entities == null || entities.size( ) == 0 ) {
			return Collections.emptyList( );
		}
		List<T> merged = new ArrayList<T>( entities.size( ) );
		for ( T item : entities ) {
			merged.add( remove( item ) );
		}
		return merged;
	}

	@Override
	public T remove( T entity )
	{
		if ( entity == null ) {
			return null;
		}
		T removed = merge( entity );
		getEntityManager( ).remove( removed );
		return removed;
	}

	@Override
	public T refresh( T entity )
	{
		if ( entity == null ) {
			return null;
		}
		getEntityManager( ).refresh( entity );
		return entity;
	}

	protected SystemParameterSessionLocal getProperty( )
	{
		return this.property;
	}

}