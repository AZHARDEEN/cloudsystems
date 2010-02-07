package br.com.mcampos.controller.admin.clients;

import br.com.mcampos.controller.admin.users.model.BaseListModel;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.ListUserDTO;

import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

public class BusinessEntityListModel extends BaseListModel<ListUserDTO>
{
    private AuthenticationDTO auth;


    public BusinessEntityListModel( AuthenticationDTO auth )
    {
        super();
        setAuth( auth );
    }

    public BusinessEntityListModel( AuthenticationDTO auth, int activePage, int pageSize )
    {
        super( activePage, pageSize );
        setAuth( auth );
    }

    public int getTotalSize()
    {
        try {
            return getLocator().getMyCompanyCount( getAuth() );
        }
        catch ( ApplicationException e ) {
            return 0;
        }
    }

    protected List getPageData( int itemStartNumber, int pageSize )
    {
        //return getLocator().getBusinessEntityByRange ( getCurentUserId(), itemStartNumber, pageSize );
        return Collections.EMPTY_LIST;
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
