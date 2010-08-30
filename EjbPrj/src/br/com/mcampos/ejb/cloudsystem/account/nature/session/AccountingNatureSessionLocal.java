package br.com.mcampos.ejb.cloudsystem.account.nature.session;


import br.com.mcampos.ejb.cloudsystem.account.nature.entity.AccountingNature;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AccountingNatureSessionLocal extends Serializable
{

    void delete( String key ) throws ApplicationException;

    AccountingNature get( String key ) throws ApplicationException;

    List<AccountingNature> getAll() throws ApplicationException;

    AccountingNature add( AccountingNature entity ) throws ApplicationException;

    AccountingNature update( AccountingNature entity ) throws ApplicationException;
}
