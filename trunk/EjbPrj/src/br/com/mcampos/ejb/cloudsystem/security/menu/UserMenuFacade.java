package br.com.mcampos.ejb.cloudsystem.security.menu;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface UserMenuFacade extends Serializable
{
    List<MenuDTO> getMenus( AuthenticationDTO auth ) throws ApplicationException;

    String getInitialPage( AuthenticationDTO auth ) throws ApplicationException;
}
