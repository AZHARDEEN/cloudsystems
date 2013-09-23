package br.com.mcampos.ejb.cloudsystem.user.attribute.title.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.entity.Title;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface TitleSessionLocal extends Serializable
{
    Title add( Title entity ) throws ApplicationException;

    Title update( Title entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    Title get( Integer key ) throws ApplicationException;

    List<Title> getAll() throws ApplicationException;

    List<Title> getAll( Gender gender ) throws ApplicationException;

    Integer getNextId() throws ApplicationException;

}
