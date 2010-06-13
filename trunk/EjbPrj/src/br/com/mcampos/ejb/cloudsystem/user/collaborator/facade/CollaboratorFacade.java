package br.com.mcampos.ejb.cloudsystem.user.collaborator.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CollaboratorFacade extends Serializable
{
    List<CompanyDTO> getCompanies( AuthenticationDTO auth ) throws ApplicationException;
}
