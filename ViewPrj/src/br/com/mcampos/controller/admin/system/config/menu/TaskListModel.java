package br.com.mcampos.controller.admin.system.config.menu;

import br.com.mcampos.controller.admin.users.model.AbstractPagingListModel;

import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;

import br.com.mcampos.dto.security.TaskDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.BaseComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.ListModelExt;
import org.zkoss.zul.event.ListDataEvent;

public class TaskListModel extends AbstractPagingListModel<TaskDTO> implements ListModelExt
{
    private Integer menuId;
    protected static MenuLocator locator;
    private AuthenticationDTO auth;
    List items = null;


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
        try {
            items = getLocator().getMenuTasks( getAuth(), getMenuId() );
        }
        catch ( ApplicationException e ) {
            e = null;
            return 0;
        }
        return ( items != null ? items.size() : 0 );
    }

    protected List getPageData( int itemStartNumber, int pageSize )
    {
        try {
            items = getLocator().getMenuTasks( getAuth(), getMenuId() );
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

    public void sort( Comparator cmpr, boolean ascending )
    {
        Collections.sort( items, new BaseComparator( ascending )
            {
                public int compare( Object o1, Object o2 )
                {
                    if ( o1 instanceof SimpleTableDTO ) {
                        SimpleTableDTO d1 = ( SimpleTableDTO )o1;
                        SimpleTableDTO d2 = ( SimpleTableDTO )o2;
                        int direction = ( d1.getDescription().compareToIgnoreCase( d2.getDescription() ) );
                        return isAscending() ? direction : -direction;
                    }
                    return 0;
                }
            } );
        fireEvent( ListDataEvent.CONTENTS_CHANGED, -1, -1 );
    }
}
