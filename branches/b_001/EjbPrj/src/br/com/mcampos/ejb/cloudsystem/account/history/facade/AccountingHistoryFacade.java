package br.com.mcampos.ejb.cloudsystem.account.history.facade;


import br.com.mcampos.dto.accounting.AccountingHistoryDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface AccountingHistoryFacade extends Serializable
{
    void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    AccountingHistoryDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException;

    AccountingHistoryDTO add( AuthenticationDTO auth, AccountingHistoryDTO dto ) throws ApplicationException;

    AccountingHistoryDTO update( AuthenticationDTO auth, AccountingHistoryDTO dto ) throws ApplicationException;

    List<AccountingHistoryDTO> getAll( AuthenticationDTO auth ) throws ApplicationException;

    Integer nextId( AuthenticationDTO auth ) throws ApplicationException;
}
