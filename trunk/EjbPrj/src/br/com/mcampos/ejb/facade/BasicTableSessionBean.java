package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.core.DisplayNameDTO;
import br.com.mcampos.dto.system.SystemParametersDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.ejb.session.address.AddressTypeSessionLocal;
import br.com.mcampos.ejb.session.address.CitySessionLocal;
import br.com.mcampos.ejb.session.address.CountrySessionLocal;
import br.com.mcampos.ejb.session.address.StateSessionLocal;
import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;
import br.com.mcampos.ejb.session.system.TableManagerSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.CivilStateSessionLocal;

import br.com.mcampos.ejb.session.user.attributes.ContactTypeSessionLocal;

import br.com.mcampos.ejb.session.user.attributes.DocumentTypeSessionLocal;

import br.com.mcampos.ejb.session.user.attributes.GenderSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.TitleSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.UserStatusSessionLocal;
import br.com.mcampos.ejb.session.user.attributes.UserTypeSessionLocal;

import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;


@Stateless( name = "BasicTableSession", mappedName = "CloudSystems-EjbPrj-BasicTableSession" )
@Remote
public class BasicTableSessionBean implements BasicTableSession
{
    @EJB CivilStateSessionLocal civilState;
    @EJB AddressTypeSessionLocal addressType;
    @EJB ContactTypeSessionLocal contactType;
    @EJB DocumentTypeSessionLocal documentType;
    @EJB GenderSessionLocal gender;
    @EJB TitleSessionLocal title;
    @EJB UserStatusSessionLocal userStatus;
    @EJB UserTypeSessionLocal userType;
    @EJB CountrySessionLocal country;
    @EJB TableManagerSessionLocal tableManager;
    @EJB SystemMessagesSessionLocal systemMessage;
    @EJB StateSessionLocal state;
    @EJB CitySessionLocal city;
    
    private static final Integer systemMessageTypeId = 2;

    public BasicTableSessionBean()
    {
        
    }

    protected CivilStateSessionLocal getCivilStateSession()
    {
        return civilState;
    }

    protected AddressTypeSessionLocal getAddressTypeSession()
    {
        return addressType;
    }

    protected ContactTypeSessionLocal getContactTypeSession()
    {
        return contactType;
    }

    protected DocumentTypeSessionLocal getDocumentTypeSession()
    {
        return documentType;
    }

    protected GenderSessionLocal getGenderSession()
    {
        return gender;
    }

    protected TitleSessionLocal getTitleSession()
    {
        return title;
    }

    protected UserStatusSessionLocal getUserStatusSession()
    {
        return userStatus;
    }

    protected UserTypeSessionLocal getUserTypeSession()
    {
        return userType;
    }

    protected CountrySessionLocal getCountrySession()
    {
        return country;
    }


    protected StateSessionLocal getStateSession()
    {
        return state;
    }

    protected CitySessionLocal getCitySession()
    {
        return city;
    }


    
    protected TableManagerSessionLocal getTableManager()
    {
        return tableManager;
    }
    
    /*
     * CIVIL STATE***************************************
     */
    public CivilStateDTO getCivilState ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getCivilStateSession ().get( id );
    }
    
    public void addCivilState ( CivilStateDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getCivilStateSession ().add( newRecord );
    }
    
    public void updateCivilState ( CivilStateDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getCivilStateSession ().update( newRecord );
    }

    public void deleteCivilState ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getCivilStateSession ().delete( id );
    }
    
    public Integer getCivilStateMaxPKValue ()
    {
        return getCivilStateSession().getMaxPKValue();
    }
    
    public List getAllCivilState ()
    {
        return getCivilStateSession().getAll();
    }


    /*
     * ADDRESS TYPE***************************************
     */
    public AddressTypeDTO getAddressType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getAddressTypeSession ().get( id );
    }
    
    public void addAddressType ( AddressTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getAddressTypeSession ().add( newRecord );
    }
    
    public void updateAddressType ( AddressTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getAddressTypeSession ().update( newRecord );
    }

    public void deleteAddressType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getAddressTypeSession ().delete( id );
    }
    
    public Integer getAddressTypeMaxPKValue ()
    {
        return getAddressTypeSession().getIdMaxValue();
    }
    
    public List getAllAddressType ()
    {
        return getAddressTypeSession().getAll();
    }



    /*
     * CONTACT TYPE***************************************
     */
    public ContactTypeDTO getContactType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getContactTypeSession ().get( id );
    }
    
    public void addContactType ( ContactTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getContactTypeSession ().add( newRecord );
    }
    
    public void updateContactType ( ContactTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getContactTypeSession ().update( newRecord );
    }

    public void deleteContactType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getContactTypeSession ().delete( id );
    }
    
    public Integer getContactTypeMaxPKValue ()
    {
        return getContactTypeSession().getIdMaxValue();
    }
    
    public List getAllContactType ()
    {
        return getContactTypeSession().getAll();
    }



    /*
     * DOCUMENT TYPE***************************************
     */
    public DocumentTypeDTO getDocumentType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getDocumentTypeSession ().get( id );
    }
    
    public void addDocumentType ( DocumentTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getDocumentTypeSession ().add( newRecord );
    }
    
    public void updateDocumentType ( DocumentTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getDocumentTypeSession ().update( newRecord );
    }

    public void deleteDocumentType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getDocumentTypeSession ().delete( id );
    }
    
    public Integer getDocumentTypeMaxPKValue ()
    {
        return getDocumentTypeSession().getIdMaxValue();
    }
    
    public List getAllDocumentType ()
    {
        return getDocumentTypeSession().getAll();
    }


    /*
     * GENDER ***************************************
     */
    public GenderDTO getGender ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getGenderSession ().get( id );
    }
    
    public void addGender ( GenderDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getGenderSession ().add( newRecord );
    }
    
    public void updateGender ( GenderDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getGenderSession ().update( newRecord );
    }

    public void deleteGender ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getGenderSession ().delete( id );
    }
    
    public Integer getGenderMaxPKValue ()
    {
        return getGenderSession().getIdMaxValue();
    }
    
    public List getAllGender ()
    {
        return getGenderSession().getAll();
    }

    /*
     * TITLE ***************************************
     */
    public TitleDTO getTitle ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getTitleSession ().get( id );
    }
    
    public void addTitle ( TitleDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTitleSession ().add( newRecord );
    }
    
    public void updateTitle ( TitleDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTitleSession ().update( newRecord );
    }

    public void deleteTitle ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getTitleSession ().delete( id );
    }
    
    public Integer getTitleMaxPKValue ()
    {
        return getTitleSession().getIdMaxValue();
    }
    
    public List getAllTitle ()
    {
        return getTitleSession().getAll();
    }


    /*
     * USER STATUS***************************************
     */
    public UserStatusDTO getUserStatus ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getUserStatusSession ().get( id );
    }
    
    public void addUserStatus ( UserStatusDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getUserStatusSession ().add( newRecord );
    }
    
    public void updateUserStatus ( UserStatusDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getUserStatusSession ().update( newRecord );
    }

    public void deleteUserStatus ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getUserStatusSession ().delete( id );
    }
    
    public Integer getUserStatusMaxPKValue ()
    {
        return getUserStatusSession().getIdMaxValue();
    }
    
    public List getAllUserStatus ()
    {
        return getUserStatusSession().getAll();
    }



    /*
     * User TYPE***************************************
     */
    public UserTypeDTO getUserType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getUserTypeSession ().get( id );
    }
    
    public void addUserType ( UserTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getUserTypeSession ().add( newRecord );
    }
    
    public void updateUserType ( UserTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getUserTypeSession ().update( newRecord );
    }

    public void deleteUserType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getUserTypeSession ().delete( id );
    }
    
    public Integer getUserTypeMaxPKValue ()
    {
        return getUserTypeSession().getIdMaxValue();
    }
    
    public List getAllUserType ()
    {
        return getUserTypeSession().getAll();
    }


    /*
     * User TYPE***************************************
     */
    public CountryDTO getCountry ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getCountrySession ().get( id );
    }
    
    public void addCountry ( CountryDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getCountrySession ().add( newRecord );
    }
    
    public void updateCountry ( CountryDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getCountrySession ().update( newRecord );
    }

    public void deleteCountry ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getCountrySession ().delete( id );
    }
    
    public Integer getCountryMaxPKValue ()
    {
        return getCountrySession().getIdMaxValue();
    }
    
    public List getAllCountry ()
    {
        return getCountrySession().getAll();
    }


    /*
     * SystemParameters***************************************
     */
    public SystemParametersDTO getSystemParameters ( String id ) throws ApplicationException
    {
        testParam ( id );
        return getTableManager ().getSystemParameter( id );
    }
    
    public void addSystemParameters ( SystemParametersDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTableManager ().add( newRecord );
    }
    
    public void updateSystemParameters ( SystemParametersDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTableManager ().update( newRecord );
    }

    public List getAllSystemParameters ()
    {
        return getTableManager().getAll();
    }



    /*
     * AccessLogType***************************************
     */
    public AccessLogTypeDTO getAccessLogType( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getTableManager ().getAccessLogType( id );
    }
    
    public void add ( AccessLogTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTableManager ().add( newRecord );
    }
    
    public void updateAccessLogType ( AccessLogTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTableManager ().update( newRecord );
    }

    public List getAllAccessLogType ()
    {
        return getTableManager().getAllAccessLogType();
    }

    public void deleteAccessLogType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getTableManager ().deleteAccessLogType( id );
    }
    
    public Integer getAccessLogTypeMaxPKValue ()
    {
        return getTableManager().getAccessLogTypeMaxValue();
    }




    /*
     * CompanyPosition***************************************
     */
    public CompanyPositionDTO getCompanyPosition( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getTableManager ().getCompanyPosition( id );
    }
    
    public void add ( CompanyPositionDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTableManager ().add( newRecord );
    }
    
    public void updateCompanyPosition ( CompanyPositionDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTableManager ().update( newRecord );
    }

    public List getAllCompanyPosition ()
    {
        return getTableManager().getAllCompanyPosition();
    }

    public void deleteCompanyPosition ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getTableManager ().deleteCompanyPosition( id );
    }
    
    public Integer getCompanyPositionMaxPKValue ()
    {
        return getTableManager().getCompanyPositionMaxValue();
    }



    /*
     * CompanyType***************************************
     */
    public CompanyTypeDTO getCompanyType( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getTableManager ().getCompanyType( id );
    }
    
    public void add ( CompanyTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTableManager ().add( newRecord );
    }
    
    public void updateCompanyType ( CompanyTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTableManager ().update( newRecord );
    }

    public List getAllCompanyType ()
    {
        return getTableManager().getAllCompanyType();
    }

    public void deleteCompanyType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getTableManager ().deleteCompanyType( id );
    }
    
    public Integer getCompanyTypeMaxPKValue ()
    {
        return getTableManager().getCompanyTypeMaxValue();
    }
    
    protected void testParam ( Integer param ) throws ApplicationException
    {
        if ( SysUtils.isZero( param ) )
            systemMessage.throwException( systemMessageTypeId, 1 );
    }

    protected void testParam ( String param ) throws ApplicationException
    {
        if ( SysUtils.isEmpty( param ) )
            systemMessage.throwException( systemMessageTypeId, 1 );
    }


    protected void testParam ( DisplayNameDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            systemMessage.throwException( systemMessageTypeId, 1 );
    }
    
    
    public List<StateDTO> getAllStates ( )
    {
        return getStateSession().getAll( 33 );
    }
    
    public List<CityDTO> getAllStateCities ( Integer countryId, Integer stateId ) throws ApplicationException
    {
        testParam( countryId );
        testParam( stateId );
        return getCitySession().getAll( countryId, stateId );
    }

}



