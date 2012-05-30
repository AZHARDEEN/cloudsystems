package br.com.mcampos.ejb.cloudsystem.resale.facade;


import br.com.mcampos.dto.resale.ResaleDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ResaleFacade extends Serializable
{
    void delete( AuthenticationDTO auth, Integer sequence ) throws ApplicationException;

    ResaleDTO get( AuthenticationDTO auth, Integer sequence ) throws ApplicationException;

    ResaleDTO add( AuthenticationDTO auth, ResaleDTO dto ) throws ApplicationException;

    ResaleDTO update( AuthenticationDTO auth, ResaleDTO dto ) throws ApplicationException;

    List<ResaleDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;

    List<ClientDTO> getCompanies( AuthenticationDTO auth ) throws ApplicationException;
}
