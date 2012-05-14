package br.com.mcampos.ejb.core;

import java.io.Serializable;

import java.util.Collection;

import javax.annotation.Resource;

import javax.ejb.SessionContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public abstract class ReadOnlySessionBean<T> implements ReadOnlySessionInterface<T>
{
    @Resource
    SessionContext sessionContext;
    @PersistenceContext( unitName = "SystemEJB" )
    private EntityManager em;

    private Class<T> persistentClass;

    protected abstract Class<T> getEntityClass();

    protected abstract void setParameters( Query query );


    public ReadOnlySessionBean()
    {
        super();
        setClass();
    }

    protected EntityManager getEntityManager()
    {
        return this.em;
    }

    private void setClass()
    {
        this.persistentClass = getEntityClass();
    }

    public Class<T> getPersistentClass()
    {
        return this.persistentClass;
    }


    @Override
    public <T> T get( Serializable key )
    {
        T entity = (T) getEntityManager().find( getPersistentClass(), key );
        return entity;
    }

    protected Query getAllQuery( String whereClause )
    {
        String sqlQuery;

        sqlQuery = "select t from " + getPersistentClass().getSimpleName() + " as t ";
        if ( whereClause != null && whereClause.isEmpty() == false ) {
            whereClause = whereClause.trim().toLowerCase();
            if ( whereClause.startsWith( "where" ) ) {
                sqlQuery += " " + whereClause;
            }
            else {
                sqlQuery += " where " + whereClause;
            }
        }
        Query query = getEntityManager().createQuery( sqlQuery );
        return query;
    }

    @SuppressWarnings( "unchecked" )
    public Collection<T> getAll()
    {
        Query query = getAllQuery( null );
        return query.getResultList();
    }

    @SuppressWarnings( "unchecked" )
    public Collection<T> getAll( String whereClause, DBPaging page )
    {
        Query query = getAllQuery( whereClause );
        if ( page != null ) {
            query.setMaxResults( page.getRows() );
            query.setFirstResult( page.getRows() * page.getPage() );
        }
        setParameters( query );
        return query.getResultList();
    }

}
