package br.com.mcampos.controller.commom.user;


import br.com.mcampos.controller.admin.clients.UserClientController;
import br.com.mcampos.controller.core.PageBrowseHistory;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.CNPJ;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;


import java.util.List;
import java.util.Map;

@SuppressWarnings( { "unchecked", "Unnecessary" } )
public class CompanyClientController extends UserClientController
{
    protected String actionParam;
    private Textbox cnpj;
    private Combobox companyType;
    protected CompanyDTO currentDTO;
    private Textbox name;
    private Textbox nickName;


    public CompanyClientController()
    {
        super();
    }

    public CompanyClientController( char c )
    {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        Map args = execution.getArg();

        if ( args != null ) {
            setActionParam( ( String )args.get( "who" ) );
            setBroseHistory( ( PageBrowseHistory )args.get( PageBrowseHistory.historyParamName ) );
            Integer userId = ( Integer )args.get( "userId" );
            if ( userId != null )
                showInfo( userId );
        }
    }

    protected void showInfo( Integer id ) throws ApplicationException
    {
        showInfo( ( CompanyDTO )getUserLocator().getUser( getLoggedInUser(), id ) );
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

    protected Boolean persist()
    {
        CompanyDTO dto = getCurrentDTO();
        Comboitem item;

        dto.setName( name.getValue() );
        dto.setNickName( nickName.getValue() );
        addAddresses( dto );
        item = companyType.getSelectedItem();

        if ( item != null ) {
            dto.setCompanyType( ( CompanyTypeDTO )item.getValue() );
        }

        addAddresses( dto );
        addContacts( dto );
        addDocuments( dto );

        if ( isBusinessParam() ) {
            //getUserLocator().addBusinessEntity( dto, getLoggedInUser() );
        }
        else {
            try {
                getUserLocator().add( getLoggedInUser(), dto );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
        return true;
    }

    protected void preparePage()
    {
        super.preparePage();
        getSimpleLoader().loadCompanyType( companyType );
    }

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
            UserDTO dto;

            try {
                dto = getUserLocator().getUserByDocument( getLoggedInUser(), UserDocumentDTO.createUserDocumentCNPJ( document ) );
            }
            catch ( ApplicationException e ) {
                e = null;
                dto = null;
            }
            if ( ( dto != null ) && ( dto instanceof CompanyDTO ) ) {
                showInfo( ( CompanyDTO )dto );
            }
        }
    }

    protected void setActionParam( String actionParam )
    {
        this.actionParam = actionParam;
    }

    protected String getActionParam()
    {
        return actionParam;
    }

    protected boolean isParam( String param )
    {
        return ( ( getActionParam() != null ) && getActionParam().equals( param ) );
    }

    protected boolean isMyselfParam()
    {
        return isParam( "myself" );
    }

    protected boolean isBusinessParam()
    {
        return isParam( "businessEntity" );
    }

    protected boolean isNewParam()
    {
        return isParam( "new" );
    }
}

