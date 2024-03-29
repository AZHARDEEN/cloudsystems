package br.com.mcampos.ejb.cloudsystem.anoto.pen;


import br.com.mcampos.dto.ApplicationException;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface AnodePenSessionLocal extends Serializable
{
    public AnotoPen add( AnotoPen entity ) throws ApplicationException;

    public void delete( String key ) throws ApplicationException;

    public AnotoPen get( String key ) throws ApplicationException;

    public List<AnotoPen> getAll( Integer nStart, Integer nSize ) throws ApplicationException;

    public AnotoPen update( AnotoPen entity ) throws ApplicationException;

    public Integer count() throws ApplicationException;
}
