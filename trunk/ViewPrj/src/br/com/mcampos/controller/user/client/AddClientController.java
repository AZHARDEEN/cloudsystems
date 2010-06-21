package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.commom.user.CompanyController;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientFacade;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;

public class AddClientController extends CompanyController
{
    private ClientFacade session;
    private Label labelCompanyRecordTitle;


    AddClientController( char c )
    {
        super( c );
    }

    public AddClientController()
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
        getSession().add( getLoggedInUser(), getCurrentDTO() );
        return true;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelCompanyRecordTitle );
    }


}
