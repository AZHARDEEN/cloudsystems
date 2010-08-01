package br.com.mcampos.ejb.cloudsystem.account.mask.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Remote;


@Remote
public interface AccountingMaskFacade extends Serializable
{
    void set( AuthenticationDTO auth, String mask ) throws ApplicationException;

    String get( AuthenticationDTO auth ) throws ApplicationException;
}
