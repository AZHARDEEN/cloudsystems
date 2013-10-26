package br.com.mcampos.ejb.cloudsystem.typing.iab.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.typing.iab.IabProblemsDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.ejb.cloudsystem.typing.iab.entity.DadosIab;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface IABFacade extends Serializable
{
    void add( AuthenticationDTO currentUser, DadosIab record ) throws ApplicationException;

    DadosIab get( String id2 ) throws ApplicationException;

    CityDTO getCity( Integer cityId ) throws ApplicationException;

    List<GenderDTO> getGenders() throws ApplicationException;

    List<IabProblemsDTO> getProblems() throws ApplicationException;
}
