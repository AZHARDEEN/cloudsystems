package br.com.imstecnologia.ejb.sped.fiscal.cfop.session;


import br.com.imstecnologia.ejb.sped.fiscal.cfop.entity.Cfop;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface CfopSessionLocal extends Serializable
{
    void delete( Integer key ) throws ApplicationException;

    Cfop get( Integer key ) throws ApplicationException;

    List<Cfop> getAll( boolean bClosed ) throws ApplicationException;

    Cfop add( Cfop entity ) throws ApplicationException;

    Integer nextId() throws ApplicationException;
}
