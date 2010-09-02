package br.com.mcampos.ejb.cloudsystem.account.event.session;


import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountEvent;
import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountEventPK;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AccountingEventSessionLocal extends Serializable
{
    void delete( Login login, AccountEventPK key ) throws ApplicationException;

    void delete( Login login, AccountingMask owner, Integer id ) throws ApplicationException;

    AccountEvent get( AccountEventPK key ) throws ApplicationException;

    AccountEvent get( AccountingMask owner, Integer id ) throws ApplicationException;

    List<AccountEvent> getAll( AccountingMask owner ) throws ApplicationException;

    AccountEvent add( Login login, AccountEvent entity ) throws ApplicationException;

    AccountEvent update( AccountEvent entity ) throws ApplicationException;

    Integer nextId( AccountingMask owner ) throws ApplicationException;
}
