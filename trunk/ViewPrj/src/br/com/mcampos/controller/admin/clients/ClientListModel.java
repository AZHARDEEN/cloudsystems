package br.com.mcampos.controller.admin.clients;

import br.com.mcampos.controller.admin.users.model.BaseListModel;

import br.com.mcampos.dto.security.AuthenticationDTO;

import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

public class ClientListModel extends BaseListModel
{
    private AuthenticationDTO auth;

    public ClientListModel()
    {
        super();
    }

    public ClientListModel( AuthenticationDTO auth, int i, int i1 )
    {
        super( i, i1 );
        setAuth( auth );
    }

    public int getTotalSize()
    {
        try {
            return getLocator().getClientRecordCount( getAuth(), 4 );
        }
        catch ( ApplicationException e ) {
            e = null;
            return 0;
        }
    }

    protected List getPageData( int itemStartNumber, int pageSize )
    {
        try {
            return getLocator().getUsersByRange( getAuth(), itemStartNumber, pageSize );
        }
        catch ( ApplicationException e ) {
            e = null;
            return Collections.EMPTY_LIST;
        }
    }

    protected void setAuth( AuthenticationDTO auth )
    {
        this.auth = auth;
    }

    protected AuthenticationDTO getAuth()
    {
        return auth;
    }
}
