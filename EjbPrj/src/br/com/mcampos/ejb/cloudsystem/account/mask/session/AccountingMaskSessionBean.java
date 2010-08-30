package br.com.mcampos.ejb.cloudsystem.account.mask.session;


import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMaskPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AccountingMaskSession", mappedName = "CloudSystems-EjbPrj-AccountingMaskSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AccountingMaskSessionBean extends Crud<AccountingMaskPK, AccountingMask> implements AccountingMaskSessionLocal
{
    public void delete( Login login, AccountingMaskPK key ) throws ApplicationException
    {
        delete( AccountingMask.class, key );
    }

    public void delete( Login login, Company company, Integer id ) throws ApplicationException
    {
        delete( login, new AccountingMaskPK( id, company.getId() ) );
    }

    public AccountingMask get( AccountingMaskPK key ) throws ApplicationException
    {
        return get( AccountingMask.class, key );
    }

    public AccountingMask get( Company company, Integer id ) throws ApplicationException
    {
        return get( new AccountingMaskPK( id, company.getId() ) );
    }

    public List<AccountingMask> getAll( Company company ) throws ApplicationException
    {
        return ( List<AccountingMask> )getResultList( AccountingMask.getAll, company );
    }

    public AccountingMask add( Login login, AccountingMask entity ) throws ApplicationException
    {
        return add( entity );
    }

    public Integer nextId( Company company ) throws ApplicationException
    {
        return nextIntegerId( AccountingMask.nextId, company );
    }
}
