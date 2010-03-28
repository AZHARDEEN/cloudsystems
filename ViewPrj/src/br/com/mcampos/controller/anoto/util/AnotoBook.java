package br.com.mcampos.controller.anoto.util;


import com.anoto.api.Page;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class AnotoBook implements Serializable
{
    Iterator book;
    List<Page> pages;

    public AnotoBook()
    {
        super();
    }

    public AnotoBook ( Iterator book )
    {
        super ();
        setBook( book );
    }

    protected void setBook( Iterator book )
    {
        this.book = book;
        while ( book != null && book.hasNext() )
        {
            Object obj = book.next();
            addPage( (Page) obj );
        }
    }

    public Iterator getBook()
    {
        return book;
    }

    public List<Page> getPages()
    {
        if ( pages == null )
            pages = new ArrayList<Page> ();
        return pages;
    }

    public void addPage ( Page page )
    {
        getPages().add( page );
    }

    public int getPageCount()
    {
        return getPages().size();
    }

}
