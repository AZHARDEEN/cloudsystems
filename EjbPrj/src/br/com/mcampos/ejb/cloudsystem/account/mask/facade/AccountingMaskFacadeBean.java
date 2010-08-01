package br.com.mcampos.ejb.cloudsystem.account.mask.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;
import br.com.mcampos.ejb.cloudsystem.account.mask.session.AccountingMaskSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AccountingMaskFacade", mappedName = "CloudSystems-EjbPrj-AccountingMaskFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class AccountingMaskFacadeBean extends AbstractSecurity implements AccountingMaskFacade
{
    public static final Integer messageId = 41;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private AccountingMaskSessionLocal maskSession;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    private Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        if ( company == null )
            throwException( 1 );
        Collaborator collaborator = collaboratorSession.get( company.getId(), auth.getUserId() );
        if ( collaborator == null )
            throwException( 2 );
        return company;
    }

    public void set( AuthenticationDTO auth, String mask ) throws ApplicationException
    {
        Company company = getCompany( auth );
        maskSession.set( company, mask );
    }

    public String get( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        AccountingMask entity = maskSession.get( company );
        return entity != null ? entity.getMask() : null;
    }
}
