package br.com.mcampos.ejb.cloudsystem.account.event.session;


import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountingRateType;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AccountingRateTypeSessionLocal extends Serializable
{
    void delete( Integer key ) throws ApplicationException;

    AccountingRateType get( Integer key ) throws ApplicationException;

    List<AccountingRateType> getAll() throws ApplicationException;

    Integer nextIntegerId() throws ApplicationException;

    AccountingRateType add( AccountingRateType entity ) throws ApplicationException;

    AccountingRateType update( AccountingRateType entity ) throws ApplicationException;
}
