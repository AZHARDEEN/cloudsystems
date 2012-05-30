package br.com.mcampos.ejb.cloudsystem.user.address.session;


import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.Address;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.AddressPK;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AddressSessionLocal extends Serializable
{
    void delete( AddressPK key ) throws ApplicationException;

    void delete( Users user ) throws ApplicationException;

    Address get( AddressPK key ) throws ApplicationException;

    List<Address> getAll( Users user ) throws ApplicationException;

    Address add( Address entity ) throws ApplicationException;

    Address update( Address entity ) throws ApplicationException;

    void refresh( Users user, List<Address> addresses ) throws ApplicationException;
}
