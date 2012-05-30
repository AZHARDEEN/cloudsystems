
package br.com.mcampos.ejb.core.BasicSessionBean;


import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;


public abstract class BasicSessionBean
{
	/**
	 * EntityManager Getter. Every SessionBean should have one!, just like this:
	 *      public EntityManager getEm()
	 *      {
	 *          return em;
	 *      }
	 *
	 * @return EntityManager
	 */
	protected abstract EntityManager getEntityManager();

	public BasicSessionBean()
	{
		super();
	}


	protected Object get( Class<?> objClass, Object primaryKey )
	{
		try {
			return getEntityManager().find( objClass, primaryKey );
		}
		catch ( Exception e ) {
			/*
             * TODO: tratar a exceção aqui!!!!!!
             */
			e = null;
			return null;
		}
	}

	protected Object nativeQuerySingleResult( String nativeSQL ) throws ApplicationException
	{
		try {
			Query query = getEntityManager().createNativeQuery( nativeSQL );
			return query.getSingleResult();
		}
		catch ( NoResultException e ) {
			e = null;
			return null;
		}
	}


	protected List<?> nativeQueryResultList( String nativeSQL ) throws ApplicationException
	{
		try {
			Query query = getEntityManager().createNativeQuery( nativeSQL );
			return query.getResultList();
		}
		catch ( NoResultException e ) {
			e = null;
			return Collections.emptyList();
		}
	}
}
