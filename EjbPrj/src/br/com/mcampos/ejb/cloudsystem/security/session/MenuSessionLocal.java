package br.com.mcampos.ejb.cloudsystem.security.session;


import br.com.mcampos.ejb.cloudsystem.security.entity.Menu;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface MenuSessionLocal
{
    Menu add( Menu entity ) throws ApplicationException;

    Menu update( Menu entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    Menu get( Integer key ) throws ApplicationException;

    List<Menu> getAll() throws ApplicationException;

    Integer getNextSequence( int parentId ) throws ApplicationException;
}
