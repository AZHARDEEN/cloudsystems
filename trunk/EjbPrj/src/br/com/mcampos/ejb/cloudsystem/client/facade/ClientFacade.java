package br.com.mcampos.ejb.cloudsystem.client.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
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

    void delete( AuthenticationDTO auth, ClientDTO dto ) throws ApplicationException;
}
