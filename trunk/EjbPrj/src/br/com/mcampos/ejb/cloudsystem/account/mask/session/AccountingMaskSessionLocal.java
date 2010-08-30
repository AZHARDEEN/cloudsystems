package br.com.mcampos.ejb.cloudsystem.account.mask.session;


import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMaskPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AccountingMaskSessionLocal extends Serializable
{
    void delete( Login login, AccountingMaskPK key ) throws ApplicationException;

    void delete( Login login, Company company, Integer id ) throws ApplicationException;

    AccountingMask get( AccountingMaskPK key ) throws ApplicationException;

    AccountingMask get( Company company, Integer id ) throws ApplicationException;

    List<AccountingMask> getAll( Company company ) throws ApplicationException;

    AccountingMask add( Login login, AccountingMask entity ) throws ApplicationException;

    AccountingMask update( AccountingMask entity ) throws ApplicationException;

    Integer nextId( Company company ) throws ApplicationException;
}
