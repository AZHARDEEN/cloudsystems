package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CountryDTO;
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
import br.com.mcampos.ejb.session.address.CountrySessionLocal;
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
    
    private static final Integer systemMessageTypeId = 2;

    public BasicTableSessionBean()
    {
        
    }

    protected CivilStateSessionLocal getCivilState()
    {
        return civilState;
    }

    protected AddressTypeSessionLocal getAddressType()
    {
        return addressType;
    }

    protected ContactTypeSessionLocal getContactType()
    {
        return contactType;
    }

    protected DocumentTypeSessionLocal getDocumentType()
    {
        return documentType;
    }

    protected GenderSessionLocal getGender()
    {
        return gender;
    }

    protected TitleSessionLocal getTitle()
    {
        return title;
    }

    protected UserStatusSessionLocal getUserStatus()
    {
        return userStatus;
    }

    protected UserTypeSessionLocal getUserType()
    {
        return userType;
    }

    protected CountrySessionLocal getCountry()
    {
        return country;
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
        return getCivilState ().get( id );
    }
    
    public void addCivilState ( CivilStateDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getCivilState ().add( newRecord );
    }
    
    public void updateCivilState ( CivilStateDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getCivilState ().update( newRecord );
    }

    public void deleteCivilState ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getCivilState ().delete( id );
    }
    
    public Integer getCivilStateMaxPKValue ()
    {
        return getCivilState().getMaxPKValue();
    }
    
    public List getAllCivilState ()
    {
        return getCivilState().getAll();
    }


    /*
     * ADDRESS TYPE***************************************
     */
    public AddressTypeDTO getAddressType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getAddressType ().get( id );
    }
    
    public void addAddressType ( AddressTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getAddressType ().add( newRecord );
    }
    
    public void updateAddressType ( AddressTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getAddressType ().update( newRecord );
    }

    public void deleteAddressType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getAddressType ().delete( id );
    }
    
    public Integer getAddressTypeMaxPKValue ()
    {
        return getAddressType().getIdMaxValue();
    }
    
    public List getAllAddressType ()
    {
        return getAddressType().getAll();
    }



    /*
     * CONTACT TYPE***************************************
     */
    public ContactTypeDTO getContactType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getContactType ().get( id );
    }
    
    public void addContactType ( ContactTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getContactType ().add( newRecord );
    }
    
    public void updateContactType ( ContactTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getContactType ().update( newRecord );
    }

    public void deleteContactType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getContactType ().delete( id );
    }
    
    public Integer getContactTypeMaxPKValue ()
    {
        return getContactType().getIdMaxValue();
    }
    
    public List getAllContactType ()
    {
        return getContactType().getAll();
    }



    /*
     * DOCUMENT TYPE***************************************
     */
    public DocumentTypeDTO getDocumentType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getDocumentType ().get( id );
    }
    
    public void addDocumentType ( DocumentTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getDocumentType ().add( newRecord );
    }
    
    public void updateDocumentType ( DocumentTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getDocumentType ().update( newRecord );
    }

    public void deleteDocumentType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getDocumentType ().delete( id );
    }
    
    public Integer getDocumentTypeMaxPKValue ()
    {
        return getDocumentType().getIdMaxValue();
    }
    
    public List getAllDocumentType ()
    {
        return getDocumentType().getAll();
    }


    /*
     * GENDER ***************************************
     */
    public GenderDTO getGender ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getGender ().get( id );
    }
    
    public void addGender ( GenderDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getGender ().add( newRecord );
    }
    
    public void updateGender ( GenderDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getGender ().update( newRecord );
    }

    public void deleteGender ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getGender ().delete( id );
    }
    
    public Integer getGenderMaxPKValue ()
    {
        return getGender().getIdMaxValue();
    }
    
    public List getAllGender ()
    {
        return getGender().getAll();
    }

    /*
     * TITLE ***************************************
     */
    public TitleDTO getTitle ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getTitle ().get( id );
    }
    
    public void addTitle ( TitleDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTitle ().add( newRecord );
    }
    
    public void updateTitle ( TitleDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getTitle ().update( newRecord );
    }

    public void deleteTitle ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getTitle ().delete( id );
    }
    
    public Integer getTitleMaxPKValue ()
    {
        return getTitle().getIdMaxValue();
    }
    
    public List getAllTitle ()
    {
        return getTitle().getAll();
    }


    /*
     * USER STATUS***************************************
     */
    public UserStatusDTO getUserStatus ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getUserStatus ().get( id );
    }
    
    public void addUserStatus ( UserStatusDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getUserStatus ().add( newRecord );
    }
    
    public void updateUserStatus ( UserStatusDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getUserStatus ().update( newRecord );
    }

    public void deleteUserStatus ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getUserStatus ().delete( id );
    }
    
    public Integer getUserStatusMaxPKValue ()
    {
        return getUserStatus().getIdMaxValue();
    }
    
    public List getAllUserStatus ()
    {
        return getUserStatus().getAll();
    }



    /*
     * User TYPE***************************************
     */
    public UserTypeDTO getUserType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getUserType ().get( id );
    }
    
    public void addUserType ( UserTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getUserType ().add( newRecord );
    }
    
    public void updateUserType ( UserTypeDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getUserType ().update( newRecord );
    }

    public void deleteUserType ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getUserType ().delete( id );
    }
    
    public Integer getUserTypeMaxPKValue ()
    {
        return getUserType().getIdMaxValue();
    }
    
    public List getAllUserType ()
    {
        return getUserType().getAll();
    }


    /*
     * User TYPE***************************************
     */
    public CountryDTO getCountry ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        return getCountry ().get( id );
    }
    
    public void addCountry ( CountryDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getCountry ().add( newRecord );
    }
    
    public void updateCountry ( CountryDTO newRecord ) throws ApplicationException
    {
        testParam ( newRecord );
        getCountry ().update( newRecord );
    }

    public void deleteCountry ( Integer id ) throws ApplicationException
    {
        testParam ( id );
        getCountry ().delete( id );
    }
    
    public Integer getCountryMaxPKValue ()
    {
        return getCountry().getIdMaxValue();
    }
    
    public List getAllCountry ()
    {
        return getCountry().getAll();
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

}



