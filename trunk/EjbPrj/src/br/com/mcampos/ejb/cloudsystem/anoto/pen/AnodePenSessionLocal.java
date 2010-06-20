package br.com.mcampos.ejb.cloudsystem.anoto.pen;


import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AnodePenSessionLocal extends Serializable
{
    public AnotoPen add( AnotoPen entity, Person person ) throws ApplicationException;

    public void delete( String key ) throws ApplicationException;

    public AnotoPen get( String key ) throws ApplicationException;

    public List<AnotoPen> getAll() throws ApplicationException;

    public AnotoPen update( AnotoPen entity ) throws ApplicationException;
}
