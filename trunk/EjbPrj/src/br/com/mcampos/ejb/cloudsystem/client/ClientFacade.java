package br.com.mcampos.ejb.cloudsystem.client;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ClientFacade extends Serializable
{
    List<ListUserDTO> getClients( AuthenticationDTO auth ) throws ApplicationException;

    List<ListUserDTO> getCompanies( AuthenticationDTO auth ) throws ApplicationException;

    List<ListUserDTO> getPeople( AuthenticationDTO auth ) throws ApplicationException;
}
