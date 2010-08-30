package br.com.mcampos.ejb.cloudsystem.account.util;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.account.mask.session.AccountingMaskSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.cloudsystem.user.login.LoginSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.EJB;

public abstract class AccountingAuthUser extends AbstractSecurity
{
    private Company company;
    private Login login;
    private AccountingMask mask;

    @EJB
    protected CompanySessionLocal companySession;

    @EJB
    protected LoginSessionLocal loginSession;

    @EJB
    protected AccountingMaskSessionLocal maskSession;


    public AccountingAuthUser load( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = companySession.get( auth.getCurrentCompany() );
        if ( company == null )
            throw new ApplicationException( "No Company" );
        Login login = loginSession.get( auth.getUserId() );
        if ( login == null )
            throw new ApplicationException( "No login" );
        setLogin( login );
        setCompany( company );
        return this;
    }


    public AccountingAuthUser load( AuthenticationDTO auth, Integer maskId ) throws ApplicationException
    {
        Company company = companySession.get( auth.getCurrentCompany() );
        if ( company == null )
            throw new ApplicationException( "No Company" );
        Login login = loginSession.get( auth.getUserId() );
        if ( login == null )
            throw new ApplicationException( "No login" );
        setLogin( login );
        setCompany( company );
        AccountingMask mask = maskSession.get( getCompany(), maskId );
        if ( mask == null )
            throw new ApplicationException( "No Accounting Mask" );
        setMask( mask );
        return this;
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

    public void setMask( AccountingMask mask )
    {
        this.mask = mask;
    }

    public AccountingMask getMask()
    {
        return mask;
    }
}
