package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientFacade;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

public class CompanyClientController extends LoggedBaseController
{
    public static final String clientParamName = "client";

    private static final String addClientPage = "/private/user/client/persist_company.zul";
    private static final String updateClientPage = "/private/user/client/update_company.zul";
    private static final String collaboratorPage = "/private/user/collaborator/collaborator_list.zul";
    private static final String rolePage = "/private/user/client/client_role.zul";

    private ClientFacade clientSession;

    private Label labelCompanyClientTitle;
    private Listheader listHeaderCode;
    private Listheader listHeaderName;
    private Listheader headerId;

    private Listbox listboxRecord;


    /**
     * Mapeamento do botão cmdCreate. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdCreate dispara o início do processo de inclusão em um formulário
     */
    private Button cmdCreate;

    /**
     * Mapeamento do botão cmdUpdate. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdUpdate dispara o início do processo de alteração de um registro
     * O registro selecionado em um formulário.
     */
    private Button cmdUpdate;

    /**
     * Mapeamento do botão cmdDelete. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdDelete dispara o início do processo de exclusão de um registro em um formulário.
     */
    private Button cmdDelete;

    /**
     * Mapeamento do botão cmdRefresh. O nome do botão NÃO pode ser alterado no formulário WEB.
     * O evento onClick$cmdRefresh dispara o processo de atualização geral.
     */
    private Button cmdRefresh;

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
        listboxRecord.setItemRenderer( new ClientListRenderer() );
        configureLabels();
        refresh();
    }

    private void configureLabels()
    {
        setLabel( labelCompanyClientTitle );
        setLabel( listHeaderCode );
        setLabel( listHeaderName );

        setLabel( headerId );

        setLabel( cmdCreate );
        setLabel( cmdUpdate );
        setLabel( cmdDelete );
        setLabel( cmdRefresh );
        setLabel( cmdCollaborator );

        setLabel( cmdUploadLogo );
        setLabel( cmdManageRoles );
    }

    public ClientFacade getSession()
    {
        if ( clientSession == null )
            clientSession = ( ClientFacade )getRemoteSession( ClientFacade.class );
        return clientSession;
    }

    private void refresh()
    {
        if ( listboxRecord == null )
            return;
        ListModelList model = ( ListModelList )listboxRecord.getModel();
        if ( model == null ) {
            model = new ListModelList();
            listboxRecord.setModel( model );
        }
        model.clear();
        try {
            model.addAll( getSession().getCompanies( getLoggedInUser() ) );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    public void onClick$cmdRefresh()
    {
        refresh();
    }

    public void onClick$cmdCreate()
    {
        loadCompanyRecordPage( addClientPage );
    }

    public void onClick$cmdUpdate()
    {
        Object item = getCurrentRecord();

        if ( item == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        setParameter( "client", item );
        loadCompanyRecordPage( updateClientPage );
    }

    private void loadCompanyRecordPage( String page )
    {
        gotoPage( page, getRootParent().getParent() );
    }


    public void onClick$cmdDelete() throws ApplicationException
    {
        Object item = getCurrentRecord();

        if ( item == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        ClientDTO dto = ( ClientDTO )item;
        getSession().delete( getLoggedInUser(), dto );
        ListModelList model = ( ListModelList )getListbox().getModel();
        if ( model != null )
            model.remove( dto );
    }

    public void onClick$cmdCollaborator() throws ApplicationException
    {
        if ( getListbox().getSelectedItem() == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        ClientDTO client = ( ClientDTO )getListbox().getSelectedItem().getValue();
        CompanyDTO company = getSession().get( getLoggedInUser(), client.getCompanyId() );
        setParameter( clientParamName, company );
        gotoPage( collaboratorPage, getRootParent().getParent() );
    }

    public void onClick$cmdManageRoles() throws ApplicationException
    {
        if ( getListbox().getSelectedItem() == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        ClientDTO client = ( ClientDTO )getListbox().getSelectedItem().getValue();
        if ( client != null ) {
            CompanyDTO company = getSession().get( getLoggedInUser(), client.getCompanyId() );
            if ( company != null ) {
                setParameter( clientParamName, company );
                gotoPage( rolePage, getRootParent().getParent() );
            }
        }
    }

    public void onClick$cmdUploadLogo()
    {
        if ( getListbox().getSelectedItem() == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        try {
            final Window winLogo = ( Window )Executions.createComponents( "/private/user/client/update_logo.zul", null, null );
            winLogo.setAttribute( "client", getListbox().getSelectedItem().getValue() );
            winLogo.doModal();
            if ( winLogo != null )
                winLogo.detach();

        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    private Object getCurrentRecord()
    {
        Listitem currentItem = getListbox().getSelectedItem();
        Object item = null;

        if ( currentItem != null )
            item = currentItem.getValue();
        return item;
    }

    private Listbox getListbox()
    {
        return listboxRecord;
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

