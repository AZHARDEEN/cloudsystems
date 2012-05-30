package br.com.mcampos.ejb.cloudsystem.account.event.facade;


import br.com.mcampos.dto.accounting.AccountingEventDTO;
import br.com.mcampos.dto.accounting.AccountingPlanDTO;
import br.com.mcampos.dto.accounting.AccountingRateTypeDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.account.event.AccountingEventUtil;
import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountEvent;
import br.com.mcampos.ejb.cloudsystem.account.event.session.AccountingEventSessionLocal;
import br.com.mcampos.ejb.cloudsystem.account.event.session.AccountingRateTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.account.plan.AccountingPlanUtil;
import br.com.mcampos.ejb.cloudsystem.account.plan.AccountingRateTypeUtil;
import br.com.mcampos.ejb.cloudsystem.account.plan.session.AccountingPlanSessionLocal;
import br.com.mcampos.ejb.cloudsystem.account.util.AccountingAuthUser;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AccountingEventFacade", mappedName = "CloudSystems-EjbPrj-AccountingEventFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AccountingEventFacadeBean extends AccountingAuthUser implements AccountingEventFacade
{
    public static final Integer messageId = 44;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private AccountingEventSessionLocal session;

    @EJB
    private AccountingRateTypeSessionLocal rateTypeSession;

    @EJB
    AccountingPlanSessionLocal accNumberSession;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public void delete( AuthenticationDTO auth, Integer maskId, Integer eventId ) throws ApplicationException
    {
        load( auth, maskId );
        session.delete( getLogin(), getMask(), eventId );

    }

    public AccountingEventDTO get( AuthenticationDTO auth, Integer maskId, Integer eventId ) throws ApplicationException
    {
        load( auth, maskId );
        return AccountingEventUtil.copy( session.get( getMask(), eventId ) );
    }

    public AccountingEventDTO add( AuthenticationDTO auth, AccountingEventDTO dto ) throws ApplicationException
    {
        load( auth, dto.getMask().getId() );
        AccountEvent accNumber = session.get( getMask(), dto.getId() );
        if ( accNumber != null )
            throwException( 2 );
        accNumber = AccountingEventUtil.createEntity( getMask(), dto );
        accNumber = session.add( getLogin(), accNumber );
        return AccountingEventUtil.copy( accNumber );
    }

    public AccountingEventDTO update( AuthenticationDTO auth, AccountingEventDTO dto ) throws ApplicationException
    {
        load( auth, dto.getMask().getId() );
        AccountEvent accNumber = session.get( getMask(), dto.getId() );
        if ( accNumber == null )
            throwException( 3 );
        accNumber = AccountingEventUtil.update( accNumber, dto );
        accNumber = session.update( accNumber );
        return AccountingEventUtil.copy( accNumber );
    }

    public List<AccountingEventDTO> getAll( AuthenticationDTO auth, Integer maskId ) throws ApplicationException
    {
        load( auth, maskId );
        return AccountingEventUtil.toDTOList( session.getAll( getMask() ) );
    }

    public Integer nextId( AuthenticationDTO auth, Integer maskId ) throws ApplicationException
    {
        load( auth, maskId );
        return session.nextId( getMask() );
    }

    public List<AccountingRateTypeDTO> getRateTypes() throws ApplicationException
    {
        return AccountingRateTypeUtil.toDTOList( rateTypeSession.getAll() );
    }

    public List<AccountingPlanDTO> getAccountNumbers( AuthenticationDTO auth, Integer maskId ) throws ApplicationException
    {
        load( auth, maskId );
        return AccountingPlanUtil.toDTOList( accNumberSession.getAll( getMask() ) );
    }

}
