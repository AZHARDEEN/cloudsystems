package br.com.mcampos.ejb.cloudsystem.account.history.facade;


import br.com.mcampos.dto.accounting.AccountingHistoryDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.account.history.AccountingHistorySessionLocal;
import br.com.mcampos.ejb.cloudsystem.account.history.AccountingHistoryUtil;
import br.com.mcampos.ejb.cloudsystem.account.history.entity.AccountingHistory;
import br.com.mcampos.ejb.cloudsystem.account.util.AccountingAuthUser;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AccountingHistoryFacade", mappedName = "CloudSystems-EjbPrj-AccountingHistoryFacade" )
@Remote
public class AccountingHistoryFacadeBean extends AccountingAuthUser implements AccountingHistoryFacade
{
    public static final Integer messageId = 44;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    protected AccountingHistorySessionLocal session;


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
        session.delete( getLogin(), getCompany(), id );
    }

    public AccountingHistoryDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
    {
        load( auth );
        return AccountingHistoryUtil.copy( session.get( getCompany(), id ) );
    }

    public AccountingHistoryDTO add( AuthenticationDTO auth, AccountingHistoryDTO dto ) throws ApplicationException
    {
        load( auth );
        AccountingHistory ca = session.get( getCompany(), dto.getId() );
        if ( ca != null )
            throwException( 1 );
        ca = AccountingHistoryUtil.createEntity( getCompany(), dto );
        ca = session.add( getLogin(), ca );
        return AccountingHistoryUtil.copy( ca );
    }

    public AccountingHistoryDTO update( AuthenticationDTO auth, AccountingHistoryDTO dto ) throws ApplicationException
    {
        load( auth );
        AccountingHistory ca = session.get( getCompany(), dto.getId() );
        if ( ca == null )
            throwException( 2 );
        ca = AccountingHistoryUtil.update( ca, dto );
        ca = session.update( ca );
        return AccountingHistoryUtil.copy( ca );
    }

    public List<AccountingHistoryDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
    {
        load( auth );
        return AccountingHistoryUtil.toDTOList( session.getAll( getCompany() ) );
    }

    public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
    {
        load( auth );
        return session.nextId( getCompany() );
    }
}
