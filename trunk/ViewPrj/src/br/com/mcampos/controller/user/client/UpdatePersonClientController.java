package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.commom.user.PersonController;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientFacade;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Label;

public class UpdatePersonClientController extends PersonController
{
    private ClientFacade session;
    private Label labelCompanyRecordTitle;
    private ClientDTO currentClient;

    public UpdatePersonClientController( char c )
    {
        super( c );
    }

    public UpdatePersonClientController()
    {
        super();
    }

    private ClientFacade getSession()
    {
        if ( session == null )
            session = ( ClientFacade )getRemoteSession( ClientFacade.class );
        return session;
    }

    @Override
    protected Boolean persist() throws ApplicationException
    {
        if ( super.persist() == false )
            return false;
        getSession().update( getLoggedInUser(), currentClient.getCompanyId(), getCurrentDTO() );
        return true;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        PersonDTO person = getSession().getPerson( getLoggedInUser(), currentClient.getSequence() );
        showInfo( person );
        setLabel( labelCompanyRecordTitle );
        cpf.setDisabled( true );
    }


    @Override
    public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
    {
        currentClient = ( ClientDTO )getParameter( "client" );
        if ( currentClient != null )
            return super.doBeforeCompose( page, parent, compInfo );
        else
            return null;
    }

    protected PersonDTO searchByDocument( String document, Integer type )
    {
        return null;
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel ("updatePersonClient");
    }
}
