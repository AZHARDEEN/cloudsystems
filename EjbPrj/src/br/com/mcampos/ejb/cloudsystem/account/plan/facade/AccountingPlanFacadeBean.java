package br.com.mcampos.ejb.cloudsystem.account.plan.facade;


import br.com.mcampos.dto.accounting.AccountingPlanDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.account.plan.AccountingPlanUtil;
import br.com.mcampos.ejb.cloudsystem.account.plan.entity.AccountingPlan;
import br.com.mcampos.ejb.cloudsystem.account.plan.session.AccountingPlanSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.cloudsystem.user.login.LoginSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
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
public class AccountingPlanFacadeBean extends AbstractSecurity implements AccountingPlanFacade
{
    public static final Integer messageId = 42;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    CompanySessionLocal companySession;

    @EJB
    LoginSessionLocal loginSession;

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

    private AuthUser getAuthUser( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        if ( company == null )
            throwException( 1 );
        Login login = loginSession.get( auth.getUserId() );
        return new AuthUser( company, login );
    }

    public void delete( AuthenticationDTO auth, String accNumber ) throws ApplicationException
    {
        AuthUser au = getAuthUser( auth );
        session.delete( au.getLogin(), au.getCompany(), accNumber );

    }

    public AccountingPlanDTO get( AuthenticationDTO auth, String accNumber ) throws ApplicationException
    {
        AuthUser au = getAuthUser( auth );
        return AccountingPlanUtil.copy( session.get( au.getCompany(), accNumber ) );
    }

    public AccountingPlanDTO add( AuthenticationDTO auth, AccountingPlanDTO dto ) throws ApplicationException
    {
        AuthUser au = getAuthUser( auth );
        AccountingPlan accNumber = session.get( au.getCompany(), dto.getNumber() );
        if ( accNumber != null )
            throwException( 2 );
        accNumber = AccountingPlanUtil.createEntity( au.getCompany(), dto );
        accNumber = session.add( au.getLogin(), accNumber );
        return AccountingPlanUtil.copy( accNumber );
    }

    public AccountingPlanDTO update( AuthenticationDTO auth, AccountingPlanDTO dto ) throws ApplicationException
    {
        AuthUser au = getAuthUser( auth );
        AccountingPlan accNumber = session.get( au.getCompany(), dto.getNumber() );
        if ( accNumber == null )
            throwException( 3 );
        accNumber = AccountingPlanUtil.update( accNumber, dto );
        accNumber = session.update( accNumber );
        return AccountingPlanUtil.copy( accNumber );
    }

    public List<AccountingPlanDTO> getAll( AuthenticationDTO auth ) throws ApplicationException
    {
        AuthUser au = getAuthUser( auth );
        return AccountingPlanUtil.toDTOList( session.getAll( au.getCompany() ) );
    }

    private class AuthUser
    {
        private Company company;
        private Login login;

        public AuthUser( Company c, Login l )
        {
            company = c;
            login = l;
        }

        public void setCompany( Company company )
        {
            this.company = company;
        }

        public Company getCompany()
        {
            return company;
        }

        public void setLogin( Login login )
        {
            this.login = login;
        }

        public Login getLogin()
        {
            return login;
        }
    }
}
