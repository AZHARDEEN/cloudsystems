package br.com.mcampos.controller.user.client;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.client.facade.ClientRoleFacade;
import br.com.mcampos.util.system.SimpleSearchListBox;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;


public class ClientSearchBox extends SimpleSearchListBox
{
    private AuthenticationDTO auth;
    private ClientRoleFacade session;

    public ClientSearchBox( AuthenticationDTO auth, Component parent )
    {
        super( "Clientes", "normal", true );
        this.auth = auth;
        setParent( parent );
        _title = "Clientes";
        _listHeader1 = "Nome";
        createBox();
    }


    protected ClientRoleFacade getSession()
    {
        if ( session == null )
            session = ( ClientRoleFacade )getRemoteSession( ClientRoleFacade.class );
        return session;
    }


    @Override
    protected List getList()
    {
        try {
            return getSession().getClients( auth );
        }
        catch ( ApplicationException e ) {
            return Collections.emptyList();
        }
    }


    public static Object show( AuthenticationDTO auth, Component parent )
    {
        return new ClientSearchBox( auth, parent ).getSelected();
    }
}
