package br.com.mcampos.ejb.cloudsystem.client.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.PersonDTO;

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

    ClientDTO add( AuthenticationDTO auth, PersonDTO dto ) throws ApplicationException;

    ClientDTO update( AuthenticationDTO auth, Integer clientSequence, CompanyDTO dto ) throws ApplicationException;

    ClientDTO update( AuthenticationDTO auth, Integer clientSequence, PersonDTO dto ) throws ApplicationException;

    void delete( AuthenticationDTO auth, ClientDTO dto ) throws ApplicationException;

    MediaDTO getLogo( AuthenticationDTO auth, ClientDTO dto ) throws ApplicationException;

    void setLogo( AuthenticationDTO auth, ClientDTO dto, MediaDTO media ) throws ApplicationException;

    CompanyDTO getCompany( AuthenticationDTO auth, Integer clientSequence ) throws ApplicationException;

    CompanyDTO getCompany( AuthenticationDTO auth, String document, Integer docTpe ) throws ApplicationException;

    PersonDTO getPerson( AuthenticationDTO auth, Integer clientSequence ) throws ApplicationException;

    PersonDTO getPerson( AuthenticationDTO auth, String document, Integer docTpe ) throws ApplicationException;
    
    ClientDTO add( CompanyDTO dto ) throws ApplicationException;

    ClientDTO add( PersonDTO dto ) throws ApplicationException;
}
