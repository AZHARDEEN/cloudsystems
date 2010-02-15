package br.com.mcampos.controller.admin.system.config.menu;

import br.com.mcampos.controller.admin.users.model.AbstractPagingListModel;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.TaskDTO;

import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.business.MenuLocator;

import java.util.List;

public class TaskListModel extends AbstractPagingListModel<TaskDTO>
{
    private Integer menuId;
    protected static MenuLocator locator;
    private AuthenticationDTO auth;


    public TaskListModel( AuthenticationDTO auth, Integer menuId, int i, int i1 )
    {
        setAuth( auth );
        setMenuId( menuId );
        loadPage( i, i1 );
    }

    public TaskListModel( AuthenticationDTO auth, Integer menuId )
    {
        setAuth( auth );
        setMenuId( menuId );
        loadPage( 1, 100 );
    }

    public int getTotalSize()
    {
        List items;

        try {
            items = getLocator().getTasks( getAuth(), getMenuId() );
        }
        catch ( ApplicationException e ) {
            e = null;
            return 0;
        }
        return ( items != null ? items.size() : 0 );
    }

    protected List getPageData( int itemStartNumber, int pageSize )
    {
        List items = null;

        try {
            items = getLocator().getTasks( getAuth(), getMenuId() );
        }
        catch ( ApplicationException e ) {
            e = null;
        }
        return items;
    }

    protected void setMenuId( Integer menuId )
    {
        this.menuId = menuId;
    }

    protected Integer getMenuId()
    {
        return menuId;
    }

    protected static MenuLocator getLocator()
    {
        if ( locator == null )
            locator = new MenuLocator();
        return locator;
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
