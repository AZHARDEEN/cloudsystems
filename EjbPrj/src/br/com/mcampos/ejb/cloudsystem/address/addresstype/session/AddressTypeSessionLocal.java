package br.com.mcampos.ejb.cloudsystem.address.addresstype.session;


import br.com.mcampos.ejb.cloudsystem.address.addresstype.entity.AddressType;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AddressTypeSessionLocal extends Serializable
{
    void delete( Integer key ) throws ApplicationException;

    AddressType get( Integer key ) throws ApplicationException;

    List<AddressType> getAll() throws ApplicationException;

    Integer nextIntegerId() throws ApplicationException;

    AddressType add( AddressType entity ) throws ApplicationException;

    AddressType update( AddressType entity ) throws ApplicationException;
}
