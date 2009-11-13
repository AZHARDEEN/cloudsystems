package br.com.mcampos.controller.admin.users.model;

import br.com.mcampos.dto.user.login.ListLoginDTO;

import java.util.List;

public class LoginPagingListModel extends BaseListModel<ListLoginDTO>
{
    public LoginPagingListModel( int i, int i1 )
    {
        super( i, i1 );
    }

    public LoginPagingListModel()
    {
        super();
    }

    public int getTotalSize()
    {
        Long nRecords;
        
        nRecords = getLocator ().getLoginRecordCount();
        return nRecords.intValue();
    }

    protected List getPageData( int itemStartNumber, int pageSize )
    {
        return getLocator().getLoginByRange( itemStartNumber, pageSize );
    }

}
