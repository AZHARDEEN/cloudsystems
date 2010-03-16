package br.com.mcampos.ejb.cloudsystem.security.session;


import br.com.mcampos.ejb.cloudsystem.security.entity.Role;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface RoleSessionLocal
{
    Role add( Role entity ) throws ApplicationException;

    Role update( Role entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    Role get( Integer key ) throws ApplicationException;

    List<Role> getAll( ) throws ApplicationException;

    Role getRootRole() throws ApplicationException;

    Object getSingleResult( String namedQuery ) throws ApplicationException;

    Integer nextIntegerId( String namedQuery ) throws ApplicationException;

    public List<Role> getChildRoles( Role role ) throws ApplicationException;

    Integer getMaxId ( );
}
