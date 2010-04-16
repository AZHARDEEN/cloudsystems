package br.com.mcampos.ejb.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.ejb.cloudsystem.security.util.MenuInterface;
import br.com.mcampos.ejb.core.AccessLogTypeInterface;
import br.com.mcampos.ejb.core.TaskInterface;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Remote;

@Remote
public interface SystemFacade extends MenuInterface, TaskInterface, AccessLogTypeInterface
{
    RoleDTO getRootRole ( AuthenticationDTO auth ) throws ApplicationException;
}
