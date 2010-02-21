package br.com.mcampos.ejb.session.core;

import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import java.util.Map;

import java.util.Properties;

import javax.ejb.TransactionAttribute;

import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@TransactionAttribute( TransactionAttributeType.SUPPORTS )
public abstract class Crud<KEY, ENTITY> implements CrudInterface<KEY, ENTITY>
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;


    public Crud()
    {
        super();
    }

    public ENTITY add( ENTITY entity )
    {
        getEntityManager().persist( entity );
        getEntityManager().flush();
        getEntityManager().refresh( entity );
        return entity;
    }

    public void delete( Class<ENTITY> eClass, KEY key )
    {
        ENTITY toDelete = get( eClass, key );

        getEntityManager().remove( toDelete );
    }

    public ENTITY get( Class<ENTITY> eClass, KEY key )
    {
        try {
            return getEntityManager().find( eClass, key );
        }
        catch ( NoResultException e ) {
            e = null;
            return null;
        }
    }

    public List<ENTITY> getAll( String namedQuery )
    {
        if ( namedQuery == null )
            return Collections.emptyList();
        try {
            return getEntityManager().createNamedQuery( namedQuery ).getResultList();
        }
        catch ( NoResultException e ) {
            e = null;
            return Collections.emptyList();
        }
    }

    public ENTITY update( ENTITY entity )
    {
        ENTITY merged = getEntityManager().merge( entity );
        return merged;
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Object getSingleResult( String namedQuery ) throws ApplicationException
    {
        return getSingleResult( namedQuery, Collections.emptyList() );
    }

    public Object getSingleResult( String namedQuery, List<Object> list ) throws ApplicationException
    {
        try {
            Query query = getEntityManager().createNamedQuery( namedQuery );
            if ( SysUtils.isEmpty( list ) == false ) {
                int nIndex = 1;
                for ( Object obj : list ) {
                    query.setParameter( nIndex, obj );
                    nIndex++;
                }
            }
            return query.getSingleResult();

        }
        catch ( NoResultException e ) {
            return null;
        }
    }

    public List getResultList( String namedQuery ) throws ApplicationException
    {
        return getResultList( namedQuery, Collections.emptyList() );
    }

    public List getResultList( String namedQuery, List<Object> list ) throws ApplicationException
    {
        try {
            Query query = getEntityManager().createNamedQuery( namedQuery );
            if ( SysUtils.isEmpty( list ) == false ) {
                int nIndex = 1;
                for ( Object obj : list ) {
                    query.setParameter( nIndex, obj );
                    nIndex++;
                }
            }
            return query.getResultList();

        }
        catch ( NoResultException e ) {
            return Collections.emptyList();
        }
    }


    public Integer nextIntegerId( String namedQuery ) throws ApplicationException
    {
        Integer id = ( Integer )getSingleResult( namedQuery );
        if ( SysUtils.isZero( id ) )
            id = 0;
        id++;
        return id;
    }
}
