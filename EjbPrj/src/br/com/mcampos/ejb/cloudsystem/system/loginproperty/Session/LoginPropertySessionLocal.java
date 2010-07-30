package br.com.mcampos.ejb.cloudsystem.system.loginproperty.Session;


import br.com.mcampos.ejb.cloudsystem.system.loginproperty.entity.LoginProperty;
import br.com.mcampos.ejb.cloudsystem.system.loginproperty.entity.LoginPropertyPK;
import br.com.mcampos.ejb.cloudsystem.system.systemuserproperty.entity.SystemUserProperty;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface LoginPropertySessionLocal extends Serializable
{
    LoginProperty get( LoginPropertyPK key ) throws ApplicationException;

    List<LoginProperty> getAll( Collaborator login ) throws ApplicationException;

    LoginProperty get( Collaborator login, SystemUserProperty property ) throws ApplicationException;

    LoginProperty add( LoginProperty entity ) throws ApplicationException;
}
