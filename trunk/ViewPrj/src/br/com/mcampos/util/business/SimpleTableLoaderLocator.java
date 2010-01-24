package br.com.mcampos.util.business;


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
import br.com.mcampos.ejb.facade.BasicTableSession;

import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

public class SimpleTableLoaderLocator extends BusinessDelegate
{
	public SimpleTableLoaderLocator()
	{
        super();
	}
    
    /*
     * Para as funções de get and set de session assumiremos que sempre 
     * será retornado um objeto e nunca null.
     * */
    protected BasicTableSession getSessionBean ()
    {
        return ( BasicTableSession ) getEJBSession( BasicTableSession.class );
    }


	public List<AddressTypeDTO> getAddressTypeList()
	{
		return getSessionBean().getAllAddressType ();
	}
    
    public void addAddressType ( AddressTypeDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addAddressType( newRecord );        
    }

    public Integer getMaxAddressTypeId()
    {
        return getSessionBean().getAddressTypeMaxPKValue();
    }

    public void updateAddressType ( AddressTypeDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateAddressType( updateRecord );        
    }

    public void deleteAddressType ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteAddressType( id );
    }

    public AddressTypeDTO getAddressType( Integer id) throws ApplicationException
    {
        return getSessionBean().getAddressType( id );
    }


    public List<StateDTO> getStateList()
    {
        /*
        return getSessionBean().getByCountry( 33 );
        */
        return Collections.emptyList();
    }


    public List<TitleDTO> getGenderTitles( Integer genderId )
    {
        /*
        return getSessionBean ().getTitleFindByGender( genderId );
        */
        return null;
    }
    


    public List<CityDTO> getLocalityByState ( Integer stateId )
    {
        /*
        return getSessionBean().findByState( stateId, 33 );
        */
        return Collections.emptyList();
    }


    public List<ContactTypeDTO> getContactTypeList (  )
    {
        return getSessionBean().getAllContactType();
    }
    

    public void addCivilState ( CivilStateDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addCivilState( newRecord );        
    }

    public List<CivilStateDTO> getCivilStateList()
    {
        return getSessionBean().getAllCivilState();
    }

    public Integer getMaxCivilStateId()
    {
        return getSessionBean().getCivilStateMaxPKValue();
    }

    public void updateCivilState ( CivilStateDTO record ) throws ApplicationException
    {
        getSessionBean().updateCivilState( record );        
    }

    public void deleteCivilState ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteCivilState( id );
    }

    public CivilStateDTO getCivilState( Integer id) throws ApplicationException
    {
        return getSessionBean().getCivilState( id );
    }

    public void addContactType ( ContactTypeDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addContactType( newRecord );        
    }

    public Integer getMaxContactTypeId()
    {
        return getSessionBean().getContactTypeMaxPKValue();
    }

    public void updateContactType ( ContactTypeDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateContactType( updateRecord );        
    }

    public void deleteContactType ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteContactType( id );
    }

    public ContactTypeDTO getContactType( Integer id) throws ApplicationException
    {
        return getSessionBean().getContactType( id );
    }



    public List<UserTypeDTO> getUserTypeList (  )
    {
        return getSessionBean().getAllUserType();
    }

    public void addUserType ( UserTypeDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addUserType( newRecord );        
    }

    public Integer getMaxUserTypeId()
    {
        return getSessionBean().getUserTypeMaxPKValue();
    }

    public void updateUserType ( UserTypeDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateUserType( updateRecord );        
    }

    public void deleteUserType ( Integer id  ) throws ApplicationException
    {
        getSessionBean().deleteUserType( id );
    }

    public UserTypeDTO getUserType( Integer id) throws ApplicationException
    {
        return getSessionBean().getUserType( id );
    }



    public List<GenderDTO> getGenderList()
    {
        return getSessionBean().getAllGender();
    }


    public void addGender ( GenderDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addGender( newRecord );        
    }

    public Integer getMaxGenderId()
    {
        return getSessionBean().getGenderMaxPKValue();
    }

    public void updateGender ( GenderDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateGender( updateRecord );        
    }

    public void deleteGender ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteGender( id );
    }

    public GenderDTO getGender( Integer id ) throws ApplicationException
    {
        return getSessionBean().getGender( id );
    }


    public List<DocumentTypeDTO> getDocumentTypeList()
    {
        return getSessionBean().getAllDocumentType();
    }


    public void addDocumentType ( DocumentTypeDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addDocumentType( newRecord );        
    }

    public Integer getMaxDocumentTypeId()
    {
        return getSessionBean().getDocumentTypeMaxPKValue();
    }

    public void updateDocumentType ( DocumentTypeDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateDocumentType( updateRecord );        
    }

    public void deleteDocumentType ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteDocumentType( id );
    }

    public DocumentTypeDTO getDocumentType( Integer id ) throws ApplicationException
    {
        return getSessionBean().getDocumentType( id );
    }





    public List<TitleDTO> getTitleList()
    {
        return getSessionBean().getAllTitle();
    }


    public void addTitle ( TitleDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addTitle( newRecord );        
    }

    public Integer getMaxTitleId()
    {
        return getSessionBean().getTitleMaxPKValue();
    }

    public void updateTitle ( TitleDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateTitle( updateRecord );        
    }

    public void deleteTitle ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteTitle( id );
    }

    public TitleDTO getTitle( Integer id) throws ApplicationException
    {
        return getSessionBean().getTitle( id );
    }





    public List<UserStatusDTO> getUserStatusList()
    {
        return getSessionBean().getAllUserStatus();
    }


    public void addUserStatus ( UserStatusDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addUserStatus( newRecord );        
    }

    public Integer getMaxUserStatusId()
    {
        return getSessionBean().getUserStatusMaxPKValue();
    }

    public void updateUserStatus ( UserStatusDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateUserStatus( updateRecord );        
    }

    public void deleteUserStatus ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteUserStatus( id );
    }

    public UserStatusDTO getUserStatus( Integer id) throws ApplicationException
    {
        return getSessionBean().getUserStatus( id );
    }





    public List<SystemParametersDTO> getSystemParametersList()
    {
        return ((List<SystemParametersDTO>)getSessionBean().getAllSystemParameters());
    }

    public void addSystemParameters ( SystemParametersDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addSystemParameters( newRecord );        
    }

    public void updateSystemParameters ( SystemParametersDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateSystemParameters( updateRecord );        
    }

    public SystemParametersDTO getSystemParameters( String id) throws ApplicationException
    {
        return getSessionBean().getSystemParameters( id );
    }



    public List<CountryDTO> getCountryList()
    {
        return getSessionBean().getAllCountry();
    }


    public void addCountry ( CountryDTO newRecord ) throws ApplicationException
    {
        getSessionBean().addCountry( newRecord );        
    }

    public Integer getMaxCountryId()
    {
        return getSessionBean().getCountryMaxPKValue();
    }

    public void updateCountry ( CountryDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateCountry( updateRecord );        
    }

    public void deleteCountry ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteCountry( id );
    }

    public CountryDTO getCountry( Integer id) throws ApplicationException
    {
        return getSessionBean().getCountry( id );
    }


    public List<AccessLogTypeDTO> getAccessLogTypeList()
    {
        return ((List<AccessLogTypeDTO>)getSessionBean().getAllAccessLogType());
    }

    public void addAccessLogType ( AccessLogTypeDTO newRecord ) throws ApplicationException
    {
        getSessionBean().add( newRecord );        
    }

    public void updateAccessLogType ( AccessLogTypeDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateAccessLogType( updateRecord );        
    }

    public AccessLogTypeDTO getAccessLogType( Integer id) throws ApplicationException
    {
        return getSessionBean().getAccessLogType( id );
    }
    
    public void deleteAccessLogType ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteAccessLogType( id );
    }

    public Integer getMaxAccessLogTypeId ( )
    {
        return getSessionBean().getAccessLogTypeMaxPKValue();
    }





    public List<CompanyPositionDTO> getCompanyPositionList()
    {
        return ((List<CompanyPositionDTO>)getSessionBean().getAllCompanyPosition());
    }

    public void addCompanyPosition ( CompanyPositionDTO newRecord ) throws ApplicationException
    {
        getSessionBean().add( newRecord );        
    }

    public void updateCompanyPosition ( CompanyPositionDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateCompanyPosition( updateRecord );        
    }

    public CompanyPositionDTO getCompanyPosition( Integer id) throws ApplicationException
    {
        return getSessionBean().getCompanyPosition( id );
    }
    
    public void deleteCompanyPosition ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteCompanyPosition( id );
    }

    public Integer getMaxCompanyPositionId ( )
    {
        return getSessionBean().getCompanyPositionMaxPKValue();
    }





    public List<CompanyTypeDTO> getCompanyTypeList()
    {
        return ((List<CompanyTypeDTO>)getSessionBean().getAllCompanyType());
    }

    public void addCompanyType ( CompanyTypeDTO newRecord ) throws ApplicationException
    {
        getSessionBean().add( newRecord );        
    }

    public void updateCompanyType ( CompanyTypeDTO updateRecord ) throws ApplicationException
    {
        getSessionBean().updateCompanyType( updateRecord );        
    }

    public CompanyTypeDTO getCompanyType( Integer id) throws ApplicationException
    {
        return getSessionBean().getCompanyType( id );
    }
    
    public void deleteCompanyType ( Integer id ) throws ApplicationException
    {
        getSessionBean().deleteCompanyType( id );
    }

    public Integer getMaxCompanyTypeId ( )
    {
        return getSessionBean().getCompanyTypeMaxPKValue();
    }


    public void loadSimpleDTO ( Combobox target, List l, Boolean bDefaultSelected )
    {
        Comboitem comboItem;
        List<DisplayNameDTO> list = (List<DisplayNameDTO>) l;
        
        
        if ( target == null )
            return;
        target.getItems().clear();
        if ( list == null || list.size() == 0 )
            return;
        for ( DisplayNameDTO item : list ) {
            comboItem = target.appendItem( item.getDisplayName() );
            if ( comboItem != null )
                comboItem.setValue( item );
        }
        if ( target.getItemCount() > 0 && bDefaultSelected )
            target.setSelectedIndex( 0 );
    }
    
    public void loadCompanyType ( Combobox target )
    {
        loadSimpleDTO( target, getSessionBean().getAllCompanyType(), true );
    }
}



