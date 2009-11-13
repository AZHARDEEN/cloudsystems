package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;

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

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface BasicTableSession
{
    CivilStateDTO getCivilState ( Integer id );


    /*CivilState Facede Service*/
    void addCivilState ( CivilStateDTO newRecord );
    void updateCivilState ( CivilStateDTO newRecord );
    void deleteCivilState ( Integer id );
    Integer getCivilStateMaxPKValue ();
    List getAllCivilState ();


    /*AddressType Facede Service*/
    AddressTypeDTO getAddressType ( Integer id );
    void addAddressType ( AddressTypeDTO newRecord );
    void updateAddressType ( AddressTypeDTO newRecord );
    void deleteAddressType ( Integer id );
    Integer getAddressTypeMaxPKValue ();
    List getAllAddressType ();


    /*ContactType Facede Service*/
   ContactTypeDTO getContactType ( Integer id );
    void addContactType ( ContactTypeDTO newRecord );
    void updateContactType ( ContactTypeDTO newRecord );
    void deleteContactType ( Integer id );
    Integer getContactTypeMaxPKValue ();
    List getAllContactType ();


    /*DocumentType Facede Service*/
    DocumentTypeDTO getDocumentType ( Integer id );
    void addDocumentType ( DocumentTypeDTO newRecord );
    void updateDocumentType ( DocumentTypeDTO newRecord );
    void deleteDocumentType ( Integer id );
    Integer getDocumentTypeMaxPKValue ();
    List getAllDocumentType ();


    /*Gender Facede Service*/
    GenderDTO getGender ( Integer id );
    void addGender ( GenderDTO newRecord );
    void updateGender ( GenderDTO newRecord );
    void deleteGender ( Integer id );
    Integer getGenderMaxPKValue ();
    List getAllGender ();


    /*Title Facede Service*/
    TitleDTO getTitle ( Integer id );
    void addTitle ( TitleDTO newRecord );
    void updateTitle ( TitleDTO newRecord );
    void deleteTitle ( Integer id );
    Integer getTitleMaxPKValue ();
    List getAllTitle ();

    /*UserStatus Facede Service*/
    UserStatusDTO getUserStatus ( Integer id );
    void addUserStatus ( UserStatusDTO newRecord );
    void updateUserStatus ( UserStatusDTO newRecord );
    void deleteUserStatus ( Integer id );
    Integer getUserStatusMaxPKValue ();
    List getAllUserStatus ();

    /*UserType Facede Service*/
    UserTypeDTO getUserType ( Integer id );
    void addUserType ( UserTypeDTO newRecord );
    void updateUserType ( UserTypeDTO newRecord );
    void deleteUserType ( Integer id );
    Integer getUserTypeMaxPKValue ();
    List getAllUserType ();

    /*Country Facade Service*/
    CountryDTO getCountry ( Integer id );
    void addCountry ( CountryDTO newRecord );
    void updateCountry ( CountryDTO newRecord );
    void deleteCountry ( Integer id );
    Integer getCountryMaxPKValue ();
    List getAllCountry ();


    /*SystemParameters Facade Service*/
    SystemParametersDTO getSystemParameters ( String id );
    void addSystemParameters ( SystemParametersDTO newRecord );
    void updateSystemParameters ( SystemParametersDTO newRecord );
    List getAllSystemParameters ();
    
    /*AccessLogType Facade Service*/
    public AccessLogTypeDTO getAccessLogType ( Integer id );
    public void add ( AccessLogTypeDTO newRecord );
    public void updateAccessLogType ( AccessLogTypeDTO newRecord );
    public List getAllAccessLogType ();
    public void deleteAccessLogType ( Integer id );
    public Integer getAccessLogTypeMaxPKValue ();
    


    /*CompanyType Facade Service*/
    public CompanyTypeDTO getCompanyType ( Integer id );
    public void add ( CompanyTypeDTO newRecord );
    public void updateCompanyType ( CompanyTypeDTO newRecord );
    public List getAllCompanyType ();
    public void deleteCompanyType ( Integer id );
    public Integer getCompanyTypeMaxPKValue ();





    /*CompanyPosition Facade Service*/
    public CompanyPositionDTO getCompanyPosition ( Integer id );
    public void add ( CompanyPositionDTO newRecord );
    public void updateCompanyPosition ( CompanyPositionDTO newRecord );
    public List getAllCompanyPosition ();
    public void deleteCompanyPosition ( Integer id );
    public Integer getCompanyPositionMaxPKValue ();
}
