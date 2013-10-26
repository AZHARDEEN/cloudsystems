package br.com.mcampos.ejb.cloudsystem.user.person.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.ejb.cloudsystem.user.UserFacadeUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.CivilStateUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.entity.CivilState;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.session.CivilStateSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.GenderUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.session.GenderSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.ejb.cloudsystem.user.document.session.UserDocumentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.cloudsystem.user.login.LoginSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.PersonUtil;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "PersonFacade", mappedName = "PersonFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class PersonFacadeBean extends UserFacadeUtil implements PersonFacade
{

    public static final Integer messageId = 22;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private NewPersonSessionLocal session;

    @EJB
    private CivilStateSessionLocal civilStateSession;

    @EJB
    private GenderSessionLocal genderSession;

    @EJB
    private NewPersonSessionLocal personSession;

    @EJB
    private UserDocumentSessionLocal userDocumentSession;

    @EJB
    private LoginSessionLocal loginSession;

    public PersonFacadeBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public PersonDTO get( AuthenticationDTO auth ) throws ApplicationException
    {
        Person person = getPerson( auth );
        return PersonUtil.copy( person );
    }

    protected Person getPerson( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        Person person = session.get( auth.getUserId() );
        if ( person == null )
            throwException( 3 );
        return person;
    }


    public List<GenderDTO> getGenders() throws ApplicationException
    {
        List<Gender> list = genderSession.getAll();

        return GenderUtil.toDTOList( list );
    }

    public List<CivilStateDTO> getCivilStates() throws ApplicationException
    {
        List<CivilState> list = civilStateSession.getAll();
        return CivilStateUtil.toDTOList( list );
    }

    public PersonDTO updateMyRecord( AuthenticationDTO auth, PersonDTO dto ) throws ApplicationException
    {
        Person person = getPerson( auth );
        if ( person.getId().equals( dto.getId() ) == false )
            throwException( 4 );
        update( person, dto );
        Login login = loginSession.get( person );
        if ( login != null ) {
            if ( login.getUserStatus().getId().equals( 5 ) ) {
                loginSession.setStatus( auth, 1 );
            }
        }
        return PersonUtil.copy( person );
    }


    private void update( Person person, PersonDTO dto ) throws ApplicationException
    {
        PersonUtil.update( person, dto );
        person = personSession.update( person );
        refreshUserAttributes( person, dto );
    }


    public PersonDTO get( AuthenticationDTO auth, String document, Integer docTpe ) throws ApplicationException
    {
        authenticate( auth );
        UserDocumentDTO doc = new UserDocumentDTO();
        doc.setCode( document );
        doc.setDocumentType( new DocumentTypeDTO( docTpe ) );
        UserDocument userDocument = userDocumentSession.find( doc );
        if ( userDocument == null || userDocument.getUser() instanceof Company )
            return null;
        return PersonUtil.copy( ( Person )userDocument.getUser() );
    }

    public Integer getLoginStatus( AuthenticationDTO auth ) throws ApplicationException
    {
        Person person = personSession.get( auth.getUserId() );
        if ( person == null )
            return 3;
        Login login = loginSession.get( person );
        if ( login == null )
            return 3;
        return login.getUserStatus().getId();
    }
}
