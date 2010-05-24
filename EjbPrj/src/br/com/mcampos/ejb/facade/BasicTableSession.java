package br.com.mcampos.ejb.facade;


import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.system.SystemParametersDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface BasicTableSession
{
    CivilStateDTO getCivilState( Integer id ) throws ApplicationException;


    /*CivilState Facede Service*/

    void addCivilState( CivilStateDTO newRecord ) throws ApplicationException;

    void updateCivilState( CivilStateDTO newRecord ) throws ApplicationException;

    void deleteCivilState( Integer id ) throws ApplicationException;

    Integer getCivilStateMaxPKValue();

    List getAllCivilState();


    /*AddressType Facede Service*/

    AddressTypeDTO getAddressType( Integer id ) throws ApplicationException;

    void addAddressType( AddressTypeDTO newRecord ) throws ApplicationException;

    void updateAddressType( AddressTypeDTO newRecord ) throws ApplicationException;

    void deleteAddressType( Integer id ) throws ApplicationException;

    Integer getAddressTypeMaxPKValue();

    List getAllAddressType();


    /*ContactType Facede Service*/

    ContactTypeDTO getContactType( Integer id ) throws ApplicationException;

    void addContactType( ContactTypeDTO newRecord ) throws ApplicationException;

    void updateContactType( ContactTypeDTO newRecord ) throws ApplicationException;

    void deleteContactType( Integer id ) throws ApplicationException;

    Integer getContactTypeMaxPKValue();

    List getAllContactType();


    /*DocumentType Facede Service*/

    DocumentTypeDTO getDocumentType( Integer id ) throws ApplicationException;

    void addDocumentType( DocumentTypeDTO newRecord ) throws ApplicationException;

    void updateDocumentType( DocumentTypeDTO newRecord ) throws ApplicationException;

    void deleteDocumentType( Integer id ) throws ApplicationException;

    Integer getDocumentTypeMaxPKValue();

    List getAllDocumentType();


    /*Title Facede Service*/

    TitleDTO getTitle( Integer id ) throws ApplicationException;

    void addTitle( TitleDTO newRecord ) throws ApplicationException;

    void updateTitle( TitleDTO newRecord ) throws ApplicationException;

    void deleteTitle( Integer id ) throws ApplicationException;

    Integer getTitleMaxPKValue();

    List getAllTitle();

    /*UserStatus Facede Service*/

    UserStatusDTO getUserStatus( Integer id ) throws ApplicationException;

    void addUserStatus( UserStatusDTO newRecord ) throws ApplicationException;

    void updateUserStatus( UserStatusDTO newRecord ) throws ApplicationException;

    void deleteUserStatus( Integer id ) throws ApplicationException;

    Integer getUserStatusMaxPKValue();

    List getAllUserStatus();

    /*UserType Facede Service*/

    UserTypeDTO getUserType( Integer id ) throws ApplicationException;

    void addUserType( UserTypeDTO newRecord ) throws ApplicationException;

    void updateUserType( UserTypeDTO newRecord ) throws ApplicationException;

    void deleteUserType( Integer id ) throws ApplicationException;

    Integer getUserTypeMaxPKValue();

    List getAllUserType();

    /*Country Facade Service*/

    CountryDTO getCountry( Integer id ) throws ApplicationException;

    void addCountry( CountryDTO newRecord ) throws ApplicationException;

    void updateCountry( CountryDTO newRecord ) throws ApplicationException;

    void deleteCountry( Integer id ) throws ApplicationException;

    Integer getCountryMaxPKValue();

    List getAllCountry();


    /*SystemParameters Facade Service*/

    SystemParametersDTO getSystemParameters( String id ) throws ApplicationException;

    void addSystemParameters( SystemParametersDTO newRecord ) throws ApplicationException;

    void updateSystemParameters( SystemParametersDTO newRecord ) throws ApplicationException;

    List getAllSystemParameters();

    /*AccessLogType Facade Service*/

    public AccessLogTypeDTO getAccessLogType( Integer id ) throws ApplicationException;

    public void add( AccessLogTypeDTO newRecord ) throws ApplicationException;

    public void updateAccessLogType( AccessLogTypeDTO newRecord ) throws ApplicationException;

    public List getAllAccessLogType();

    public void deleteAccessLogType( Integer id ) throws ApplicationException;

    public Integer getAccessLogTypeMaxPKValue();


    /*CompanyType Facade Service*/

    public CompanyTypeDTO getCompanyType( Integer id ) throws ApplicationException;

    public void add( CompanyTypeDTO newRecord ) throws ApplicationException;

    public void updateCompanyType( CompanyTypeDTO newRecord ) throws ApplicationException;

    public List getAllCompanyType();

    public void deleteCompanyType( Integer id ) throws ApplicationException;

    public Integer getCompanyTypeMaxPKValue();


    /*CompanyPosition Facade Service*/

    public CompanyPositionDTO getCompanyPosition( Integer id ) throws ApplicationException;

    public void add( CompanyPositionDTO newRecord ) throws ApplicationException;

    public void updateCompanyPosition( CompanyPositionDTO newRecord ) throws ApplicationException;

    public List getAllCompanyPosition();

    public void deleteCompanyPosition( Integer id ) throws ApplicationException;

    public Integer getCompanyPositionMaxPKValue();


    List<StateDTO> getAllStates();

    List<CityDTO> getAllStateCities( Integer countryId, Integer stateId ) throws ApplicationException;
}
