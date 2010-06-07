package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientFacade;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;

public class CompanyClientController extends LoggedBaseController
{
    private static final String addClientPage = "/private/user/client/persist_company.zul";

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
        loadCompanyRecordPage();
    }

    public void onClick$cmdUpdate()
    {
        Object item = getCurrentRecord();

        if ( item == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
        }
        loadCompanyRecordPage();
    }

    private void loadCompanyRecordPage()
    {
        gotoPage( addClientPage, getRootParent().getParent() );
    }


    public void onClick$cmdDelete()
    {
        Object item = getCurrentRecord();

        if ( item == null ) {
            showErrorMessage( getLabel( "noCurrentRecordMessage" ) );
            return;
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
}


