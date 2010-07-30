package br.com.mcampos.ejb.cloudsystem.system.loginproperty.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.LoginPropertyDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface LoginPropertyFacade extends Serializable
{
    List<LoginPropertyDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;

    List<MenuDTO> getMenus( AuthenticationDTO auth ) throws ApplicationException;

    void update( AuthenticationDTO auth, Integer propertyId, String value ) throws ApplicationException;
}
