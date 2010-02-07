package br.com.mcampos.controller.commom.user;

import br.com.mcampos.controller.admin.clients.UserClientController;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.CPF;


import java.sql.Timestamp;

import java.util.Date;
import java.util.List;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

@SuppressWarnings( { "unchecked", "Unnecessary" } )
public class PersonClientController extends UserClientController
{
    private Textbox name;
    private Textbox cpf;
    private Combobox gender;
    private Combobox title;
    private Combobox maritalStatus;
    private Datebox birthdate;
    private Textbox fatherName;
    private Textbox motherName;
    private Combobox bornState;
    private Combobox bornCity;

    private PersonDTO currentDTO;


    /*
     * TODO: implementar dependentes ao cadastro.
     */

    public PersonClientController( char c )
    {
        super( c );
    }

    public PersonClientController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        String action = null;
        Map map = Executions.getCurrent().getArg();

        if ( map != null )
            action = ( String )map.get( "who" );
        if ( SysUtils.isEmpty( action ) )
            action = Executions.getCurrent().getParameter( "who" );
        if ( action != null ) {
            if ( action.equals( "myself" ) ) {
                showInfo( getLoggedInUser().getUserId() );
            }
            else if ( action.equals( "new" ) ) {
                clearParameter( "client_id" );
            }
        }
        else {
            Integer id = ( Integer )getSessionAttribute( "client_id" );
            if ( id != null ) {
                clearParameter( "client_id" );
                showInfo( id );
            }
        }
    }


    protected void preparePage()
    {
        super.preparePage();

        getSimpleLoader().loadStates( bornState );
        if ( bornState.getSelectedIndex() > 0 )
            onSelect$bornState();
        //getSimpleLoader().loadContactType( contactType );
        getSimpleLoader().loadGenders( gender );
        if ( gender.getSelectedIndex() >= 0 ) {
            onSelect$gender();
        }
        getSimpleLoader().loadCivilStates( maritalStatus );
    }

    protected void showInfo( Integer id ) throws ApplicationException
    {
        PersonDTO dto = getUserLocator().getPerson( getLoggedInUser(), id );
        if ( dto != null )
            showInfo( dto );
    }

    protected void showInfo( PersonDTO dto ) throws ApplicationException
    {
        setCurrentDTO( dto );
        getCmdSubmit().setLabel( "Atualizar" );


        name.setValue( dto.getName() );
        if ( dto.getBirthDate() != null ) {
            Date birth = new Date();
            birth.setTime( dto.getBirthDate().getTime() );
            birthdate.setValue( birth );
        }
        showDocuments( dto.getDocumentList() );
        showAddresses( dto.getAddressList() );
        showContacts( dto.getContactList() );
        findGenderComboitem( dto.getGender() );
        findTitleComboitem( dto.getTitle() );
        findCivilStateComboitem( dto.getCivilState() );
        findBithCityComboitem( dto.getBornCity() );
        fatherName.setValue( dto.getFatherName() );
        motherName.setValue( dto.getMotherName() );
    }


    protected void addDocuments( UserDTO user )
    {
        super.addDocuments( user );

        UserDocumentDTO dto;


        dto = new UserDocumentDTO();
        dto.setDocumentType( new DocumentTypeDTO( DocumentTypeDTO.typeCPF, null, null ) );
        dto.setCode( cpf.getValue() );
        user.add( dto );
    }


    protected Boolean validate()
    {

        if ( super.validate() == false )
            return false;

        String sName;


        sName = name.getValue();
        if ( sName.isEmpty() ) {
            showErrorMessage( "O nome completo deve estar preenchido obrigatoriamente." );
            name.focus();
            return false;
        }
        if ( validateCPF() == false )
            return false;
        return true;
    }


    protected Boolean persist()
    {
        AuthenticationDTO currentUser;
        PersonDTO dto = getCurrentDTO();


        dto.setName( name.getValue() );
        dto.setBirthDate( new Timestamp( birthdate.getValue().getTime() ) );
        dto.setBornCity( bornCity.getSelectedItem() != null ? ( CityDTO )bornCity.getSelectedItem().getValue() : null );
        dto.setCivilState( maritalStatus.getSelectedItem() != null ? ( CivilStateDTO )maritalStatus.getSelectedItem().getValue() : null );
        dto.setFatherName( fatherName.getValue() );
        dto.setGender( gender.getSelectedItem() != null ? ( GenderDTO )gender.getSelectedItem().getValue() : null );
        dto.setMotherName( motherName.getValue() );
        dto.setNickName( name.getName() );
        dto.setTitle( title.getSelectedItem() != null ? ( TitleDTO )title.getSelectedItem().getValue() : null );
        addAddresses( dto );
        addContacts( dto );
        addDocuments( dto );

        try {
            getUserLocator().add( getLoggedInUser(), dto );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
        currentUser = getLoggedInUser();
        if ( getLoginLocator().getStatus( currentUser ) == UserStatusDTO.statusFullfillRecord ) {
            getLoginLocator().setStatus( currentUser, UserStatusDTO.statusOk );
            setLoggedInUser( currentUser );
            Executions.sendRedirect( "/private/index.zul" );
            return false;
        }
        return true;
    }

    public void setMaritalStatus( Combobox maritalStatus )
    {
        this.maritalStatus = maritalStatus;
    }

    public Combobox getMaritalStatus()
    {
        return maritalStatus;
    }


    public void onSelect$gender()
    {
        Comboitem item;

        item = gender.getSelectedItem();
        if ( item == null )
            return;

        GenderDTO dto = ( GenderDTO )item.getValue();
        if ( dto != null ) {
            getSimpleLoader().loadSimpleDTO( title, dto.getTitles(), true );
            title.setFocus( true );
        }
    }


    public void onSelect$bornState()
    {
        Comboitem item;

        item = bornState.getSelectedItem();
        if ( item == null )
            return;
        StateDTO dto = ( StateDTO )item.getValue();
        if ( dto != null ) {
            try {
                getSimpleLoader().loadCities( bornCity, dto.getCountryId(), dto.getId() );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }


    protected void findGenderComboitem( GenderDTO targetDTO )
    {
        List<Comboitem> comboList;
        GenderDTO item;

        if ( targetDTO == null )
            return;
        comboList = ( List<Comboitem> )gender.getItems();
        for ( Comboitem comboItem : comboList ) {
            item = ( GenderDTO )comboItem.getValue();
            if ( item.compareTo( targetDTO ) == 0 ) {
                gender.setSelectedItem( comboItem );
                getSimpleLoader().loadSimpleDTO( title, item.getTitles(), false );
                break;
            }
        }
    }

    protected void findTitleComboitem( TitleDTO targetDTO )
    {
        List<Comboitem> comboList;
        TitleDTO item;

        if ( targetDTO == null )
            return;
        comboList = ( List<Comboitem> )title.getItems();
        for ( Comboitem comboItem : comboList ) {
            item = ( TitleDTO )comboItem.getValue();
            if ( item.compareTo( targetDTO ) == 0 ) {
                title.setSelectedItem( comboItem );
                break;
            }
        }
    }

    protected void findCivilStateComboitem( CivilStateDTO targetDTO )
    {
        List<Comboitem> comboList;
        CivilStateDTO item;

        if ( targetDTO == null )
            return;
        comboList = ( List<Comboitem> )maritalStatus.getItems();
        for ( Comboitem comboItem : comboList ) {
            item = ( CivilStateDTO )comboItem.getValue();
            if ( item.compareTo( targetDTO ) == 0 ) {
                maritalStatus.setSelectedItem( comboItem );
                break;
            }
        }
    }


    protected void findBithCityComboitem( CityDTO dto ) throws ApplicationException
    {
        if ( dto != null ) {
            findStateComboitem( dto.getState(), bornState, bornCity );
            findCityComboitem( dto, bornCity );
        }
    }


    protected void showDocuments( List<UserDocumentDTO> dto )
    {
        getDocumentList().getItems().clear();
        for ( UserDocumentDTO item : dto ) {
            if ( item.getDocumentType().getId().equals( DocumentTypeDTO.typeCPF ) )
                cpf.setValue( item.getCode() );
            else {
                insertDocument( item.getCode(), item.getAdditionalInfo(), item.getDocumentType() );
            }
        }
    }

    public void setCurrentDTO( PersonDTO currentDTO )
    {
        this.currentDTO = currentDTO;
    }

    public PersonDTO getCurrentDTO()
    {
        if ( currentDTO == null )
            currentDTO = new PersonDTO();
        return currentDTO;
    }


    protected boolean validateCPF()
    {
        String sCPF;
        boolean bRet;

        sCPF = cpf.getValue();
        sCPF = sCPF.replaceAll( "[.,\\- ]", "" );
        bRet = CPF.isValid( sCPF );
        if ( bRet == false ) {
            showErrorMessage( "O CPF está inválido" );
            cpf.setFocus( true );
        }
        return bRet;
    }
}
