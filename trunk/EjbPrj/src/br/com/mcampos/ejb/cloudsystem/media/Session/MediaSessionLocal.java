package br.com.mcampos.ejb.cloudsystem.media.Session;


import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface MediaSessionLocal extends Serializable
{
    public Media add( Media entity ) throws ApplicationException;

    public void delete( Integer key ) throws ApplicationException;

    public Media get( Integer key ) throws ApplicationException;

    byte[] getObject( Integer key ) throws ApplicationException;

    List<Media> getAll( String namedQuery ) throws ApplicationException;
}
