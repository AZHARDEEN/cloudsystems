package br.com.mcampos.ejb.cloudsystem.user.attribute.gender.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface GenderSessionLocal extends Serializable
{
    Gender add( Gender entity ) throws ApplicationException;

    Gender update( Gender entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    Gender get( Integer key ) throws ApplicationException;

    List<Gender> getAll() throws ApplicationException;

    Integer getNextId() throws ApplicationException;
}
