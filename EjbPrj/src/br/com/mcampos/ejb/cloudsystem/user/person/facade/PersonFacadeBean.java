package br.com.mcampos.ejb.cloudsystem.user.person.facade;


import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.PersonDTO;
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
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.CivilStateUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.entity.CivilState;
import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.session.CivilStateSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.GenderUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.session.GenderSessionLocal;
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
        authenticate( auth );
        Person person = session.get( auth.getUserId() );
        return PersonUtil.copy( person );
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
}
