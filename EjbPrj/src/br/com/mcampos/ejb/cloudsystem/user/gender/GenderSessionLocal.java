package br.com.mcampos.ejb.cloudsystem.user.gender;


import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface GenderSessionLocal
{
    Gender add( Gender entity ) throws ApplicationException;

    Gender update( Gender entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    Gender get( Integer key ) throws ApplicationException;

    List<Gender> getAll() throws ApplicationException;
}
