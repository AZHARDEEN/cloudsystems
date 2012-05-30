package br.com.mcampos.ejb.cloudsystem.user.attribute.companytype;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CompanyTypeFacade extends Serializable
{
    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    CompanyTypeDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    CompanyTypeDTO add( AuthenticationDTO auth, CompanyTypeDTO dto ) throws ApplicationException;

    CompanyTypeDTO update( AuthenticationDTO auth, CompanyTypeDTO dto ) throws ApplicationException;

    List<CompanyTypeDTO> getAll() throws ApplicationException;
}
