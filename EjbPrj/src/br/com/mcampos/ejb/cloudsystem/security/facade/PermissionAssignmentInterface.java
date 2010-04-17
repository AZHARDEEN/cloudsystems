package br.com.mcampos.ejb.cloudsystem.security.facade;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.exception.ApplicationException;

public interface PermissionAssignmentInterface
{

    void add ( AuthenticationDTO auth, TaskDTO dtoTask, RoleDTO roleDTO ) throws ApplicationException;

    void remove ( AuthenticationDTO auth, TaskDTO dtoTask, RoleDTO roleDTO ) throws ApplicationException;

}
