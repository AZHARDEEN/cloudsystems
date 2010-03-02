package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.PgcListRendered;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import br.com.mcampos.exception.ApplicationException;

import java.io.File;

import java.io.IOException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;


public class AnotoPcgController extends LoggedBaseController
{
    private AnodeFacade session;
    protected Button cmdCreate;
    protected org.zkoss.zul.Image imgTest;
    protected Listbox listboxRecord;
    protected Grid gridProperties;

    public AnotoPcgController()
    {
        super();
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        List<PGCDTO> medias = getSession().getAllPgc( getLoggedInUser() );

        listboxRecord.setItemRenderer( new PgcListRendered() );
        listboxRecord.setModel( new ListModelList( medias, true ) );
    }

    private File checkAndCreateDir( File directory ) throws Exception
    {
        if ( !directory.exists() ) {
            directory.mkdirs();
        }
        return directory;
    }

    public void onUpload$btnAddAttach( UploadEvent evt )
    {
        MediaDTO dto = getMedia( evt.getMedia() );
        if ( dto == null )
            return;
        PGCDTO pgc = new PGCDTO( dto );
        try {
            getSession().add( pgc );
            ListModelList model = ( ListModelList )listboxRecord.getModel();
            model.add( pgc );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Upload Error" );
        }
    }

    protected MediaDTO getMedia( org.zkoss.util.media.Media media )
    {
        MediaDTO dto = new MediaDTO();
        int mediaSize;

        try {
            dto.setFormat( media.getFormat() );
            dto.setName( media.getName() );
            dto.setMimeType( media.getContentType() );
            if ( media.inMemory() ) {
                if ( media.isBinary() )
                    dto.setObject( media.getByteData() );
                else
                    dto.setObject( media.getStringData().getBytes() );
            }
            else {
                mediaSize = media.getStreamData().available();
                dto.setObject( new byte[ mediaSize ] );
                media.getStreamData().read( dto.getObject(), 0, mediaSize );
            }
            return dto;

        }
        catch ( IOException e ) {
            showErrorMessage( e.getMessage(), "Upload Error" );
            return null;
        }
    }

    public void onSelect$listboxRecord()
    {
        PGCDTO dto = ( PGCDTO )listboxRecord.getSelectedItem().getValue();

        try {
            getSession().getObject( getLoggedInUser(), dto.getMedia() );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "" );
        }
    }

}
