package br.com.mcampos.ejb.cloudsystem.account.mask.facade;


import br.com.mcampos.dto.accounting.AccountingMaskDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AccountingMaskFacade extends Serializable
{
    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    AccountingMaskDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    AccountingMaskDTO add( AuthenticationDTO auth, AccountingMaskDTO dto ) throws ApplicationException;

    AccountingMaskDTO update( AuthenticationDTO auth, AccountingMaskDTO dto ) throws ApplicationException;

    List<AccountingMaskDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;

    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;
}
