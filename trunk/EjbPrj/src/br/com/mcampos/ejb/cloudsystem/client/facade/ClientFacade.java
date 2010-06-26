package br.com.mcampos.ejb.cloudsystem.client.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ClientFacade extends Serializable
{
    List<ClientDTO> getClients( AuthenticationDTO auth ) throws ApplicationException;

    List<ClientDTO> getCompanies( AuthenticationDTO auth ) throws ApplicationException;

    List<ClientDTO> getPeople( AuthenticationDTO auth ) throws ApplicationException;

    ClientDTO add( AuthenticationDTO auth, CompanyDTO dto ) throws ApplicationException;

    ClientDTO update( AuthenticationDTO auth, Integer clientSequence, CompanyDTO dto ) throws ApplicationException;

    void delete( AuthenticationDTO auth, ClientDTO dto ) throws ApplicationException;

    MediaDTO getLogo( AuthenticationDTO auth, ClientDTO dto ) throws ApplicationException;

    void setLogo( AuthenticationDTO auth, ClientDTO dto, MediaDTO media ) throws ApplicationException;

    CompanyDTO get( AuthenticationDTO auth, Integer companyID ) throws ApplicationException;

    CompanyDTO get( AuthenticationDTO auth, String document, Integer docTpe ) throws ApplicationException;
}
