package br.com.mcampos.ejb.cloudsystem.user.person.facade;


import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface PersonFacade extends Serializable
{
    PersonDTO get( AuthenticationDTO auth ) throws ApplicationException;

    List<StateDTO> getStates( CountryDTO country ) throws ApplicationException;

    List<CountryDTO> getCountries() throws ApplicationException;

    List<GenderDTO> getGenders() throws ApplicationException;

    List<CivilStateDTO> getCivilStates() throws ApplicationException;

    List<CityDTO> getCities( StateDTO state ) throws ApplicationException;

    void updateMyRecord( AuthenticationDTO auth, PersonDTO dto ) throws ApplicationException;

}
