package br.com.mcampos.ejb.cloudsystem.security.facade;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;

public interface TaskMenuInterface
{
    void add ( AuthenticationDTO auth, TaskDTO dtoTask, MenuDTO menuDTO ) throws ApplicationException;

    void remove ( AuthenticationDTO auth, TaskDTO dtoTask, MenuDTO menuDTO ) throws ApplicationException;

}
