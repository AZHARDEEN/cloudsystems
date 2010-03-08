package br.com.mcampos.util.system;


import br.com.mcampos.dto.system.*;

import com.anoto.api.*;

import java.io.*;

import java.util.*;

import org.zkoss.zk.ui.event.*;


public class PgcFile
{
    List<MediaDTO> pgcs;

    public PgcFile()
    {
        super();
    }

    protected com.anoto.api.Pen getPgcPenObject( byte[] pgcByteArray )
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


    public void uploadPgc( UploadEvent evt ) throws IOException
    {
        MediaDTO dto;
        dto = UploadMedia.getMedia( evt.getMedia() );
        if ( dto == null )
            return;
        Pen pen = getPgcPenObject( dto.getObject() );
        split ( pen, dto );
    }

    protected MediaDTO createMedia ( MediaDTO base, byte[] object, String prefix )
    {
        MediaDTO dto = new MediaDTO ();
        dto.setFormat( "pgc" );
        dto.setMimeType( base.getMimeType() );
        dto.setName( prefix+ "_" + base.getName() );
        dto.setObject( object );
        return dto;
    }

    protected void split ( Pen pen, MediaDTO base ) throws IOException
    {
        Iterator it = pen.split();
        int nIndex = 0;
        while ( it != null && it.hasNext() )
        {
            Pen splittedPen = (Pen)it.next();
            ByteArrayOutputStream out = new ByteArrayOutputStream ();
            splittedPen.write( out );
            nIndex ++;
            MediaDTO dto = createMedia ( base, out.toByteArray(), "part_" + nIndex );
            getPgcs().add ( dto );
            out.close();
        }
    }

    public List<MediaDTO> getPgcs()
    {
        if ( pgcs == null )
            pgcs = new ArrayList<MediaDTO> ();
        return pgcs;
    }
}
