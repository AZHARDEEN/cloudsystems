package br.com.mcampos.ejb.cloudsystem.user.company.facade;


import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CompanyFacade extends Serializable
{
    List<StateDTO> getStates( CountryDTO country ) throws ApplicationException;

    List<CountryDTO> getCountries() throws ApplicationException;

    List<CityDTO> getCities( StateDTO state ) throws ApplicationException;
}
