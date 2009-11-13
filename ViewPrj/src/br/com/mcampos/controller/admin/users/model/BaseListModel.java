package br.com.mcampos.controller.admin.users.model;

import br.com.mcampos.util.business.UsersLocator;

public abstract class BaseListModel<T> extends AbstractPagingListModel<T>
{
    protected static UsersLocator locator = null;
    
    
    public BaseListModel( int i, int i1 )
    {
        super( i, i1 );
    }

    public BaseListModel()
    {
        super();
    }

    public static void setLocator( UsersLocator locator )
    {
        LoginPagingListModel.locator = locator;
    }

    public static UsersLocator getLocator()
    {
        if ( locator == null )
            locator = new UsersLocator();    
        
        return locator;
    }
}
