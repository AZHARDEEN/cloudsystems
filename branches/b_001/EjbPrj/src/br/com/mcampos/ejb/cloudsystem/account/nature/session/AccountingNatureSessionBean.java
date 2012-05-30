package br.com.mcampos.ejb.cloudsystem.account.nature.session;


import br.com.mcampos.ejb.cloudsystem.account.nature.entity.AccountingNature;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AccountingNatureSession", mappedName = "CloudSystems-EjbPrj-AccountingNatureSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AccountingNatureSessionBean extends Crud<String, AccountingNature> implements AccountingNatureSessionLocal
{
    public void delete( String key ) throws ApplicationException
    {
        delete( AccountingNature.class, key );
    }

    public AccountingNature get( String key ) throws ApplicationException
    {
        return get( AccountingNature.class, key );
    }

    public List<AccountingNature> getAll() throws ApplicationException
    {
        return ( List<AccountingNature> )getResultList( AccountingNature.getAll );
    }
}
