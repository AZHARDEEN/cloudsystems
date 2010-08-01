package br.com.mcampos.ejb.cloudsystem.account.mask.session;


import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface AccountingMaskSessionLocal extends Serializable
{
    void set( Company company, String mask ) throws ApplicationException;

    AccountingMask get( Company company ) throws ApplicationException;
}
