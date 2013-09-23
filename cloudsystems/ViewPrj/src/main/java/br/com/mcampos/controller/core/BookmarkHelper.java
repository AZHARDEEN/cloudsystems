package br.com.mcampos.controller.core;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Map;

import org.zkoss.zk.ui.Component;

public class BookmarkHelper implements Serializable
{
    protected ArrayList<PageBrowseHistory> history;

    public BookmarkHelper()
    {
        super();
    }

    public ArrayList<PageBrowseHistory> get()
    {
        if ( history == null )
            history = new ArrayList<PageBrowseHistory>();
        return history;
    }

    public void clear()
    {
        get().clear();
    }

    public PageBrowseHistory get( int nIndex )
    {
        if ( nIndex < 0 || nIndex > get().size() )
            return null;
        return get().get( nIndex );
    }

    public void add( String uri, Component parent, Map parameters )
    {
        if ( uri == null )
            return;
        add( new PageBrowseHistory( uri, parent, parameters ) );
    }

    public void add( PageBrowseHistory history )
    {
        if ( history != null )
            get().add( history );
    }
}
