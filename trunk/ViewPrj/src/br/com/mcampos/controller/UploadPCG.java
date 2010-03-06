package br.com.mcampos.controller;


import br.com.mcampos.controller.core.BaseController;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;


public class UploadPCG extends BaseController
{
    private AnodeFacade session;

    UploadPCG( char c )
    {
        super( c );
    }

    public UploadPCG()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        Execution execution = Executions.getCurrent();

        try {
            javax.servlet.http.HttpServletRequest req = ( HttpServletRequest )execution.getNativeRequest();
            ServletInputStream is = req.getInputStream();
            System.out.println( "Size: " + is.available() );
            byte[] pgcByte = new byte[ is.available() ];
            /*I really need pgc info*/
            int nRead = is.read( pgcByte );
            System.out.println( "Bytes Read: " + nRead );
            while ( nRead != pgcByte.length ) {
                nRead += is.read( pgcByte, nRead, pgcByte.length );
                System.out.println( "Bytes Read: " + nRead );
            }
            MediaDTO media = new MediaDTO();
            media.setFormat( "pgc" );
            media.setMimeType( "application/octet-stream" );
            Date currentDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat( "dd_MM_yyyy_hh.mm.ss.SSS" );
            media.setName( String.format( "penDispatcher_%s.pcg", format.format( currentDate ) ) );
            media.setObject( pgcByte );
            PGCDTO pgc = new PGCDTO( media );
            getSession().add( pgc );
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }

    public AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }
}
