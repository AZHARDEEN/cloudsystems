package br.com.mcampos.ejb.cloudsystem.user.address.facade;


import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.ejb.cloudsystem.locality.country.CountryUtil;
import br.com.mcampos.ejb.cloudsystem.locality.country.entity.Country;
import br.com.mcampos.ejb.cloudsystem.locality.country.session.CountrySessionLocal;
import br.com.mcampos.ejb.cloudsystem.locality.state.StateUtil;
import br.com.mcampos.ejb.cloudsystem.locality.state.session.StateSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.AddressTypeUtil;
import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.session.AddressTypeSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AddressFacade", mappedName = "CloudSystems-EjbPrj-AddressFacade" )
@Remote
public class AddressFacadeBean extends AbstractSecurity implements AddressFacade
{
    public static final Integer messageId = 23;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private AddressTypeSessionLocal addressTypeSession;

    @EJB
    private StateSessionLocal stateSession;

    @EJB
    private CountrySessionLocal countrySession;

    public AddressFacadeBean()
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

    public List<AddressTypeDTO> getTypes() throws ApplicationException
    {
        return AddressTypeUtil.toDTOList( addressTypeSession.getAll() );
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
}
