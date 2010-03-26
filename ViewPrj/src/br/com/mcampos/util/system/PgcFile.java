package br.com.mcampos.util.system;


import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.system.MediaDTO;

import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

import com.anoto.api.NoSuchPermissionException;
import com.anoto.api.Pen;
import com.anoto.api.PenCreationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.event.UploadEvent;


public class PgcFile
{
    List<MediaDTO> pgcs;
    String penId;
    AnodeFacade session;

    PGCDTO currentPgc;

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
        Pen pen = getPgcPenObject( dto.getObject() );
        String strPen;
        strPen = pen.getPenData().getPenSerial();
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
        getSession().add( getCurrentPgc(), getPenId(), "nil" );
    }

    public void setCurrentPgc( PGCDTO currentPgc )
    {
        this.currentPgc = currentPgc;
    }

    public PGCDTO getCurrentPgc()
    {
        return currentPgc;
    }
}
