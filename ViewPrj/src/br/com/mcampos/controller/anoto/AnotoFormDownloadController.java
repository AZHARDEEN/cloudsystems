package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.AnotoFormListRenderer;
import br.com.mcampos.controller.anoto.renderer.MediaListRenderer;
import br.com.mcampos.controller.anoto.util.AnotoExport;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;


public class AnotoFormDownloadController extends LoggedBaseController
{
    @SuppressWarnings( "compatibility:-7648546457520714761" )
    private static final long serialVersionUID = -134995036935713778L;
    private AnotoFormFacade session;
    private AnodeFacade exportFacade;

    private Label labelAnotoFormDownloadTitle;
    private Listheader headerApplication;
    private Listheader headerDescription;
    private Listheader listHeaderNameOther;

    private Listbox listAttachsOther;
    private Listbox listboxRecord;

    private Button btnExportOther;
    private Button btnExportAllPgc;

    public AnotoFormDownloadController( char c )
    {
        super( c );
    }

    public AnotoFormDownloadController()
    {
        super();
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelAnotoFormDownloadTitle );
        setLabel( headerApplication );
        setLabel( headerDescription );
        setLabel( listHeaderNameOther );
        setLabel( btnExportOther );
        setLabel( btnExportAllPgc );

        if ( listAttachsOther != null ) {
            listAttachsOther.setItemRenderer( new MediaListRenderer() );
        }
        btnExportOther.setDisabled( true );
        List<FormDTO> list = getSession().getForms( getLoggedInUser() );
        ListModelList model = new ListModelList( list );
        listboxRecord.setItemRenderer( new AnotoFormListRenderer() );
        listboxRecord.setModel( model );
    }


    public void onSelect$listAttachsOther()
    {
        btnExportOther.setDisabled( false );
    }

    public void onSelect$listboxRecord()
    {
        FormDTO formDTO = ( ( FormDTO )listboxRecord.getSelectedItem().getValue() );
        refreshOtherAttachs( formDTO );
    }

    public void onClick$btnExportOther()
    {
        try {
            Listitem item = listAttachsOther.getSelectedItem();
            if ( item == null ) {
                showErrorMessage( "noCurrentRecordMessage" );
                return;
            }

            MediaDTO media = ( MediaDTO )item.getValue();
            byte[] obj;
            obj = getSession().getObject( media );
            if ( obj != null ) {
                Filedownload.save( obj, media.getMimeType(), media.getName() );
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Download" );
        }
    }

    public void onClick$btnExportAllPgc()
    {
        AnotoExport export;
        FormDTO formDTO = ( ( FormDTO )listboxRecord.getSelectedItem().getValue() );


        Document doc;
        try {
            export = new AnotoExport( getLoggedInUser(), formDTO );
            export.setExportImages( true );
            export.exportToFile();
            try {
                Messagebox.show( "Foram exportados os pgc's!", "Exportar Pgc's", Messagebox.OK, Messagebox.INFORMATION );
            }
            catch ( Exception e ) {
                e = null;
            }
        }
        catch ( ApplicationException e ) {
            try {
                Messagebox.show( e.getMessage() );
            }
            catch ( InterruptedException f ) {
                f = null;
            }
            doc = null;
        }
    }

    private AnotoFormFacade getSession()
    {
        if ( session == null )
            session = ( AnotoFormFacade )getRemoteSession( AnotoFormFacade.class );
        return session;
    }

    private AnodeFacade getExportSession()
    {
        if ( exportFacade == null )
            exportFacade = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return exportFacade;
    }

    private void refreshOtherAttachs( FormDTO current )
    {
        try {
            List<MediaDTO> medias = getSession().getMedias( getLoggedInUser(), current );
            listAttachsOther.setModel( new ListModelList( !SysUtils.isEmpty( medias ) ? medias : new ArrayList<MediaDTO>() ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }

}
