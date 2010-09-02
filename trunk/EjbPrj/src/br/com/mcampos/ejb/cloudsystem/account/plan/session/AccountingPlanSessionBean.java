package br.com.mcampos.ejb.cloudsystem.account.plan.session;


import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.account.plan.AccountingPlanUtil;
import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlan;
import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlanPK;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

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
        if ( getLevel( entity ) > 1 )
            entity.setParent( getParent( entity ) );
        if ( AccountingPlanUtil.validateNumberMask( entity ) == false )
            throw new IllegalArgumentException( "Accounting number mask is invalid" );
        return add( entity );
    }

    private int getLevel( AccountingPlan accNumber )
    {
        String number = accNumber.getNumber();

        String[] parts = number.split( "\\." );
        if ( parts == null || parts.length == 0 )
            return 1;
        else
            return parts.length;
    }

    private AccountingPlan getParent( AccountingPlan accNumber ) throws ApplicationException
    {
        String number = AccountingPlanUtil.getParent( accNumber.getNumber() );
        if ( SysUtils.isEmpty( number ) )
            return null;
        AccountingPlan parent = get( accNumber.getMask(), number );
        if ( parent == null )
            throw new IllegalArgumentException( "Accounting number has no parent." );
        return parent;
    }

}
