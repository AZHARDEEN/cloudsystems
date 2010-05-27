package br.com.mcampos.ejb.cloudsystem.security.taskmenu;


import br.com.mcampos.ejb.cloudsystem.security.menu.Menu;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface TaskMenuSessionLocal extends Serializable
{
    TaskMenu add( Menu menu, Task task ) throws ApplicationException;

    void delete( Menu menu, Task task ) throws ApplicationException;

    TaskMenu get( Menu menu, Task task ) throws ApplicationException;

    List<TaskMenu> getAll( Menu menu ) throws ApplicationException;
}
