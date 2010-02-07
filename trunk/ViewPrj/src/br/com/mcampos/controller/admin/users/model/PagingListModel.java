package br.com.mcampos.controller.admin.users.model;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ListUserDTO;


import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.UsersLocator;

import java.util.Collections;
import java.util.List;

public class PagingListModel extends BaseListModel<ListUserDTO>
{
    private AuthenticationDTO auth;

    public PagingListModel( AuthenticationDTO auth, int startPageNumber, int pageSize )
    {
        super( startPageNumber, pageSize );
        setAuth( auth );
    }

    public PagingListModel()
    {
        super();
    }


    public int getTotalSize()
    {
        try {
            return getLocator().getUserRecordCount( getAuth() );
        }
        catch ( ApplicationException e ) {
            return 0;
        }
    }

    protected List getPageData( int itemStartNumber, int pageSize )
    {
        try {
            return getLocator().getUsersByRange( getAuth(), itemStartNumber, pageSize );
        }
        catch ( ApplicationException e ) {
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
