package br.com.mcampos.ejb.cloudsystem.media.Session;

import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;

@Local
public interface MediaSessionLocal
{
    public Media add( Media entity );

    public void delete( Integer key );

    public Media get( Integer key );

    byte[] getObject( Integer key );

    List<Media> getAll( String namedQuery );
}
