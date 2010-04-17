package br.com.mcampos.ejb.cloudsystem.security.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public interface RoleInterface
{
    List<RoleDTO> getRoles( AuthenticationDTO auth ) throws ApplicationException;

    RoleDTO getRootRole( AuthenticationDTO auth ) throws ApplicationException;

    List<RoleDTO> getChildRoles( AuthenticationDTO auth, RoleDTO parent ) throws ApplicationException;

    RoleDTO add( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException;

    RoleDTO update( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException;

    Integer getRoleMaxId( AuthenticationDTO auth ) throws ApplicationException;

    void delete( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException;

    List<TaskDTO> getTasks( AuthenticationDTO auth, RoleDTO dto ) throws ApplicationException;

    List<MenuDTO> getMenus ( AuthenticationDTO auth, RoleDTO dto )throws ApplicationException;
}
