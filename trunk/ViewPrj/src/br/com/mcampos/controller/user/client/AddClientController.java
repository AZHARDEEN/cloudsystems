package br.com.mcampos.controller.user.client;

import br.com.mcampos.controller.commom.user.CompanyController;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientFacadeBean;

public class AddClientController extends CompanyController
{
    private ClientFacadeBean session;


    AddClientController( char c )
    {
        super( c );
    }

    public AddClientController()
    {
        super();
    }

    private ClientFacadeBean getSession()
    {
        if ( session == null )
            session = ( ClientFacadeBean )getRemoteSession( ClientFacadeBean.class );
        return session;
    }

    @Override
    protected Boolean persist()
    {
        if ( super.persist() == false )
            return false;
        CompanyDTO dto = getCurrentDTO();

        getSession();
        return true;
    }
}
