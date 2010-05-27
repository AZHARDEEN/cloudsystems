package br.com.mcampos.ejb.cloudsystem.security.task;


import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface TaskSessionLocal extends Serializable
{
    void delete( Integer key ) throws ApplicationException;

    Task get( Integer key ) throws ApplicationException;

    List<Task> getAll() throws ApplicationException;

    List<Task> getRoots() throws ApplicationException;

    Task add( Task entity ) throws ApplicationException;

    void add( Task masterTask, Task entity ) throws ApplicationException;

    Task update( Task entity ) throws ApplicationException;

    Integer getNextTaskId() throws ApplicationException;
}
