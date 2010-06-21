package br.com.mcampos.ejb.cloudsystem.user.collaborator.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.collaborator.CollaboratorDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.entity.ClientPK;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.CollaboratorUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CollaboratorFacade", mappedName = "CloudSystems-EjbPrj-CollaboratorFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CollaboratorFacadeBean extends AbstractSecurity implements CollaboratorFacade
{
    public static final Integer messageId = 28;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private NewCollaboratorSessionLocal collaboratorSession;

    @EJB
    private NewPersonSessionLocal personSession;

    @EJB
    private ClientSessionLocal clientSession;

    @EJB
    private CompanySessionLocal companySession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    private Person getPerson( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Person person = personSession.get( auth.getUserId() );
        if ( person == null )
            throwException( 1 );
        return person;
    }

    public List<CompanyDTO> getCompanies( AuthenticationDTO auth ) throws ApplicationException
    {
        Person person = getPerson( auth );
        List<Collaborator> list = collaboratorSession.get( person );
        return CollaboratorUtil.toCompanyDTOList( list );
    }

    public List<CollaboratorDTO> getCollaborators( AuthenticationDTO auth, Integer clientCompany ) throws ApplicationException
    {
        Person person = getPerson( auth );
        Company company = companySession.get( auth.getCurrentCompany() );
        Collaborator coll = collaboratorSession.get( company, person );
        if ( coll == null )
            throwException( 2 ); //I'm not a collaborator of current company..... ????
        Client client = clientSession.get( new ClientPK( company.getId(), clientCompany ) );
        if ( client == null )
            throwException( 3 ); // this is not a client of my company, so i cannot get a collaborator list
        List<Collaborator> list = collaboratorSession.get( client.getClient() );
        return CollaboratorUtil.toDTOList( list );
    }
}
