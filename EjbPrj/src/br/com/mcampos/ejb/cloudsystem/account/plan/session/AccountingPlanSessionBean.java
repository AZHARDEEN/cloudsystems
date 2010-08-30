package br.com.mcampos.ejb.cloudsystem.account.plan.session;


import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlan;
import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlanPK;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AccountingPlanSession", mappedName = "CloudSystems-EjbPrj-AccountingPlanSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class AccountingPlanSessionBean extends Crud<AccountingPlanPK, AccountingPlan> implements AccountingPlanSessionLocal
{
    public void delete( Login login, AccountingPlanPK key ) throws ApplicationException
    {
        delete( AccountingPlan.class, key );
    }

    public void delete( Login login, AccountingMask mask, String accNumber ) throws ApplicationException
    {
        AccountingPlan accPlan = get( mask, accNumber );
        if ( accPlan != null )
            delete( accPlan );
    }

    public AccountingPlan get( AccountingPlanPK key ) throws ApplicationException
    {
        return get( AccountingPlan.class, key );
    }

    public AccountingPlan get( AccountingMask mask, String accNumber ) throws ApplicationException
    {
        AccountingPlanPK key = new AccountingPlanPK( mask, accNumber );
        return get( key );
    }

    public List<AccountingPlan> getAll( AccountingMask mask ) throws ApplicationException
    {
        return ( List<AccountingPlan> )getResultList( AccountingPlan.getAll, mask );
    }

    public AccountingPlan add( Login login, AccountingPlan entity ) throws ApplicationException
    {
        return add( entity );
    }
}
