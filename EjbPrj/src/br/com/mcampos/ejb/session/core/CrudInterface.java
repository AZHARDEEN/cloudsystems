package br.com.mcampos.ejb.session.core;

import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public interface CrudInterface<KEY, ENTITY>
{
    ENTITY add( ENTITY entity ) throws ApplicationException;

    ENTITY update( ENTITY entity ) throws ApplicationException;

    void delete( Class<ENTITY> eClass, KEY key ) throws ApplicationException;

    ENTITY get( Class<ENTITY> eClass, KEY key ) throws ApplicationException;

    List<ENTITY> getAll( String namedQuery ) throws ApplicationException;

    Object getSingleResult( String namedQuery ) throws ApplicationException;

}
