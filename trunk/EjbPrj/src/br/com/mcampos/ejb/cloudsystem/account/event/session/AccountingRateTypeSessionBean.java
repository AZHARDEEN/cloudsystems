package br.com.mcampos.ejb.cloudsystem.account.event.session;


import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountingRateType;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AccountingRateTypeSession", mappedName = "CloudSystems-EjbPrj-AccountingRateTypeSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AccountingRateTypeSessionBean extends Crud<Integer, AccountingRateType> implements AccountingRateTypeSessionLocal
{
    public void delete( Integer key ) throws ApplicationException
    {
        delete( AccountingRateType.class, key );
    }

    public AccountingRateType get( Integer key ) throws ApplicationException
    {
        return get( AccountingRateType.class, key );
    }

    public List<AccountingRateType> getAll() throws ApplicationException
    {
        return getAll( AccountingRateType.getAll );
    }

    public Integer nextIntegerId() throws ApplicationException
    {
        return nextIntegerId( AccountingRateType.nextId );
    }
}
