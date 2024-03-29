package br.com.mcampos.ejb.cloudsystem;


import br.com.mcampos.dto.ApplicationException;

import java.io.Serializable;
import java.util.List;


public interface CrudInterface<KEY, ENTITY> extends Serializable
{
    ENTITY add( ENTITY entity ) throws ApplicationException;

    ENTITY update( ENTITY entity ) throws ApplicationException;

    void delete( Class<ENTITY> eClass, KEY key ) throws ApplicationException;

    void delete( ENTITY entity ) throws ApplicationException;

    ENTITY get( Class<ENTITY> eClass, KEY key ) throws ApplicationException;

    List<ENTITY> getAll( String namedQuery ) throws ApplicationException;

    Object getSingleResult( String namedQuery ) throws ApplicationException;

    Integer nextIntegerId( String namedQuery ) throws ApplicationException;

    ENTITY refresh( ENTITY entity ) throws ApplicationException;
}
