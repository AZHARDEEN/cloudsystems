package br.com.mcampos.controller.commom.user;


import br.com.mcampos.controller.admin.clients.UserController;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.company.facade.CompanyFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.CNPJ;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;


@SuppressWarnings( { "unchecked", "Unnecessary" } )
public abstract class CompanyController extends UserController
{
    private CompanyFacade session;

    protected Textbox cnpj;
    protected Combobox companyType;
    protected CompanyDTO currentDTO;
    protected Textbox name;
    protected Textbox nickName;

    protected abstract CompanyDTO searchByDocument( String document, Integer type ) throws Exception;


    public CompanyController()
    {
        super();
    }

    public CompanyController( char c )
    {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        loadCombobox( companyType, getSession().getCompanyTypes() );
        //debugInfo();
    }

    private void debugInfo()
    {
        name.setValue( "Teste de razao social" );
        nickName.setValue( "teste" );
        companyType.setValue( "Sociedade Empresária Limitada" );
        zip.setValue( "70000000" );
        address.setValue( "Qualquer endereço" );
        hood.setValue( "ASA SUL" );
        addressComment.setValue( "debug comment" );
        cnpj.setValue( "33444555000181" );
    }

    public void setCurrentDTO( CompanyDTO currentDTO )
    {
        this.currentDTO = currentDTO;
    }

    public CompanyDTO getCurrentDTO()
    {
        if ( currentDTO == null ) {
            currentDTO = new CompanyDTO();
        }

        return currentDTO;
    }

    protected void showInfo( CompanyDTO dto )
    {
        setCurrentDTO( dto );
        getCmdSubmit().setLabel( "Atualizar" );
        name.setValue( dto.getName() );
        nickName.setValue( dto.getNickName() );
        findCompanyType( dto.getCompanyType() );
        showDocuments( dto.getDocumentList() );
        showAddresses( dto.getAddressList() );
        showContacts( dto.getContactList() );
    }

    protected void findCompanyType( CompanyTypeDTO dto )
    {
        if ( dto != null ) {
            List<Comboitem> comboList;
            CompanyTypeDTO item;

            comboList = ( List<Comboitem> )companyType.getItems();
            for ( Comboitem comboItem : comboList ) {
                item = ( CompanyTypeDTO )comboItem.getValue();
                if ( item.equals( dto ) ) {
                    companyType.setSelectedItem( comboItem );
                    break;
                }
            }
        }
    }

    @Override
    protected Boolean persist() throws ApplicationException
    {
        CompanyDTO dto = getCurrentDTO();
        Comboitem item;

        dto.setName( name.getValue() );
        dto.setNickName( nickName.getValue() );
        item = companyType.getSelectedItem();
        if ( item != null ) {
            dto.setCompanyType( ( CompanyTypeDTO )item.getValue() );
        }
        addAddresses( dto );
        addContacts( dto );
        addDocuments( dto );
        return true;
    }


    @Override
    protected Boolean validate()
    {
        String sName;

        sName = name.getValue();

        if ( sName.isEmpty() ) {
            showErrorMessage( "A razão social deve estar preenchido obrigatoriamente." );
            name.focus();

            return false;
        }

        sName = nickName.getValue();

        if ( sName.isEmpty() ) {
            showErrorMessage( "O nome de fantasia deve estar preenchido obrigatoriamente." );
            nickName.focus();

            return false;
        }

        if ( companyType.getSelectedItem() == null ) {
            showErrorMessage( "O tipo da companhia deve estar selecionada." );
            companyType.focus();

            return false;
        }

        if ( validateCNPJ() == false ) {
            return false;
        }

        return super.validate();
    }

    protected boolean validateCNPJ()
    {
        String sData;
        boolean bRet;

        sData = cnpj.getValue();
        sData = sData.replaceAll( "[.,\\-/ ]", "" );
        bRet = CNPJ.isValid( sData );

        if ( bRet == false ) {
            showErrorMessage( "O CNPJ está inválido" );
            cnpj.setFocus( true );
        }

        return bRet;
    }

    protected void showDocuments( List<UserDocumentDTO> dto )
    {
        getDocumentList().getItems().clear();

        for ( UserDocumentDTO item : dto ) {
            if ( item.getDocumentType().getId().equals( DocumentTypeDTO.typeCNPJ ) ) {
                cnpj.setValue( item.getCode() );
            }
            else {
                insertDocument( item.getCode(), item.getAdditionalInfo(), item.getDocumentType() );
            }
        }
    }

    @Override
    protected void addDocuments( UserDTO user )
    {
        super.addDocuments( user );

        UserDocumentDTO dto = new UserDocumentDTO();

        dto.setCode( cnpj.getValue() );
        dto.setDocumentType( new DocumentTypeDTO( DocumentTypeDTO.typeCNPJ, null, null ) );
        user.add( dto );
    }

    public void onBlur$cnpj()
    {
        String document;

        document = cnpj.getValue();

        if ( ( document != null ) && ( document.isEmpty() == false ) ) {
            try {
                CompanyDTO dto = searchByDocument( document, DocumentTypeDTO.typeCNPJ );
                if ( dto != null ) {
                    showInfo( ( CompanyDTO )dto );
                }
            }
            catch ( Exception e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }


    protected List<StateDTO> getStates( CountryDTO country )
    {
        try {
            return getSession().getStates( country );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
            return Collections.emptyList();
        }
    }


    protected List<CityDTO> getCities( StateDTO state )
    {
        try {
            return getSession().getCities( state );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
            return Collections.emptyList();
        }
    }

    private CompanyFacade getSession()
    {
        if ( session == null )
            session = ( CompanyFacade )getRemoteSession( CompanyFacade.class );
        return session;
    }
}

