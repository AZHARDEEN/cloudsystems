package br.com.mcampos.ejb.cloudsystem.user.person.facade;


import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.ejb.cloudsystem.locality.city.CityUtil;
import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.cloudsystem.locality.city.session.CitySessionLocal;
import br.com.mcampos.ejb.cloudsystem.locality.country.CountryUtil;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.cloudsystem.locality.country.session.CountrySessionLocal;
import br.com.mcampos.ejb.cloudsystem.locality.state.StateUtil;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.State;
import br.com.mcampos.ejb.cloudsystem.locality.state.entity.StatePK;
import br.com.mcampos.ejb.cloudsystem.locality.state.session.StateSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.address.AddressUtil;
import br.com.mcampos.ejb.cloudsystem.user.address.entity.Address;
import br.com.mcampos.ejb.cloudsystem.user.address.session.AddressSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.CivilStateUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.entity.CivilState;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.session.CivilStateSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.GenderUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.session.GenderSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.session.UserTypeSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.contact.UserContactUtil;
import br.com.mcampos.ejb.cloudsystem.user.contact.entity.UserContact;
import br.com.mcampos.ejb.cloudsystem.user.contact.session.UserContactSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.document.UserDocumentUtil;
import br.com.mcampos.ejb.cloudsystem.user.document.entity.UserDocument;
import br.com.mcampos.ejb.cloudsystem.user.document.session.UserDocumentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.PersonUtil;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "PersonFacade", mappedName = "CloudSystems-EjbPrj-PersonFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class PersonFacadeBean extends AbstractSecurity implements PersonFacade
{

    public static final Integer messageId = 22;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private NewPersonSessionLocal session;

    @EJB
    private StateSessionLocal stateSession;

    @EJB
    private CountrySessionLocal countrySession;

    @EJB
    private CivilStateSessionLocal civilStateSession;

    @EJB
    private CitySessionLocal citySession;

    @EJB
    private GenderSessionLocal genderSession;

    @EJB
    private NewPersonSessionLocal personSession;

    @EJB
    UserTypeSessionLocal userTypeSession;

    @EJB
    AddressSessionLocal addressSession;

    @EJB
    UserDocumentSessionLocal documentSession;

    @EJB
    UserContactSessionLocal contactSession;


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

    public List<StateDTO> getStates( CountryDTO dto ) throws ApplicationException
    {
        Country country = countrySession.get( dto.getId() );

        return StateUtil.toDTOList( stateSession.getAll( country ) );
    }

    public List<CountryDTO> getCountries() throws ApplicationException
    {
        List<Country> countries = countrySession.getAllWithCities();

        return CountryUtil.toDTOList( countries );
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

    public List<CityDTO> getCities( StateDTO dto ) throws ApplicationException
    {
        if ( dto != null ) {
            State state = stateSession.get( new StatePK( dto ) );
            List<City> list = citySession.getAll( state );
            return CityUtil.toDTOList( list );
        }
        return Collections.emptyList();
    }

    public void updateMyRecord( AuthenticationDTO auth, PersonDTO dto ) throws ApplicationException
    {
        Person person = getPerson( auth );
        if ( person.getId().equals( dto.getId() ) == false )
            throwException( 4 );

        person.getAddresses().clear();
        person.getDocuments().clear();
        person.getDocuments().clear();
        PersonUtil.update( person, dto );
        person = personSession.update( person );
        for ( AddressDTO i : dto.getAddressList() ) {
            Address e = AddressUtil.createEntity( i, person );
            addressSession.add( e );
        }
        for ( UserDocumentDTO i : dto.getDocumentList() ) {
            UserDocument e = UserDocumentUtil.createEntity( i, person );
            documentSession.add( e );
        }
        for ( UserContactDTO i : dto.getContactList() ) {
            UserContact e = UserContactUtil.createEntity( i, person );
            contactSession.add( e );
        }
    }
}
