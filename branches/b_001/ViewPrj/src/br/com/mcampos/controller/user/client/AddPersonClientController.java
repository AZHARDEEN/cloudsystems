package br.com.mcampos.controller.user.client;


import br.com.mcampos.controller.commom.user.PersonController;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientFacade;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;

public class AddPersonClientController extends PersonController
{
    private ClientFacade session;
    private Label labelCompanyRecordTitle;

    public AddPersonClientController( char c )
    {
        super( c );
    }

    public AddPersonClientController()
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

    @Override
    protected String getPageTitle()
    {
        return getLabel( "newPersonClient" );
    }

}

