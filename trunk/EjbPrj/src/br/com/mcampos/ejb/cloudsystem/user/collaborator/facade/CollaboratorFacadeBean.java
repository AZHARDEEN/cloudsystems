package br.com.mcampos.ejb.cloudsystem.user.collaborator.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.collaborator.CollaboratorDTO;
import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.client.session.ClientSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.UserFacadeUtil;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.CollaboratorUtil;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.NewCollaboratorSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.CollaboratorPK;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.type.session.CollaboratorTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.PersonUtil;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CollaboratorFacade", mappedName = "CloudSystems-EjbPrj-CollaboratorFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CollaboratorFacadeBean extends UserFacadeUtil implements CollaboratorFacade
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

    @EJB
    private CollaboratorTypeSessionLocal type;

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
        if ( hasAuthority( auth, clientCompany ) == false )
            return Collections.emptyList();
        Company company = companySession.get( clientCompany );
        List<Collaborator> list = collaboratorSession.get( company );
        return CollaboratorUtil.toDTOList( list );
    }

    protected Company getCompany( AuthenticationDTO auth ) throws ApplicationException
    {
        Collaborator coll = collaboratorSession.get( auth.getCurrentCompany(), auth.getUserId() );
        return coll.getCompany();
    }

    public void delete( AuthenticationDTO auth, CollaboratorDTO dto ) throws ApplicationException
    {
        if ( hasAuthority( auth, dto.getCompany().getId() ) == false )
            return;
        collaboratorSession.delete( new CollaboratorPK( dto.getCompany().getId(), dto.getSequence() ) );
    }


    protected boolean hasAuthority( AuthenticationDTO auth, Integer clientId ) throws ApplicationException
    {
        Person person = getPerson( auth );
        Company company = companySession.get( clientId );
        Company myCompany = getCompany( auth );
        Collaborator coll = collaboratorSession.get( myCompany, person );
        if ( coll == null )
            throwException( 2 ); //I'm not a collaborator of current company..... ????
        if ( myCompany.equals( company ) == false ) {
            Client client = clientSession.get( myCompany, company );
            if ( client == null )
                throwException( 3 ); // this is not a client of my company, so i cannot get a collaborator list
        }
        return true;
    }

    public CollaboratorDTO add( AuthenticationDTO auth, Integer clientId, PersonDTO newDto ) throws ApplicationException
    {
        if ( hasAuthority( auth, clientId ) == false )
            return null;
        Person person = createPerson( newDto );
        Company company = companySession.get( clientId );
        if ( company == null )
            throwException( 3 );
        Collaborator col = collaboratorSession.get( company, person );
        if ( col == null ) {
            col = new Collaborator();
            col.setCompany( company );
            col.setPerson( person );
            col.setCollaboratorType( type.get( 1 ) );
            col.setCompanyPosition( 10 );
            col = collaboratorSession.add( col );
        }
        return CollaboratorUtil.copy( col );
    }

    private Person createPerson( PersonDTO newDto ) throws ApplicationException
    {
        Person newPerson = PersonUtil.createEntity( newDto );
        UserUtil.addDocuments( newPerson, newDto );
        Person entity = personSession.find( newPerson );
        if ( entity == null ) {
            entity = personSession.add( entity );
        }
        else {
            PersonUtil.update( entity, newDto );
            entity = personSession.update( entity );
        }
        refreshUserAttributes( entity, newDto );
        return entity;
    }
}
