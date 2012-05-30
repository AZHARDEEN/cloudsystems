package br.com.mcampos.ejb.cloudsystem.account.event.session;


import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountEvent;
import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountEventPK;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AccountingEventSession", mappedName = "CloudSystems-EjbPrj-AccountingEventSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AccountingEventSessionBean extends Crud<AccountEventPK, AccountEvent> implements AccountingEventSessionLocal
{
    public void delete( Login login, AccountEventPK key ) throws ApplicationException
    {
        delete( AccountEvent.class, key );
    }

    public void delete( Login login, AccountingMask owner, Integer id ) throws ApplicationException
    {
        delete( login, new AccountEventPK( owner, id ) );
    }

    public AccountEvent get( AccountEventPK key ) throws ApplicationException
    {
        return get( AccountEvent.class, key );
    }

    public AccountEvent get( AccountingMask owner, Integer id ) throws ApplicationException
    {
        return get( new AccountEventPK( owner, id ) );
    }

    public List<AccountEvent> getAll( AccountingMask owner ) throws ApplicationException
    {
        return ( List<AccountEvent> )getResultList( AccountEvent.getAll, owner );
    }

    public AccountEvent add( Login login, AccountEvent entity ) throws ApplicationException
    {
        return add( entity );
    }

    public Integer nextId( AccountingMask owner ) throws ApplicationException
    {
        return nextIntegerId( AccountEvent.nextId, owner );
    }
}
