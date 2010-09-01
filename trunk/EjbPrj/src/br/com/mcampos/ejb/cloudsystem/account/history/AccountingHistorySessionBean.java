package br.com.mcampos.ejb.cloudsystem.account.history;


import br.com.mcampos.ejb.cloudsystem.account.history.entity.AccountingHistory;
import br.com.mcampos.ejb.cloudsystem.account.history.entity.AccountingHistoryPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AccountingHistorySession", mappedName = "CloudSystems-EjbPrj-AccountingHistorySession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AccountingHistorySessionBean extends Crud<AccountingHistoryPK, AccountingHistory> implements AccountingHistorySessionLocal
{
    public void delete( Login login, AccountingHistoryPK key ) throws ApplicationException
    {
        delete( AccountingHistory.class, key );
    }

    public void delete( Login login, Company company, Integer id ) throws ApplicationException
    {
        delete( login, new AccountingHistoryPK( id, company.getId() ) );
    }

    public AccountingHistory get( AccountingHistoryPK key ) throws ApplicationException
    {
        return get( AccountingHistory.class, key );
    }

    public AccountingHistory get( Company company, Integer id ) throws ApplicationException
    {
        return get( new AccountingHistoryPK( id, company.getId() ) );
    }

    public List<AccountingHistory> getAll( Company company ) throws ApplicationException
    {
        return ( List<AccountingHistory> )getResultList( AccountingHistory.getAll, company );
    }

    public AccountingHistory add( Login login, AccountingHistory entity ) throws ApplicationException
    {
        return add( entity );
    }

    public Integer nextId( Company company ) throws ApplicationException
    {
        return nextIntegerId( AccountingHistory.nextId, company );
    }

}
