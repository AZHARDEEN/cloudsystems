package br.com.mcampos.ejb.cloudsystem.user.company.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.user.UserFacadeUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.entity.CollaboratorType;
import br.com.mcampos.ejb.cloudsystem.user.company.CompanyUtil;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "MyCompanyFacade", mappedName = "CloudSystems-EjbPrj-MyCompanyFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class MyCompanyFacadeBean extends UserFacadeUtil implements MyCompanyFacade
{
    public static final Integer messageId = 32;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private CompanySessionLocal companySession;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private NewPersonSessionLocal personSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public CompanyDTO get( AuthenticationDTO auth ) throws ApplicationException
    {
        Company company = getCompany( auth );
        return CompanyUtil.copy( company );
    }

    public CompanyDTO update( AuthenticationDTO auth, CompanyDTO dto ) throws ApplicationException
    {
        Company company = getCompany( auth );
        CompanyUtil.update( company, dto );
        refreshUserAttributes( company, dto );
        return CompanyUtil.copy( company );
    }

    private Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        Person person = personSession.get( auth.getUserId() );
        Collaborator c = collaboratorSession.get( company, person );
        if ( c == null )
            throwException( 1 );
        if ( c.getCollaboratorType().getId() != CollaboratorType.typeManager )
            throwException( 2 );
        return c.getCompany();
    }
}
