package br.com.mcampos.controller.user.client;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.dto.user.CompanyDTO;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;


public class CompanyClientController extends ClientBaseController
{
    public static final String clientParamName = "client";

    private static final String addClientPage = "/private/user/client/persist_company.zul";
    private static final String updateClientPage = "/private/user/client/update_company.zul";
    private static final String collaboratorPage = "/private/user/collaborator/collaborator_list.zul";
    private static final String rolePage = "/private/user/client/client_role.zul";

    private Label labelCompanyClientTitle;

    private Button cmdCollaborator;

    private Button cmdUploadLogo;

    private Button cmdManageRoles;


    public CompanyClientController( char c )
    {
        super( c );
    }

    public CompanyClientController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component component ) throws Exception
    {
        super.doAfterCompose( component );
        configureLabels();
    }

    private void configureLabels()
    {
        setLabel( labelCompanyClientTitle );
        setLabel( cmdCollaborator );
        setLabel( cmdUploadLogo );
        setLabel( cmdManageRoles );
    }


    @Override
    protected List getList() throws ApplicationException
    {
        return getSession().getCompanies( getLoggedInUser() );
    }


    public void onClick$cmdCreate()
    {
        loadCompanyRecordPage( addClientPage );
    }

    public void update()
    {
        loadCompanyRecordPage( updateClientPage );
    }

    private void loadCompanyRecordPage( String page )
    {
        gotoPage( page, getRootParent().getParent() );
    }


    public void onClick$cmdCollaborator() throws ApplicationException
    {
        if ( getListboxRecord().getSelectedItem() == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        ClientDTO client = ( ClientDTO )getListboxRecord().getSelectedItem().getValue();
        CompanyDTO company = getSession().getCompany( getLoggedInUser(), client.getSequence() );
        setParameter( clientParamName, company );
        gotoPage( collaboratorPage, getRootParent().getParent() );
    }

    public void onClick$cmdManageRoles() throws ApplicationException
    {
        if ( getListboxRecord().getSelectedItem() == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        ClientDTO client = ( ClientDTO )getListboxRecord().getSelectedItem().getValue();
        if ( client != null ) {
            CompanyDTO company = getSession().getCompany( getLoggedInUser(), client.getSequence() );
            if ( company != null ) {
                setParameter( clientParamName, company );
                gotoPage( rolePage, getRootParent().getParent() );
            }
        }
    }

    public void onClick$cmdUploadLogo()
    {
        if ( getListboxRecord().getSelectedItem() == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        try {
            final Window winLogo = ( Window )Executions.createComponents( "/private/user/client/update_logo.zul", null, null );
            winLogo.setAttribute( "client", getListboxRecord().getSelectedItem().getValue() );
            winLogo.doModal();
            if ( winLogo != null )
                winLogo.detach();

        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    public void onSelect$listboxRecord() throws ApplicationException
    {
        Object item = getCurrentRecord();
        if ( item == null )
            return;
        ClientDTO dto = ( ClientDTO )item;
        MediaDTO logo = getSession().getLogo( getLoggedInUser(), dto );
        if ( logo != null )
            setClientLogo( logo.getObject() );
    }


}


