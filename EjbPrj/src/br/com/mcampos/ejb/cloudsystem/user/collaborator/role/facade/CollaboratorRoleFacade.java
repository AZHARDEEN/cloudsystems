package br.com.mcampos.ejb.cloudsystem.user.collaborator.role.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.user.collaborator.CollaboratorDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface CollaboratorRoleFacade extends Serializable
{
    List<RoleDTO> getAvailableRoles( AuthenticationDTO auth ) throws ApplicationException;

    List<RoleDTO> getRoles( AuthenticationDTO auth, CollaboratorDTO dto ) throws ApplicationException;

    void add( AuthenticationDTO auth, CollaboratorDTO dto, List<RoleDTO> roles ) throws ApplicationException;

    void delete( AuthenticationDTO auth, CollaboratorDTO dto, List<RoleDTO> roles ) throws ApplicationException;
}
