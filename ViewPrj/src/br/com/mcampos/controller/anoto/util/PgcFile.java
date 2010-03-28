package br.com.mcampos.controller.anoto.util;


import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;
import br.com.mcampos.util.system.UploadMedia;

import com.anoto.api.NoSuchPermissionException;
import com.anoto.api.Page;
import com.anoto.api.PageException;
import com.anoto.api.Pen;
import com.anoto.api.PenCreationException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.event.UploadEvent;


public class PgcFile
{
    protected List<MediaDTO> pgcs;
    protected String penId;
    protected AnodeFacade session;
    protected PGCDTO currentPgc;
    protected Pen currentPen;
    protected PadFile pad;
    protected byte[] bytePgc;

    public PgcFile()
    {
        super();
    }

    protected static com.anoto.api.Pen getPgcPenObject( byte[] pgcByteArray )
    {
        ByteArrayInputStream is = new ByteArrayInputStream( pgcByteArray );
        com.anoto.api.Pen pen = null;
        try {
            pen = com.anoto.api.PenHome.read( is );
        }
        catch ( PenCreationException e ) {
            System.err.println( e.getMessage() );
            pen = null;
        }
        return pen;
    }

    protected void setPenId( String id )
    {
        penId = id;
    }

    public String getPenId()
    {
        return penId;
    }

    public static String getPageAddress( byte[] pgc )
    {
        Pen pen = getPgcPenObject( pgc );
        Iterator it = pen.getPageAddresses();
        if ( it != null && it.hasNext() )
            return ( String )it.next();
        else
            return null;
    }

    public void uploadPgc( UploadEvent evt ) throws IOException, NoSuchPermissionException
    {
        MediaDTO dto;
        dto = UploadMedia.getMedia( evt.getMedia() );
        uploadPgc( dto );
    }


    public void uploadPgc( MediaDTO dto ) throws IOException, NoSuchPermissionException
    {
        if ( dto == null )
            return;
        setCurrentPen( getPgcPenObject( dto.getObject() ) );
        String strPen;
        strPen = getCurrentPen().getPenData().getPenSerial();
        setPenId( strPen );
        setCurrentPgc( new PGCDTO( dto ) );
    }

    protected MediaDTO createMedia( MediaDTO base, byte[] object, String prefix )
    {
        MediaDTO dto = new MediaDTO();
        dto.setFormat( "pgc" );
        dto.setMimeType( base.getMimeType() );
        dto.setName( prefix + "_" + base.getName() );
        dto.setObject( object );
        return dto;
    }


    /*
    protected void split( Pen pen, MediaDTO base ) throws IOException
    {
        Iterator it = pen.split();
        int nIndex = 0;
        while ( it != null && it.hasNext() ) {
            Pen splittedPen = ( Pen )it.next();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            splittedPen.write( out );
            nIndex++;
            MediaDTO dto = createMedia( base, out.toByteArray(), "part_" + nIndex );
            getPgcs().add( dto );
            out.close();
        }
    }
    */

    public List<MediaDTO> getPgcs()
    {
        if ( pgcs == null )
            pgcs = new ArrayList<MediaDTO>();
        return pgcs;
    }


    protected AnodeFacade getRemoteSession()
    {
        try {
            return ( AnodeFacade )ServiceLocator.getInstance().getRemoteSession( AnodeFacade.class );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }


    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = getRemoteSession();
        return session;
    }


    public void persist() throws ApplicationException
    {
        PGCDTO pgc = getCurrentPgc();
        List<String> addresses = getPageAddresess();
        getSession().add( getCurrentPgc(), getPenId(), addresses );
    }


    protected List<String> getPageAddresess ()
    {
        Iterator it = getCurrentPen().getPageAddresses();
        List<String> addresses = null;
        while ( it != null & it.hasNext() )
        {
            if ( addresses == null )
                addresses = new ArrayList<String> ();
            addresses.add( (String) it.next() );
        }
        return addresses;
    }


    public void setCurrentPgc( PGCDTO currentPgc )
    {
        this.currentPgc = currentPgc;
    }

    public PGCDTO getCurrentPgc()
    {
        return currentPgc;
    }

    protected void setCurrentPen( Pen currentPen )
    {
        this.currentPen = currentPen;
    }

    protected Pen getCurrentPen()
    {
        return currentPen;
    }

    public void setObject ( PadFile pad,  byte[] object ) throws PenCreationException
    {
        setPad( pad );
        bytePgc = object;
        setCurrentPen( pad.getPen( new ByteArrayInputStream( bytePgc ) ) );
    }

    protected void setPad( PadFile pad )
    {
        this.pad = pad;
    }

    protected PadFile getPad()
    {
        return pad;
    }


    public int getBookCount ()
    {
        try {
            Iterator it = getCurrentPen().getLogicalBooks();
            Iterator obj;
            int nCount = 0;
            while ( it != null && it.hasNext() )
            {
                obj = (Iterator) it.next();
                nCount++;
            }
            return nCount;
        }
        catch ( Exception e ) {
            e = null;
            return 0;
        }
    }

    public List<AnotoBook> getBooks ()
    {
        List<AnotoBook> books = null;

        Iterator bookIterator;
        try {
            bookIterator = getCurrentPen().getLogicalBooks();
            while ( bookIterator != null && bookIterator.hasNext() )
            {
                if ( books == null )
                    books = new ArrayList<AnotoBook> ();
                AnotoBook ab = new AnotoBook ( (Iterator) bookIterator.next() );
                books.add( ab );
            }
            return books;
        }
        catch ( Exception e ) {
            return books;
        }
    }

    protected Iterator getBook ( int nIndex )
    {
        if ( nIndex < 0 )
            return null;
        Iterator book = null;
        Iterator it;
        try {
            it = getCurrentPen().getLogicalBooks();
        }
        catch ( Exception e ) {
            return null;
        }
        while ( it != null && it.hasNext() && nIndex >= 0 )
        {
            book = ( Iterator )it.next();
            nIndex --;
        }
        if ( nIndex >= 0 )
            return null;
        return book;
    }

    public Page getPage ( int book, int nIndex )
    {
        Iterator bookIt = getBook ( book );
        Page page = null;
        page = getPage( bookIt, nIndex );
        return page;
    }


    public Page getPage ( Iterator book, int nIndex )
    {
        Page page = null;
        if ( book == null )
            return getPage ( nIndex );
        while ( book.hasNext() && nIndex >=0 )
        {
            page = ( Page ) book.next();
            nIndex --;
        }
        if ( nIndex >= 0 )
            return null;
        return page;
    }

    public Page getPage ( int nIndex )
    {
        Page page = null;
        Iterator it;
        try {
            it = getCurrentPen().getPages();
        }
        catch ( PageException e ) {
            return null;
        }
        while ( it != null && it.hasNext() && nIndex >= 0  )
        {
            page = (Page) it.next();
            nIndex --;
        }
        return page;
    }

    public int getPageCount ( int nBook )
    {
        int nIndex = 0;

        Iterator book = getBook ( nBook );
        if ( book != null )
        {
            while ( book.hasNext() )
            {
                book.next();
                nIndex ++;
            }
        }
        else {
            try {
                Iterator it = getCurrentPen().getPages();
                while ( it != null && it.hasNext() )
                {
                    it.next();
                    nIndex ++;
                }
            }
            catch ( PageException e ) {
                nIndex = 0;
            }
        }
        return nIndex;
    }

    public boolean hasLogicalBooks ()
    {
        Iterator books;
        try {
            books = getCurrentPen().getLogicalBooks();
            return ( books != null && books.hasNext() );
        }
        catch ( Exception e ) {
            return false;
        }
    }
}
