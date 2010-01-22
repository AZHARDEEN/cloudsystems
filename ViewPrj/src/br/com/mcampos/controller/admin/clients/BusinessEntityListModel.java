package br.com.mcampos.controller.admin.clients;

import br.com.mcampos.controller.admin.users.model.BaseListModel;

import br.com.mcampos.dto.user.ListUserDTO;

import java.util.Collections;
import java.util.List;

public class BusinessEntityListModel extends BaseListModel<ListUserDTO>
{
    protected Integer curentUserId;
    
    
    public BusinessEntityListModel( Integer user )
    {
        super();
        setCurentUserId(user);
    }

    public BusinessEntityListModel( Integer user, int activePage, int pageSize )
    {
        super ( activePage, pageSize );
        setCurentUserId( user );
    }

    public int getTotalSize()
    {
        //Long nRecords;
        //nRecords = getLocator ().getBusinessEntityCount( getCurentUserId() );
        return 0; //nRecords.intValue();
    }

    protected List getPageData( int itemStartNumber, int pageSize )
    {
        //return getLocator().getBusinessEntityByRange ( getCurentUserId(), itemStartNumber, pageSize );
        return Collections.EMPTY_LIST;
    }

    public void setCurentUserId( Integer curentUserId )
    {
        this.curentUserId = curentUserId;
    }

    public Integer getCurentUserId()
    {
        return curentUserId;
    }
}
