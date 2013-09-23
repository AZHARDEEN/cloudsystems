package br.com.mcampos.ejb.cloudsystem.security.role;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.security.task.Task;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface RoleSessionLocal extends Serializable
{
    Role add( Role entity ) throws ApplicationException;

    Role update( Role entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    Role get( Integer key ) throws ApplicationException;

    List<Role> getAll() throws ApplicationException;

    Role getRootRole() throws ApplicationException;

    Object getSingleResult( String namedQuery ) throws ApplicationException;

    Integer getMaxId() throws ApplicationException;

    List<Role> getChildRoles( Role role ) throws ApplicationException;

    List<Task> getTasks( Integer key ) throws ApplicationException;

    List<Role> getDefaultRoles() throws ApplicationException;
}
