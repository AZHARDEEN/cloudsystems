package br.com.mcampos.web.core.bookmark;

import java.io.Serializable;

import java.util.ArrayList;


public class BookmarkSession implements Serializable
{
	private static final long serialVersionUID = 8084516862720739363L;
	public static final String parameterName = "Bookmark";
    private ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>( 5 );
    private Integer nIndex = -1;


    public BookmarkSession()
    {
        super();
    }

    public Bookmark getPrevious()
    {
        nIndex--;
        return bookmarks.get( nIndex );
    }

    public Bookmark getNext()
    {
        nIndex++;
        return bookmarks.get( nIndex );
    }

    public Bookmark get( Integer nIndex )
    {
        if ( nIndex < 0 || nIndex >= bookmarks.size() )
            return null;
        this.nIndex = nIndex;
        return bookmarks.get( this.nIndex );
    }

    public Integer set( Bookmark bookmark )
    {
        ++nIndex;
        if ( nIndex < bookmarks.size() )
            bookmarks.set( nIndex, bookmark );
        else
            bookmarks.add( nIndex, bookmark );
        return nIndex;
    }

}
