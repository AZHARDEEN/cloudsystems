package br.com.mcampos.ejb.cloudsystem.security.session;


import br.com.mcampos.ejb.cloudsystem.security.entity.Menu;
import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.ejb.cloudsystem.security.entity.TaskMenu;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Local;

@Local
public interface TaskMenuSessionLocal
{
    TaskMenu add( Menu menu, Task task ) throws ApplicationException;

    void delete( Menu menu, Task task ) throws ApplicationException;

    TaskMenu get( Menu menu, Task task ) throws ApplicationException;
}
