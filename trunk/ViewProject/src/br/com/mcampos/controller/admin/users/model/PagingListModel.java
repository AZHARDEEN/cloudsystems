package br.com.mcampos.controller.admin.users.model;

import br.com.mcampos.dto.user.ListUserDTO;


import br.com.mcampos.util.business.UsersLocator;

import java.util.List;

public class PagingListModel extends BaseListModel<ListUserDTO>
{
    
    public PagingListModel( int startPageNumber, int pageSize )
    {
        super( startPageNumber, pageSize );
    }

    public PagingListModel()
    {
        super();
    }
    

    public int getTotalSize()
    {
        Long nRecords;
        
        nRecords = getLocator ().getUserRecordCount();
        
        return nRecords.intValue();
    }

    protected List getPageData( int itemStartNumber, int pageSize )
    {
        return getLocator().getUsersByRange( itemStartNumber, pageSize );
    }
}
