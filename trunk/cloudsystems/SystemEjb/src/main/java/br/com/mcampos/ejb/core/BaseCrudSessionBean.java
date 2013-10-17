package br.com.mcampos.ejb.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.params.SystemParameterSessionLocal;
import br.com.mcampos.jpa.BaseEntity;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseCrudSessionBean<T> extends PagingSessionBean<T> implements BaseCrudSessionInterface<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1598463542802104703L;
	@EJB
	private SystemParameterSessionLocal property;

	@Override
	@Deprecated
	public T merge( T newEntity )
	{
		try {
			if ( newEntity == null ) {
				return null;
			}
			T merged = this.getEntityManager( ).merge( newEntity );
			return merged;
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}
	}

	public T merge( PrincipalDTO auth, T newEntity )
	{
		try {
			T merged = this.getEntityManager( ).merge( newEntity );
			return merged;
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}
	}

	@Override
	public T update( PrincipalDTO auth, T newEntity )
	{
		return this.merge( auth, newEntity );
	}

	@Override
	public T updateAndRefresh( PrincipalDTO auth, T newEntity )
	{
		T merged = this.merge( auth, newEntity );
		this.refresh( merged );
		return merged;
	}

	@Override
	public T add( PrincipalDTO auth, T newEntity )
	{
		/*
		 * Do not call this class persist method, please!
		 */
		return this.add( newEntity );
	}

	@Override
	public T add( T newEntity )
	{
		/*
		 * Do not call this class persist, please!
		 */
		try {
			this.getEntityManager( ).persist( newEntity );
			return newEntity;
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}
	}

	@Override
	public T addAndRefresh( PrincipalDTO auth, T newEntity )
	{
		if ( newEntity == null ) {
			return null;
		}
		this.add( auth, newEntity );
		this.refresh( newEntity );
		return newEntity;
	}

	@Override
	@Deprecated
	public T persist( T newEntity )
	{
		if ( newEntity == null ) {
			return null;
		}
		return this.merge( newEntity );
	}

	@Override
	public Collection<T> merge( Collection<T> entities )
	{
		List<T> merged = Collections.emptyList( );
		if ( SysUtils.isEmpty( entities ) == false ) {
			merged = new ArrayList<T>( entities.size( ) );
			for ( T item : entities ) {
				merged.add( this.merge( item ) );
			}
		}
		return merged;
	}

	@Override
	public T remove( PrincipalDTO auth, Serializable key )
	{
		try {
			T removed = this.get( key );
			if ( removed != null ) {
				this.getEntityManager( ).remove( removed );
				return removed;
			}
			else {
				return null;
			}
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}
	}

	@Override
	public T refresh( T entity )
	{
		try {
			if ( entity == null ) {
				return null;
			}
			this.getEntityManager( ).flush( );
			this.getEntityManager( ).refresh( entity );
			return entity;
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}
	}

	protected SystemParameterSessionLocal getProperty( )
	{
		return this.property;
	}

	@Override
	public void remove( PrincipalDTO auth, Collection<T> entities )
	{
		try {
			for ( T item : entities ) {
				if ( item instanceof BaseEntity ) {
					BaseEntity baseEntity = (BaseEntity) item;
					this.remove( auth, baseEntity.getId( ) );
				}
				else {
					throw new ClassCastException( item.getClass( ).getSimpleName( ) + " is not an instance of BaseEntity " );
				}
			}
		}
		catch ( Exception e ) {
			this.storeException( e );
			throw e;
		}
	}
}