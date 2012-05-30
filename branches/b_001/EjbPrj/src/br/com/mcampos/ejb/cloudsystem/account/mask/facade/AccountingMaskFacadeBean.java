package br.com.mcampos.ejb.cloudsystem.account.mask.facade;


import br.com.mcampos.dto.accounting.AccountingMaskDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.account.mask.AccountingMaskUtil;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.account.util.AccountingAuthUser;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AccountingMaskFacade", mappedName = "CloudSystems-EjbPrj-AccountingMaskFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AccountingMaskFacadeBean extends AccountingAuthUser implements AccountingMaskFacade
{
    public static final Integer messageId = 41;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        load( auth );
        maskSession.delete( getLogin(), getCompany(), id );
    }

    public AccountingMaskDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        AccountingMask mask = maskSession.get( companySession.get( auth.getCurrentCompany() ), id );
        return AccountingMaskUtil.copy( mask );
    }

    public AccountingMaskDTO add( AuthenticationDTO auth, AccountingMaskDTO dto ) throws ApplicationException
    {
        load( auth );
        AccountingMask mask = maskSession.get( getCompany(), dto.getId() );
        if ( mask != null )
            throwException( 1 );
        mask = AccountingMaskUtil.createEntity( getCompany(), dto );
        mask = maskSession.add( getLogin(), mask );
        return AccountingMaskUtil.copy( mask );
    }

    public AccountingMaskDTO update( AuthenticationDTO auth, AccountingMaskDTO dto ) throws ApplicationException
    {
        load( auth );
        AccountingMask mask = maskSession.get( getCompany(), dto.getId() );
        if ( mask == null )
            throwException( 2 );
        mask = AccountingMaskUtil.update( mask, dto );
        mask = maskSession.update( mask );
        return AccountingMaskUtil.copy( mask );
    }

    public List<AccountingMaskDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
    {
        load( auth );
        return AccountingMaskUtil.toDTOList( maskSession.getAll( getCompany() ) );
    }

    public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
    {
        load( auth );
        return maskSession.nextId( getCompany() );
    }
}
