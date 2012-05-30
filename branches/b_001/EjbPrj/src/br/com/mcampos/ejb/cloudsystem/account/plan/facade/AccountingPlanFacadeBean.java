package br.com.mcampos.ejb.cloudsystem.account.plan.facade;


import br.com.mcampos.dto.accounting.AccountingPlanDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.account.nature.entity.AccountingNature;
import br.com.mcampos.ejb.cloudsystem.account.plan.AccountingPlanUtil;
import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlan;
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


@Stateless( name = "AccountingPlanFacade", mappedName = "CloudSystems-EjbPrj-AccountingPlanFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AccountingPlanFacadeBean extends AccountingAuthUser implements AccountingPlanFacade
{
    public static final Integer messageId = 42;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    AccountingPlanSessionLocal session;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public void delete( AuthenticationDTO auth, Integer maskId, String accNumber ) throws ApplicationException
    {
        load( auth, maskId );
        session.delete( getLogin(), getMask(), accNumber );

    }

    public AccountingPlanDTO get( AuthenticationDTO auth, Integer maskId, String accNumber ) throws ApplicationException
    {
        load( auth, maskId );
        return AccountingPlanUtil.copy( session.get( getMask(), accNumber ) );
    }

    public AccountingPlanDTO add( AuthenticationDTO auth, AccountingPlanDTO dto ) throws ApplicationException
    {
        load( auth, dto.getMask().getId() );
        AccountingPlan accNumber = session.get( getMask(), dto.getNumber() );
        if ( accNumber != null )
            throwException( 2 );
        AccountingNature nature = natureSession.get( dto.getNature().getId() );
        accNumber = AccountingPlanUtil.createEntity( getMask(), dto, nature );
        accNumber = session.add( getLogin(), accNumber );
        return AccountingPlanUtil.copy( accNumber );
    }

    public AccountingPlanDTO update( AuthenticationDTO auth, AccountingPlanDTO dto ) throws ApplicationException
    {
        load( auth, dto.getMask().getId() );
        AccountingPlan accNumber = session.get( getMask(), dto.getNumber() );
        if ( accNumber == null )
            throwException( 3 );
        AccountingNature nature = natureSession.get( dto.getNature().getId() );
        accNumber = AccountingPlanUtil.update( accNumber, dto, nature );
        accNumber = session.update( accNumber );
        return AccountingPlanUtil.copy( accNumber );
    }

    public List<AccountingPlanDTO> getAll( AuthenticationDTO auth, Integer maskId ) throws ApplicationException
    {
        load( auth, maskId );
        return AccountingPlanUtil.toDTOList( session.getAll( getMask() ) );
    }
}
