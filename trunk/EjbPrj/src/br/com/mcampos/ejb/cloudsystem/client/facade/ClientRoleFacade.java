package br.com.mcampos.ejb.cloudsystem.client.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ClientRoleFacade extends Serializable
{
    List<RoleDTO> getAvailableRoles( AuthenticationDTO auth ) throws ApplicationException;

    List<RoleDTO> getRoles( AuthenticationDTO auth, CompanyDTO companyDto ) throws ApplicationException;

    void add( AuthenticationDTO auth, CompanyDTO companyDto, List<RoleDTO> roles ) throws ApplicationException;

    void delete( AuthenticationDTO auth, CompanyDTO companyDto, List<RoleDTO> roles ) throws ApplicationException;
}
