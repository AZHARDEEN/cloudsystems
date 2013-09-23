package br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CompanyPositionFacade extends Serializable
{
    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    CompanyPositionDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    CompanyPositionDTO add( AuthenticationDTO auth, CompanyPositionDTO dto ) throws ApplicationException;

    CompanyPositionDTO update( AuthenticationDTO auth, CompanyPositionDTO dto ) throws ApplicationException;

    List<CompanyPositionDTO> getAll() throws ApplicationException;
}
