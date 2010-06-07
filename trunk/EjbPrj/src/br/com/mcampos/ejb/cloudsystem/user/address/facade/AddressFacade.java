package br.com.mcampos.ejb.cloudsystem.user.address.facade;


import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AddressFacade extends Serializable
{
    List<AddressTypeDTO> getTypes() throws ApplicationException;

    List<StateDTO> getStates( CountryDTO country ) throws ApplicationException;

    List<CountryDTO> getCountries() throws ApplicationException;
}
