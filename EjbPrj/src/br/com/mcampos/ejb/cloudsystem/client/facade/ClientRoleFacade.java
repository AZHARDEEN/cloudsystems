package br.com.mcampos.ejb.cloudsystem.client.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface ClientRoleFacade extends Serializable
{
    List<RoleDTO> getAvailableRoles( AuthenticationDTO auth ) throws ApplicationException;

    List<RoleDTO> getRoles( AuthenticationDTO auth, Integer userId ) throws ApplicationException;

    void add( AuthenticationDTO auth, Integer userId, List<RoleDTO> roles ) throws ApplicationException;

    void delete( AuthenticationDTO auth, Integer userId, List<RoleDTO> roles ) throws ApplicationException;

    List<ListUserDTO> getClients( AuthenticationDTO auth ) throws ApplicationException;
}
