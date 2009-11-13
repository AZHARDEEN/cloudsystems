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
    
    public void addAddressType ( AddressTypeDTO newRecord )
    {
        getSessionBean().addAddressType( newRecord );        
    }

    public Integer getMaxAddressTypeId()
    {
        return getSessionBean().getAddressTypeMaxPKValue();
    }

    public void updateAddressType ( AddressTypeDTO updateRecord )
    {
        getSessionBean().updateAddressType( updateRecord );        
    }

    public void deleteAddressType ( Integer id )
    {
        getSessionBean().deleteAddressType( id );
    }

    public AddressTypeDTO getAddressType( Integer id)
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
    

    public void addCivilState ( CivilStateDTO newRecord )
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

    public void updateCivilState ( CivilStateDTO record )
    {
        getSessionBean().updateCivilState( record );        
    }

    public void deleteCivilState ( Integer id )
    {
        getSessionBean().deleteCivilState( id );
    }

    public CivilStateDTO getCivilState( Integer id)
    {
        return getSessionBean().getCivilState( id );
    }

    public void addContactType ( ContactTypeDTO newRecord )
    {
        getSessionBean().addContactType( newRecord );        
    }

    public Integer getMaxContactTypeId()
    {
        return getSessionBean().getContactTypeMaxPKValue();
    }

    public void updateContactType ( ContactTypeDTO updateRecord )
    {
        getSessionBean().updateContactType( updateRecord );        
    }

    public void deleteContactType ( Integer id )
    {
        getSessionBean().deleteContactType( id );
    }

    public ContactTypeDTO getContactType( Integer id)
    {
        return getSessionBean().getContactType( id );
    }



    public List<UserTypeDTO> getUserTypeList (  )
    {
        return getSessionBean().getAllUserType();
    }

    public void addUserType ( UserTypeDTO newRecord )
    {
        getSessionBean().addUserType( newRecord );        
    }

    public Integer getMaxUserTypeId()
    {
        return getSessionBean().getUserTypeMaxPKValue();
    }

    public void updateUserType ( UserTypeDTO updateRecord )
    {
        getSessionBean().updateUserType( updateRecord );        
    }

    public void deleteUserType ( Integer id  )
    {
        getSessionBean().deleteUserType( id );
    }

    public UserTypeDTO getUserType( Integer id)
    {
        return getSessionBean().getUserType( id );
    }



    public List<GenderDTO> getGenderList()
    {
        return getSessionBean().getAllGender();
    }


    public void addGender ( GenderDTO newRecord )
    {
        getSessionBean().addGender( newRecord );        
    }

    public Integer getMaxGenderId()
    {
        return getSessionBean().getGenderMaxPKValue();
    }

    public void updateGender ( GenderDTO updateRecord )
    {
        getSessionBean().updateGender( updateRecord );        
    }

    public void deleteGender ( Integer id )
    {
        getSessionBean().deleteGender( id );
    }

    public GenderDTO getGender( Integer id )
    {
        return getSessionBean().getGender( id );
    }


    public List<DocumentTypeDTO> getDocumentTypeList()
    {
        return getSessionBean().getAllDocumentType();
    }


    public void addDocumentType ( DocumentTypeDTO newRecord )
    {
        getSessionBean().addDocumentType( newRecord );        
    }

    public Integer getMaxDocumentTypeId()
    {
        return getSessionBean().getDocumentTypeMaxPKValue();
    }

    public void updateDocumentType ( DocumentTypeDTO updateRecord )
    {
        getSessionBean().updateDocumentType( updateRecord );        
    }

    public void deleteDocumentType ( Integer id )
    {
        getSessionBean().deleteDocumentType( id );
    }

    public DocumentTypeDTO getDocumentType( Integer id )
    {
        return getSessionBean().getDocumentType( id );
    }





    public List<TitleDTO> getTitleList()
    {
        return getSessionBean().getAllTitle();
    }


    public void addTitle ( TitleDTO newRecord )
    {
        getSessionBean().addTitle( newRecord );        
    }

    public Integer getMaxTitleId()
    {
        return getSessionBean().getTitleMaxPKValue();
    }

    public void updateTitle ( TitleDTO updateRecord )
    {
        getSessionBean().updateTitle( updateRecord );        
    }

    public void deleteTitle ( Integer id )
    {
        getSessionBean().deleteTitle( id );
    }

    public TitleDTO getTitle( Integer id)
    {
        return getSessionBean().getTitle( id );
    }





    public List<UserStatusDTO> getUserStatusList()
    {
        return getSessionBean().getAllUserStatus();
    }


    public void addUserStatus ( UserStatusDTO newRecord )
    {
        getSessionBean().addUserStatus( newRecord );        
    }

    public Integer getMaxUserStatusId()
    {
        return getSessionBean().getUserStatusMaxPKValue();
    }

    public void updateUserStatus ( UserStatusDTO updateRecord )
    {
        getSessionBean().updateUserStatus( updateRecord );        
    }

    public void deleteUserStatus ( Integer id )
    {
        getSessionBean().deleteUserStatus( id );
    }

    public UserStatusDTO getUserStatus( Integer id)
    {
        return getSessionBean().getUserStatus( id );
    }





    public List<SystemParametersDTO> getSystemParametersList()
    {
        return ((List<SystemParametersDTO>)getSessionBean().getAllSystemParameters());
    }

    public void addSystemParameters ( SystemParametersDTO newRecord )
    {
        getSessionBean().addSystemParameters( newRecord );        
    }

    public void updateSystemParameters ( SystemParametersDTO updateRecord )
    {
        getSessionBean().updateSystemParameters( updateRecord );        
    }

    public SystemParametersDTO getSystemParameters( String id)
    {
        return getSessionBean().getSystemParameters( id );
    }



    public List<CountryDTO> getCountryList()
    {
        return getSessionBean().getAllCountry();
    }


    public void addCountry ( CountryDTO newRecord )
    {
        getSessionBean().addCountry( newRecord );        
    }

    public Integer getMaxCountryId()
    {
        return getSessionBean().getCountryMaxPKValue();
    }

    public void updateCountry ( CountryDTO updateRecord )
    {
        getSessionBean().updateCountry( updateRecord );        
    }

    public void deleteCountry ( Integer id )
    {
        getSessionBean().deleteCountry( id );
    }

    public CountryDTO getCountry( Integer id)
    {
        return getSessionBean().getCountry( id );
    }


    public List<AccessLogTypeDTO> getAccessLogTypeList()
    {
        return ((List<AccessLogTypeDTO>)getSessionBean().getAllAccessLogType());
    }

    public void addAccessLogType ( AccessLogTypeDTO newRecord )
    {
        getSessionBean().add( newRecord );        
    }

    public void updateAccessLogType ( AccessLogTypeDTO updateRecord )
    {
        getSessionBean().updateAccessLogType( updateRecord );        
    }

    public AccessLogTypeDTO getAccessLogType( Integer id)
    {
        return getSessionBean().getAccessLogType( id );
    }
    
    public void deleteAccessLogType ( Integer id )
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

    public void addCompanyPosition ( CompanyPositionDTO newRecord )
    {
        getSessionBean().add( newRecord );        
    }

    public void updateCompanyPosition ( CompanyPositionDTO updateRecord )
    {
        getSessionBean().updateCompanyPosition( updateRecord );        
    }

    public CompanyPositionDTO getCompanyPosition( Integer id)
    {
        return getSessionBean().getCompanyPosition( id );
    }
    
    public void deleteCompanyPosition ( Integer id )
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

    public void addCompanyType ( CompanyTypeDTO newRecord )
    {
        getSessionBean().add( newRecord );        
    }

    public void updateCompanyType ( CompanyTypeDTO updateRecord )
    {
        getSessionBean().updateCompanyType( updateRecord );        
    }

    public CompanyTypeDTO getCompanyType( Integer id)
    {
        return getSessionBean().getCompanyType( id );
    }
    
    public void deleteCompanyType ( Integer id )
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



