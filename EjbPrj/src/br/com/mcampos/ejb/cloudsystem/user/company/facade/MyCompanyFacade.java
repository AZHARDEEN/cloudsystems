package br.com.mcampos.ejb.cloudsystem.user.company.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Remote;


@Remote
public interface MyCompanyFacade extends Serializable
{
    CompanyDTO get( AuthenticationDTO auth ) throws ApplicationException;

    CompanyDTO update( AuthenticationDTO auth, CompanyDTO dto ) throws ApplicationException;

    void setLogo( AuthenticationDTO auth, MediaDTO media ) throws ApplicationException;
}
