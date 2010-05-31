package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.client.ClientFacade;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;

public class CompanyClientController extends LoggedBaseController
{
    private ClientFacade clientSession;

    private Label labelCompanyClientTitle;
    private Listheader listHeaderCode;
    private Listheader listHeaderName;
    private Listheader headerId;

    private Listbox listboxRecord;

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
    }

    public ClientFacade getSession()
    {
        if ( clientSession == null )
            clientSession = ( ClientFacade )getRemoteSession( ClientFacade.class );
        return clientSession;
    }

    private void refresh() throws ApplicationException
    {
        if ( listboxRecord == null )
            return;
        ListModelList model = ( ListModelList )listboxRecord.getModel();
        if ( model == null ) {
            model = new ListModelList();
            listboxRecord.setModel( model );
        }
        model.clear();
        model.addAll( getSession().getCompanies( getLoggedInUser() ) );
    }
}
