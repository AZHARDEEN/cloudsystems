package br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.session;


import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.entity.SystemUserProperty;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface SystemUserPropertySessionLocal extends Serializable
{
    void delete( Integer key ) throws ApplicationException;

    SystemUserProperty get( Integer key ) throws ApplicationException;

    List<SystemUserProperty> getAll() throws ApplicationException;

    Integer nextIntegerId() throws ApplicationException;

    SystemUserProperty add( SystemUserProperty entity ) throws ApplicationException;

    SystemUserProperty update( SystemUserProperty entity ) throws ApplicationException;
}
