package br.com.mcampos.ejb.cloudsystem.security.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.security.RoleDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface SecurityFacade
{
    List<RoleDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;
}
