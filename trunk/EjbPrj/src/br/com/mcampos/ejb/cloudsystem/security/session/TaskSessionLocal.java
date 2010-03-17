package br.com.mcampos.ejb.cloudsystem.security.session;

import br.com.mcampos.ejb.cloudsystem.security.entity.Task;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;

@Local
public interface TaskSessionLocal
{
    void delete( Integer key ) throws ApplicationException;

    Task get( Integer key ) throws ApplicationException;

    List<Task> getAll() throws ApplicationException;

    List<Task> getRoots() throws ApplicationException;

    List<Task> getSubtasks( Task task ) throws ApplicationException;
}
