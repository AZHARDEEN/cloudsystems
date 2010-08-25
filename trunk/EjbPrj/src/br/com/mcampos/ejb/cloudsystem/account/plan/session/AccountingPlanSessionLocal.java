package br.com.mcampos.ejb.cloudsystem.account.plan.session;


import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlan;
import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlanPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface AccountingPlanSessionLocal extends Serializable
{
    void delete( Login login, AccountingPlanPK key ) throws ApplicationException;

    void delete( Login login, Company company, String accNumber ) throws ApplicationException;

    AccountingPlan get( AccountingPlanPK key ) throws ApplicationException;

    AccountingPlan get( Company company, String accNumber ) throws ApplicationException;

    List<AccountingPlan> getAll( Company company ) throws ApplicationException;

    AccountingPlan add( Login login, AccountingPlan entity ) throws ApplicationException;

    AccountingPlan update( AccountingPlan entity ) throws ApplicationException;
}
