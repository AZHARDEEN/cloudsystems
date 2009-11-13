package br.com.mcampos.controller.admin.clients;

import br.com.mcampos.controller.admin.users.model.BaseListModel;

import java.util.Collections;
import java.util.List;

public class ClientListModel extends BaseListModel
{
    public ClientListModel()
    {
        super();
    }

    public ClientListModel( int i, int i1 )
    {
        super( i, i1 );
    }

    public int getTotalSize()
    {
        Long nRecords;
        nRecords = getLocator ().getClientRecordCount( 4 );
        return nRecords.intValue();
    }

    protected List getPageData( int itemStartNumber, int pageSize )
    {
        return getLocator().getUsersByRange( itemStartNumber, pageSize );
    }
}
