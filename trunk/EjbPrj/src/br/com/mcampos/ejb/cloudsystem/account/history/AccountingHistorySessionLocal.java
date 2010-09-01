package br.com.mcampos.ejb.cloudsystem.account.history;


import br.com.mcampos.ejb.cloudsystem.account.history.entity.AccountingHistory;
import br.com.mcampos.ejb.cloudsystem.account.history.entity.AccountingHistoryPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AccountingHistorySessionLocal extends Serializable
{
    void delete( Login login, AccountingHistoryPK key ) throws ApplicationException;

    void delete( Login login, Company company, Integer id ) throws ApplicationException;

    AccountingHistory get( AccountingHistoryPK key ) throws ApplicationException;

    AccountingHistory get( Company company, Integer id ) throws ApplicationException;

    List<AccountingHistory> getAll( Company company ) throws ApplicationException;

    AccountingHistory add( Login login, AccountingHistory entity ) throws ApplicationException;

    AccountingHistory update( AccountingHistory entity ) throws ApplicationException;

    Integer nextId( Company company ) throws ApplicationException;
}
