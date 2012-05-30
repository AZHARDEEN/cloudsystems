package br.com.mcampos.ejb.cloudsystem.resale.dealer.type.session;


import br.com.mcampos.ejb.cloudsystem.resale.dealer.type.entity.DealerType;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface DealerTypeSessionLocal extends Serializable
{
    DealerType add( DealerType entity ) throws ApplicationException;

    DealerType update( DealerType entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    void delete( DealerType entity ) throws ApplicationException;

    DealerType get( Integer key ) throws ApplicationException;

    List<DealerType> getAll() throws ApplicationException;

    Integer nextId() throws ApplicationException;

}
