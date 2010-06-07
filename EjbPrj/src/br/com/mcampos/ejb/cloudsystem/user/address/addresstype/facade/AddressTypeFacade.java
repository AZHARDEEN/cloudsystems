package br.com.mcampos.ejb.cloudsystem.user.address.addresstype.facade;


import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AddressTypeFacade extends Serializable
{
    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, AddressTypeDTO entity ) throws ApplicationException;

    AddressTypeDTO get( AuthenticationDTO auth, AddressTypeDTO entity ) throws ApplicationException;

    AddressTypeDTO add( AuthenticationDTO auth, AddressTypeDTO entity ) throws ApplicationException;

    AddressTypeDTO update( AuthenticationDTO auth, AddressTypeDTO entity ) throws ApplicationException;

    List<AddressTypeDTO> getAll() throws ApplicationException;
}
