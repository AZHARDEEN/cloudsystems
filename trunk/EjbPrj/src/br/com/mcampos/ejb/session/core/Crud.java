package br.com.mcampos.ejb.session.core;


import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@TransactionAttribute( TransactionAttributeType.SUPPORTS )
public abstract class Crud<KEY, ENTITY> implements CrudInterface<KEY, ENTITY>
{
    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;


    public Crud()
    {
        super();
    }

    @TransactionAttribute( TransactionAttributeType.MANDATORY )
    public ENTITY add( ENTITY entity ) throws ApplicationException
    {
        getEntityManager().persist( entity );
        return entity;
    }

    public ENTITY refresh( ENTITY entity ) throws ApplicationException
    {
        if ( entity != null ) {
            getEntityManager().flush();
            try {
                getEntityManager().refresh( entity );
            }
            catch ( Exception e ) {
                e = null;
            }
        }
        return entity;
    }


    @TransactionAttribute( TransactionAttributeType.MANDATORY )
    public void delete( Class<ENTITY> eClass, KEY key ) throws ApplicationException
    {
        ENTITY toDelete = get( eClass, key );

        getEntityManager().remove( toDelete );
    }

    @TransactionAttribute( TransactionAttributeType.MANDATORY )
    public void delete( ENTITY entity ) throws ApplicationException
    {
        if ( entity != null )
            getEntityManager().remove( entity );
    }


    public List<ENTITY> getAll( String namedQuery ) throws ApplicationException
    {
        if ( namedQuery == null )
            return Collections.emptyList();
        return ( List<ENTITY> )getResultList( namedQuery );
    }

    @TransactionAttribute( TransactionAttributeType.MANDATORY )
    public ENTITY update( ENTITY entity ) throws ApplicationException
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
        catch ( Exception e ) {
            return null;
        }
    }

    public List<?> getResultList( String namedQuery ) throws ApplicationException
    {
        return getResultList( namedQuery, Collections.emptyList() );
    }

    public List<?> getResultList( String namedQuery, Object param ) throws ApplicationException
    {
        List<Object> parameter = new ArrayList<Object>( 1 );
        parameter.add( param );
        return getResultList( namedQuery, parameter );
    }

    public List<?> getResultList( String namedQuery, Object param1, Object param2 ) throws ApplicationException
    {
        List<Object> parameter = new ArrayList<Object>( 1 );
        parameter.add( param1 );
        parameter.add( param2 );
        return getResultList( namedQuery, parameter );
    }


    public Object getSingleResult( String namedQuery, Object param ) throws ApplicationException
    {
        List<Object> parameter = new ArrayList<Object>( 1 );
        parameter.add( param );
        return getSingleResult( namedQuery, parameter );
    }

    public Object getSingleResult( String namedQuery, Object param1, Object param2 ) throws ApplicationException
    {
        List<Object> parameter = new ArrayList<Object>( 1 );
        parameter.add( param1 );
        parameter.add( param2 );
        return getSingleResult( namedQuery, parameter );
    }


    public List<?> getResultList( String namedQuery, List<Object> list ) throws ApplicationException
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
        catch ( Exception e ) {

            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public Integer nextIntegerId( String namedQuery ) throws ApplicationException
    {
        Integer id;

        try {
            id = ( Integer )getSingleResult( namedQuery );
            if ( SysUtils.isZero( id ) )
                id = 0;
            id++;
        }
        catch ( Exception e ) {
            id = 1;
        }
        return id;
    }

    protected Integer nextIntegerId( String namedQuery, Object param ) throws ApplicationException
    {
        Integer id;

        try {
            id = ( Integer )getSingleResult( namedQuery, param );
            if ( SysUtils.isZero( id ) )
                id = 0;
            id++;
        }
        catch ( Exception e ) {
            id = 1;
        }
        return id;
    }

    public ENTITY get( Class<ENTITY> eClass, KEY key ) throws ApplicationException
    {
        try {
            return getEntityManager().find( eClass, key );
        }
        catch ( Exception e ) {
            e = null;
            return null;
        }
    }
}
