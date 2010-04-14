package br.com.mcampos.ejb.cloudsystem.security.session;


import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Local;

@Local
public interface PermissionAssignmentSessionLocal
{
    void add (Role role, Task task ) throws ApplicationException;
}
