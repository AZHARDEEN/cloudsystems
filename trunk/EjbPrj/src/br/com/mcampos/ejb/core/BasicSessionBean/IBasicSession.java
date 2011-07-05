package br.com.mcampos.ejb.core.BasicSessionBean;


import br.com.mcampos.paging.Paging;

import java.io.Serializable;

import java.util.List;
import java.util.Map;


public interface IBasicSession<T>
{

    List<T> findAll();

    List<T> findAll( Paging paging );


    T findById( Serializable id );

    List<T> find( String queryStr, Object... params );

    List<T> find( String queryStr, Paging paging, Object... params );


    List<T> findByNamedQuery( String namedQuery, Object... params );

    List<T> findByNamedQuery( String namedQuery, Paging paging, Object... params );


    List<T> findByNamedParams( String queryname, Map<String, Object> params );

    List<T> findByNamedParams( String queryname, Paging paging, Map<String, Object> params );


    List<T> findByNativeQuery( String sql, Object... params );

    List<T> findByNativeQuery( String sql, Paging paging, Object... params );


    T refresh( T entity );

    T add( T obj );

    T update( T obj );

    T delete( Serializable id );
}
